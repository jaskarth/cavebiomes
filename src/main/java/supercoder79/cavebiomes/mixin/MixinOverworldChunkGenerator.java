package supercoder79.cavebiomes.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.layer.LayerHolder;
import supercoder79.cavebiomes.magic.CaveAirAccess;
import supercoder79.cavebiomes.magic.SaneCarverAccess;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(OverworldChunkGenerator.class)
public abstract class MixinOverworldChunkGenerator extends SurfaceChunkGenerator implements SaneCarverAccess {

    public MixinOverworldChunkGenerator(IWorld world, BiomeSource biomeSource, int verticalNoiseResolution, int horizontalNoiseResolution, int worldHeight, ChunkGeneratorConfig config, boolean useSimplexNoise) {
        super(world, biomeSource, verticalNoiseResolution, horizontalNoiseResolution, worldHeight, config, useSimplexNoise);
    }

    @Shadow protected abstract double[] computeNoiseRange(int x, int z);

    @Shadow protected abstract double computeNoiseFalloff(double depth, double scale, int y);

    @Shadow public abstract int getSpawnHeight();

    @Shadow public abstract void sampleNoiseColumn(double[] buffer, int x, int z) ;

    @Override
    public void carve(ChunkRegion world, BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carver) {
        ChunkRandom chunkRandom = new ChunkRandom();
        ChunkPos chunkPos = chunk.getPos();
        int j = chunkPos.x;
        int k = chunkPos.z;
        Biome biome = this.getDecorationBiome(biomeAccess, chunkPos.getCenterBlockPos());
        BitSet bitSet = chunk.getCarvingMask(carver);

        for(int l = j - 8; l <= j + 8; ++l) {
            for(int m = k - 8; m <= k + 8; ++m) {
                List<ConfiguredCarver<?>> list = biome.getCarversForStep(carver);
                ListIterator listIterator = list.listIterator();

                Set<BlockPos> positions = new HashSet<>();

                while(listIterator.hasNext()) {
                    int n = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = (ConfiguredCarver)listIterator.next();
                    chunkRandom.setCarverSeed(this.seed + (long)n, l, m);

                    if (configuredCarver.shouldCarve(chunkRandom, l, m)) {
                        if (configuredCarver.carver instanceof CaveAirAccess) {
                            ((CaveAirAccess)configuredCarver.carver).reset();
                        }

                        configuredCarver.carve(chunk, (blockPos) -> this.getDecorationBiome(biomeAccess, blockPos), chunkRandom, this.getSeaLevel(), l, m, j, k, bitSet);

                        if (configuredCarver.carver instanceof CaveAirAccess) {
                            positions.addAll(((CaveAirAccess)configuredCarver.carver).retrieve());
                        }
                    }
                }

                //regular biome based decoration
                Set<BlockPos> upperPos = positions.stream().filter(pos -> pos.getY() > 28).collect(Collectors.toSet());
                CaveDecorator decorator = CaveBiomes.BIOME2CD.getOrDefault(biome, CaveDecorators.NONE);
                decorator.decorate(world, chunk, upperPos);

                //epic underground biome based decoration
                Set<BlockPos> lowerPos = positions.stream().filter(pos -> pos.getY() <= 28).collect(Collectors.toSet());
                LayerHolder.getDecorator(world.getSeed(), j, k).decorate(world, chunk, lowerPos);
            }
        }

    }
}
