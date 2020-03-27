package supercoder79.cavebiomes.cave;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkRegion;

import java.util.Set;

public class RedSandCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (chunk.getBlockState(pos.down()).isOpaque()) {
                if (world.getRandom().nextInt(3) == 0) {
                    chunk.setBlockState(pos.down(), Blocks.RED_SAND.getDefaultState(), false);
                }
            }
        }
    }
}
