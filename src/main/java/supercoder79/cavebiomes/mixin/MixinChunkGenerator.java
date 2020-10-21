package supercoder79.cavebiomes.mixin;

import com.google.common.collect.ImmutableSet;
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
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.fluid.FloodFill;
import supercoder79.cavebiomes.layer.LayerGenerator;
import supercoder79.cavebiomes.util.CaveAirAccess;
import supercoder79.cavebiomes.util.WorldCarverAccess;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(ChunkGenerator.class)
public abstract class MixinChunkGenerator implements WorldCarverAccess {

    @Shadow public abstract int getSeaLevel();

    @Shadow @Final protected BiomeSource biomeSource;

    //TODO: fix this to not essentially be an overwrite
    @Override
    public void carve(long seed, ChunkRegion world, BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carverStep) {
        //only generate in the overworld by default
        boolean shouldGenerate = true;
        RegistryKey<World> key = world.toServerWorld().getRegistryKey();
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.getValue().toString())) {
            shouldGenerate = false;
        }

        biomeAccess = biomeAccess.withSource(this.biomeSource);
        ChunkRandom chunkRandom = new ChunkRandom();
        ChunkPos chunkPos = chunk.getPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;
        Biome biome = this.biomeSource.getBiomeForNoiseGen(chunkPos.x << 2, 0, chunkPos.z << 2);
        BitSet bitSet = ((ProtoChunk)chunk).getOrCreateCarvingMask(carverStep);

        Set<BlockPos> positions = new HashSet<>();

        for(int localX = chunkX - 8; localX <= chunkX + 8; ++localX) {
            for(int localZ = chunkZ - 8; localZ <= chunkZ + 8; ++localZ) {
                List<Supplier<ConfiguredCarver<?>>> list = biome.getGenerationSettings().getCarversForStep(carverStep);
                ListIterator<Supplier<ConfiguredCarver<?>>> listIterator = list.listIterator();

                while(listIterator.hasNext()) {
                    int idx = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = listIterator.next().get();

                    Carver<?> carver = ((ConfiguredCarverAccessor) configuredCarver).getCarver();
                    chunkRandom.setCarverSeed(seed + (long) idx, localX, localZ);

                    if (configuredCarver.shouldCarve(chunkRandom, localX, localZ)) {
                        if (carver instanceof CaveAirAccess) {
                            ((CaveAirAccess) carver).reset();
                        }

                        configuredCarver.carve(chunk, biomeAccess::getBiome, chunkRandom, this.getSeaLevel(), localX, localZ, chunkX, chunkZ, bitSet);

                        if (carver instanceof CaveAirAccess) {
                            positions.addAll(((CaveAirAccess) carver).retrieve());
                        }
                    }
                }
            }
        }

        if (shouldGenerate) {
            int threshold = CaveBiomes.CONFIG.caveLayerThreshold;

            //regular biome based decoration
            Set<BlockPos> upperPos = positions.stream().filter(pos -> pos.getY() > threshold).collect(Collectors.toSet());

            Registry<Biome> biomes = world.toServerWorld().getServer().getRegistryManager().get(Registry.BIOME_KEY);
            CaveDecorator decorator = CaveBiomesAPI.getCaveDecoratorForBiome(biomes, biome);
            decorator.decorate(world, chunk, upperPos);

            //epic underground biome based decoration
            Set<BlockPos> lowerPos = positions.stream().filter(pos -> pos.getY() <= threshold).collect(Collectors.toSet());
            LayerGenerator.getDecorator(world.getSeed(), chunkX, chunkZ).decorate(world, chunk, lowerPos);
        }
    }

    @Inject(method = "generateFeatures", at = @At("HEAD"))
    private void generateFloodFill(ChunkRegion world, StructureAccessor accessor, CallbackInfo ci) {
        ChunkRandom random = new ChunkRandom();
        int chunkX = world.getCenterChunkX();
        int chunkZ = world.getCenterChunkZ();
        random.setPopulationSeed(world.getSeed(), chunkX << 4, chunkZ << 4);

        int x = (chunkX << 4) + random.nextInt(16);
        int z = (chunkZ << 4) + random.nextInt(16);
        int y = random.nextInt(50) + 12;
        FloodFill.floodFill(world, random, new BlockPos(x, y, z), ImmutableSet.of());
    }
}
