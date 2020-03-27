package supercoder79.cavemod.cave;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkRegion;

import java.util.Set;

public class DummyCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
    }
}
