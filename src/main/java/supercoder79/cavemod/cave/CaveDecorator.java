package supercoder79.cavemod.cave;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkRegion;

import java.util.Set;

public abstract class CaveDecorator {
    public abstract void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions);
}
