package supercoder79.cavemod.magic;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;

public interface SaneCarverAccess {
    void carve(ChunkRegion region, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver);
}