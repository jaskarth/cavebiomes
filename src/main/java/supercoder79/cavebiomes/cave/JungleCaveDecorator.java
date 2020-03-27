package supercoder79.cavebiomes.cave;

import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JungleCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            //grass generation
            if (chunk.getBlockState(pos.down()).isOpaque()) {
                if (world.getRandom().nextInt(2) == 0) {
                    chunk.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), false);
                    chunk.setBlockState(pos, Blocks.GRASS.getDefaultState(), false);
                }
            }
        }

        //vine generation (yeah this is even more cursed)
        for (BlockPos pos : positions) {
            if ((pos.getX() & 15) != 15 && (pos.getZ() & 15) != 15 && (pos.getX() & 15) != 0 && (pos.getZ() & 15) != 0) {
                int num = world.getRandom().nextInt(4);
                if (num == 0) {
                    if (chunk.getBlockState(pos.north()).isOpaque()) {
                        chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.NORTH, true), false);
                    }
                }
                if (num == 1) {
                    if (chunk.getBlockState(pos.south()).isOpaque()) {
                        chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, true), false);
                    }
                }
                if (num == 2) {
                    if (chunk.getBlockState(pos.west()).isOpaque()) {
                        chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.WEST, true), false);
                    }
                }
                if (num == 3) {
                    if (chunk.getBlockState(pos.east()).isOpaque()) {
                        chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.EAST, true), false);
                    }
                }
            }
        }
    }
}
