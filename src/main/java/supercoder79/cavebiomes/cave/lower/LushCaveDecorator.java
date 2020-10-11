package supercoder79.cavebiomes.cave.lower;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavebiomes.cave.CaveDecorator;

import java.util.Random;
import java.util.Set;

public class LushCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            // Try to set a dirt block in every direction
            for (Direction direction : Direction.values()) {
                trySet(world, chunk, pos.offset(direction));
            }
        }

        // make shrub
        // ecotones moment
        for (BlockPos pos : positions) {
            if (chunk.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
                if (world.getRandom().nextInt(32) == 0) {
                    generateShrub(world, chunk, pos);
                }
            }
        }

        for (BlockPos pos : positions) {
            Direction direction = Direction.Type.HORIZONTAL.random(world.getRandom());
            generateVines(world, chunk, pos, direction);
        }
    }

    private void trySet(ChunkRegion world, Chunk chunk, BlockPos pos) {
        // Generate dirt 75% of the time
        if (world.getRandom().nextInt(4) != 0) {
            if (chunk.getBlockState(pos).isOf(Blocks.STONE)) {
                chunk.setBlockState(pos, Blocks.DIRT.getDefaultState(), false);
                // Generate grass sometimes
                if (world.getRandom().nextInt(2) == 0) {
                    if (!chunk.getBlockState(pos.up()).isOpaque()) {
                        chunk.setBlockState(pos.up(), Blocks.GRASS.getDefaultState(), false);
                    }
                }
            }
        }
    }

    private void generateVines(ChunkRegion world, Chunk chunk, BlockPos pos, Direction direction) {
        if (!(validatePos(pos.getX()) && validatePos(pos.getZ()))) {
            return;
        }

        int height = world.getRandom().nextInt(4) + 2;

        if (canGenerateVine(chunk.getBlockState(pos.offset(direction))) && chunk.getBlockState(pos).isAir()) {
            chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true), false);
        } else {
            return;
        }

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int i = 0; i < height; i++) {
            mutable.move(Direction.DOWN);

            if (!chunk.getBlockState(mutable).isAir()) {
                return;
            }

            chunk.setBlockState(mutable, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true), false);
        }
    }

    private void generateShrub(ChunkRegion world, Chunk chunk, BlockPos pos) {
        if (!(validatePos(pos.getX()) && validatePos(pos.getZ()))) {
            return;
        }

        int height = world.getRandom().nextInt(2) + 1;

        BlockPos.Mutable mutable = pos.mutableCopy();

        // Check to make sure that there's space
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y < height + 2; y++) {
                    mutable.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

                    if (chunk.getBlockState(mutable).isOpaque()) {
                        return;
                    }
                }
            }
        }

        for (int y = 0; y < height; y++) {
            chunk.setBlockState(pos.up(y), Blocks.OAK_LOG.getDefaultState(), false);
        }

        for (Direction direction : Direction.values()) {
            BlockPos local = pos.up(height - 1).offset(direction);

            if (chunk.getBlockState(local).getMaterial().isReplaceable()) {
                chunk.setBlockState(local, Blocks.OAK_LEAVES.getDefaultState(), false);
            }
        }
    }

    private static boolean validatePos(int val) {
        return (val & 15) != 0 && (val & 15) != 15;
    }

    private static boolean canGenerateVine(BlockState state) {
        return state.isOpaque() || state.isIn(BlockTags.LEAVES);
    }
}
