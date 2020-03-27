package supercoder79.cavemod.cave;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class DirtGrassCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (chunk.getBlockState(pos.down()).isOpaque()) {
                if (world.getRandom().nextInt(3) == 0) {
                    chunk.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), false);
                    if (world.getRandom().nextInt(2) == 0) {
                        chunk.setBlockState(pos, Blocks.GRASS.getDefaultState(), false);
                    }
                }
            }
        }
    }
}
