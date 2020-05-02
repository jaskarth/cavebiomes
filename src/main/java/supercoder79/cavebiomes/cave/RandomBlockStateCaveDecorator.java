package supercoder79.cavebiomes.cave;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class RandomBlockStateCaveDecorator extends CaveDecorator {
    private final BlockState state;
    private final int chance;

    public RandomBlockStateCaveDecorator(BlockState state, int chance) {
        this.state = state;
        this.chance = chance;
    }

    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.east()).isOpaque()) {
                    chunk.setBlockState(pos.east(), state, false);
                }
            }

            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.west()).isOpaque()) {
                    chunk.setBlockState(pos.west(), state, false);
                }
            }

            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.north()).isOpaque()) {
                    chunk.setBlockState(pos.north(), state, false);
                }
            }

            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.south()).isOpaque()) {
                    chunk.setBlockState(pos.south(), state, false);
                }
            }

            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.up()).isOpaque()) {
                    chunk.setBlockState(pos.up(), state, false);
                }
            }

            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.down()).isOpaque()) {
                    chunk.setBlockState(pos.down(), state, false);
                }
            }
        }
    }
}
