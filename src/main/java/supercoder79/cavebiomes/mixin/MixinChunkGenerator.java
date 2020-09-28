package supercoder79.cavebiomes.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.layer.LayerGenerator;
import supercoder79.cavebiomes.magic.CaveAirAccess;
import supercoder79.cavebiomes.magic.WorldCarverAccess;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(ChunkGenerator.class)
public abstract class MixinChunkGenerator implements WorldCarverAccess {

    @Shadow public abstract int getSeaLevel();

    @Shadow @Final protected BiomeSource biomeSource;

    //TODO: fix this to not essentially be an overwrite
    @Override
    public void carve(long seed, ChunkRegion world, BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carver) {
        //only generate in the overworld by default
        boolean shouldGenerate = true;
        RegistryKey<World> key = world.toServerWorld().getRegistryKey();
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.getValue().toString())) {
            shouldGenerate = false;
        }

        biomeAccess = biomeAccess.withSource(this.biomeSource);
        ChunkRandom chunkRandom = new ChunkRandom();
        ChunkPos chunkPos = chunk.getPos();
        int j = chunkPos.x;
        int k = chunkPos.z;
        Biome biome = this.biomeSource.getBiomeForNoiseGen(chunkPos.x << 2, 0, chunkPos.z << 2);
        BitSet bitSet = ((ProtoChunk)chunk).getOrCreateCarvingMask(carver);

        Set<BlockPos> positions = new HashSet<>();

        for(int l = j - 8; l <= j + 8; ++l) {
            for(int m = k - 8; m <= k + 8; ++m) {
                List<Supplier<ConfiguredCarver<?>>> list = biome.getGenerationSettings().getCarversForStep(carver);
                ListIterator<Supplier<ConfiguredCarver<?>>> listIterator = list.listIterator();

                while(listIterator.hasNext()) {
                    int n = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = listIterator.next().get();
                    Carver<?> c = ((ConfiguredCarverAccessor) configuredCarver).getCarver();
                    chunkRandom.setCarverSeed(seed + (long)n, l, m);

                    if (configuredCarver.shouldCarve(chunkRandom, l, m)) {
                        if (c instanceof CaveAirAccess) {
                            ((CaveAirAccess)c).reset();
                        }

                        configuredCarver.carve(chunk, biomeAccess::getBiome, chunkRandom, this.getSeaLevel(), l, m, j, k, bitSet);

                        if (c instanceof CaveAirAccess) {
                            positions.addAll(((CaveAirAccess)c).retrieve());
                        }
                    }
                }
            }
        }

        if (shouldGenerate) {
            //regular biome based decoration
            Set<BlockPos> upperPos = positions.stream().filter(pos -> pos.getY() > 28).collect(Collectors.toSet());

            Registry<Biome> biomes = world.toServerWorld().getServer().getRegistryManager().get(Registry.BIOME_KEY);
            CaveDecorator decorator = CaveBiomesAPI.getCaveDecoratorForBiome(biomes, biome);
            decorator.decorate(world, chunk, upperPos);

            //epic underground biome based decoration
            Set<BlockPos> lowerPos = positions.stream().filter(pos -> pos.getY() <= 28).collect(Collectors.toSet());
            LayerGenerator.getDecorator(world.getSeed(), j, k).decorate(world, chunk, lowerPos);
        }
    }
}
