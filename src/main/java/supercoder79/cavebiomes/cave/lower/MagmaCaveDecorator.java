package supercoder79.cavebiomes.cave.lower;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavebiomes.cave.CaveDecorator;

import java.util.Set;

public class MagmaCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(8) == 0) {
                if (chunk.getBlockState(pos.east()).isOpaque()) {
                    chunk.setBlockState(pos.east(), Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(8) == 0) {
                if (chunk.getBlockState(pos.west()).isOpaque()) {
                    chunk.setBlockState(pos.west(), Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(8) == 0) {
                if (chunk.getBlockState(pos.north()).isOpaque()) {
                    chunk.setBlockState(pos.north(), Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(8) == 0) {
                if (chunk.getBlockState(pos.south()).isOpaque()) {
                    chunk.setBlockState(pos.south(), Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(8) == 0) {
                if (chunk.getBlockState(pos.up()).isOpaque()) {
                    chunk.setBlockState(pos.up(), Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(8) == 0) {
                if (chunk.getBlockState(pos.down()).isOpaque()) {
                    chunk.setBlockState(pos.down(), Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }
            }
        }
    }
}
