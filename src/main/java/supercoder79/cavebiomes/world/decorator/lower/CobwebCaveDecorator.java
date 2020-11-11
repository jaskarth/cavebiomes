package supercoder79.cavebiomes.world.decorator.lower;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavebiomes.world.decorator.CaveDecorator;

import java.util.Set;

public class CobwebCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(16) == 0) {
                chunk.setBlockState(pos, Blocks.COBWEB.getDefaultState(), false);
            }
        }
    }
}
