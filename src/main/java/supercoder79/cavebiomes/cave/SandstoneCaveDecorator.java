package supercoder79.cavebiomes.cave;

import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class SandstoneCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        // Set sandstone
        for (BlockPos pos : positions) {
            // Try to set a sandstone block in every direction
            for (Direction direction : Direction.values()) {
                trySet(world, chunk, pos.offset(direction));
            }
        }

        for (BlockPos pos : positions) {
            // Generate bone structure rarely
            if (world.getRandom().nextInt(24) == 0) {
                if (chunk.getBlockState(pos.down()).isOf(Blocks.SANDSTONE)) {
                    Direction direction = Direction.Type.HORIZONTAL.random(world.getRandom());
                    int height = world.getRandom().nextInt(2) + 2;
                    if (checkSpace(chunk, pos, height, direction)) {
                        makeBone(chunk, pos, height, direction);
                    }
                }
            }
        }
    }

    private void trySet(ChunkRegion world, Chunk chunk, BlockPos pos) {
        // Generate sand or sandstone 83% of the time
        if (world.getRandom().nextInt(6) != 0) {
            if (chunk.getBlockState(pos).isOf(Blocks.STONE)) {
                // Generate sand and cacti sometimes
                if (world.getRandom().nextInt(8) == 0) {
                    chunk.setBlockState(pos, Blocks.SAND.getDefaultState(), false);
                    // Try to generate a cactus here
                    tryGenerateCactus(chunk, pos.up());
                } else {
                    chunk.setBlockState(pos, Blocks.SANDSTONE.getDefaultState(), false);
                }
            }
        }
    }

    private void makeBone(Chunk chunk, BlockPos origin, int height, Direction direction) {
        for (int i = 0; i < height; i++) {
            chunk.setBlockState(origin.up(i), Blocks.BONE_BLOCK.getDefaultState(), false);
        }

        chunk.setBlockState(origin.up(height).offset(direction), Blocks.BONE_BLOCK.getDefaultState().with(Properties.AXIS, direction.getAxis()), false);
    }

    private boolean checkSpace(Chunk chunk, BlockPos origin, int height, Direction direction) {
        BlockPos.Mutable mutable = origin.mutableCopy();
        for (int i = 0; i < height; i++) {
            mutable.move(Direction.UP);
            if (chunk.getBlockState(mutable).isOpaque() || chunk.getBlockState(mutable.offset(direction)).isOpaque()) {
                return false;
            }
        }

        return true;
    }

    private void tryGenerateCactus(Chunk chunk, BlockPos pos) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (chunk.getBlockState(pos.offset(direction)).isOpaque()) {
                return;
            }
        }

        if (chunk.getBlockState(pos.up()).isOpaque()) {
            return;
        }

        chunk.setBlockState(pos, Blocks.CACTUS.getDefaultState(), false);
    }
}
