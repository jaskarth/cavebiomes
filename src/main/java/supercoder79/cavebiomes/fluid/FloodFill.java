package supercoder79.cavebiomes.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

// Code used from TFC, created by AlcatrazEscapee. Used with permission!
// The code has been modified slightly to work better in caves.
public class FloodFill {
    public static void floodFill(ChunkRegion world, Random random, BlockPos pos, Set<BlockPos> carvedPositions) {
        long start = System.currentTimeMillis();
        ChunkPos chunkPos = new ChunkPos(pos);
        BlockBox box = new BlockBox(chunkPos.getStartX() - 14, chunkPos.getStartZ() - 14, chunkPos.getEndX() + 14, chunkPos.getEndZ() + 14); // Leeway so we can check outside this box

        // Begin a flood fill, using a depth first search (in order to quickly eliminate locations due to invalid boundary conditions)
        Set<BlockPos> filled = new HashSet<>();
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        BlockPos startPos = findStart(world, pos, box);

        // Initial placement is surface level, so start filling one block above
        boolean bounded = tryFloodFill(world, startPos, box, filled, mutablePos);
        if (bounded) {
            Set<BlockPos> lowestFilled = new HashSet<>(filled);

            // Iterate upwards, but only add the found blocks if the flood fill was bounded. Iterate upwards until the fill is unbounded
            int y = 1;
            int max = random.nextInt(6) + random.nextInt(6) + 2;
            Set<BlockPos> possibleFilled = new HashSet<>();
            while (tryFloodFill(world, startPos.up(y), box, possibleFilled, mutablePos)) {
                y++;
                filled.addAll(possibleFilled);
                possibleFilled.clear();
                if (y > max) break;
            }

            // Down fill, from the initial flood fill. This relies on the terrain not having any overhangs (and may cause weird interactions with caves if it does)
            while (!lowestFilled.isEmpty()) {
                lowestFilled = tryDownFill(world, lowestFilled, mutablePos);
                filled.addAll(lowestFilled);
            }

            for (BlockPos filledPos : filled) {
                world.setBlockState(filledPos, Blocks.WATER.getDefaultState(), 2);
                world.getFluidTickScheduler().schedule(filledPos, Fluids.WATER, 0);
            }

            // An assortment of debug features
//            System.out.println("Flooded filled at " + pos + " with " + filled.size() + " blocks in " + (System.currentTimeMillis() - start) + " ms");
//            world.setBlockState(pos, Blocks.DIAMOND_BLOCK.getDefaultState(), 3);
//            world.setBlockState(startPos, Blocks.EMERALD_BLOCK.getDefaultState(), 3);
        }
    }

    private static boolean tryFloodFill(ChunkRegion world, BlockPos startPos, BlockBox box, Set<BlockPos> filled, BlockPos.Mutable mutablePos) {
        // First check the start position, this must be fillable
        if (!isFloodFillable(world.getBlockState(startPos))) {
            return false;
        }

        final LinkedList<BlockPos> queue = new LinkedList<>();
        filled.add(startPos);
        queue.addFirst(startPos);

        while (!queue.isEmpty()) {
            BlockPos posAt = queue.removeFirst();
            for (Direction direction : Direction.Type.HORIZONTAL) {
                mutablePos.set(posAt).move(direction);
                if (!filled.contains(mutablePos)) {
                    BlockState stateAt = world.getBlockState(mutablePos);
                    if (isFloodFillable(stateAt)) {
                        if (box.contains(mutablePos)) {
                            // Valid flood fill location
                            BlockPos posNext = mutablePos.toImmutable();
                            queue.addFirst(posNext);
                            filled.add(posNext);
                        }
                        else {
                            // Invalid boundary condition
                            return false;
                        }
                    }
                }
            }
        }
        return !filled.isEmpty();
    }

    private static Set<BlockPos> tryDownFill(ChunkRegion world, Set<BlockPos> lowestFilled, BlockPos.Mutable mutablePos) {
        Set<BlockPos> nextLowestFilled = new HashSet<>();
        for (BlockPos pos : lowestFilled) {
            mutablePos.set(pos).move(0, -1, 0);
            BlockState state = world.getBlockState(mutablePos);
            if (isFloodFillable(state)) {
                nextLowestFilled.add(mutablePos.toImmutable());
            }
        }
        return nextLowestFilled;
    }

    private static boolean isFloodFillable(BlockState state) {
        return state.isAir() || (state.getMaterial().isReplaceable() && state.getBlock() != Blocks.LAVA);
    }

    private static BlockPos findStart(ChunkRegion world, BlockPos base, BlockBox box) {
        if (world.getBlockState(base).isOpaque()) {
            return base; // can't spawn- let the other code deal with it
        }

        BlockPos.Mutable mutable = base.mutableCopy();
        while (mutable.getY() > 11) {
            if (world.getBlockState(mutable.down()).isOpaque()) {
                break;
            }

            mutable.move(Direction.DOWN);
        }

        Set<BlockPos> positions = new HashSet<>();
        lower(world, mutable.toImmutable(), box, 0, positions);

        BlockPos ret = base.toImmutable();
        for (BlockPos position : positions) {

            if (position.getY() < ret.getY()) {
                ret = position.toImmutable();
            }
        }

        return ret;
    }

    // TODO: this is awful, needs to optimized
    private static void lower(ChunkRegion world, BlockPos pos, BlockBox box, int depth, Set<BlockPos> visited) {
        int newDepth = depth + 1;

        // We're out of bounds, return.
        if (!box.contains(pos)) {
            return;
        }

        visited.add(pos);

        // Don't recurse too much
        if (newDepth == 512) {
            return;
        }

        // We've reached lava level- this is as low as it can go.
        if (pos.getY() <= 10) {
            return;
        }

        // Search downwards first
        BlockPos down = pos.down();
        if (world.getBlockState(down).isAir() && !visited.contains(down)) {
            lower(world, down, box, newDepth, visited);
        } else {
            // Search all directions
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos local = pos.offset(direction);
                if (world.getBlockState(local).isAir() && !visited.contains(local)) {
                    lower(world, local, box, newDepth, visited);
                }
            }
        }
    }
}
