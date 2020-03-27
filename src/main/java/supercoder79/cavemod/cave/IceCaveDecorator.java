package supercoder79.cavemod.cave;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkRegion;

import java.util.Set;

public class IceCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(16) == 0) {
                if (chunk.getBlockState(pos.east()).isOpaque()) {
                    chunk.setBlockState(pos.east(), Blocks.PACKED_ICE.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(16) == 0) {
                if (chunk.getBlockState(pos.west()).isOpaque()) {
                    chunk.setBlockState(pos.west(), Blocks.PACKED_ICE.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(16) == 0) {
                if (chunk.getBlockState(pos.north()).isOpaque()) {
                    chunk.setBlockState(pos.north(), Blocks.PACKED_ICE.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(16) == 0) {
                if (chunk.getBlockState(pos.south()).isOpaque()) {
                    chunk.setBlockState(pos.south(), Blocks.PACKED_ICE.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(16) == 0) {
                if (chunk.getBlockState(pos.up()).isOpaque()) {
                    chunk.setBlockState(pos.up(), Blocks.PACKED_ICE.getDefaultState(), false);
                }
            }

            if (world.getRandom().nextInt(16) == 0) {
                if (chunk.getBlockState(pos.down()).isOpaque()) {
                    chunk.setBlockState(pos.down(), Blocks.PACKED_ICE.getDefaultState(), false);
                }
            }

            if (chunk.getBlockState(pos.down()).isOpaque()) {
                if (world.getRandom().nextInt(6) == 0) {
                    chunk.setBlockState(pos, Blocks.SNOW.getDefaultState(), false);
                }
            }
        }
    }
}
