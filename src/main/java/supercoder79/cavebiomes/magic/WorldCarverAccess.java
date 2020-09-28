package supercoder79.cavebiomes.magic;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;

public interface WorldCarverAccess {
    void carve(long seed, ChunkRegion region, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver);
}