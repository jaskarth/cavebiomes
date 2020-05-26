package supercoder79.cavebiomes.mixin;

import net.minecraft.class_5311;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.cavebiomes.BiomeHandler;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.layer.LayerHolder;
import supercoder79.cavebiomes.magic.CaveAirAccess;
import supercoder79.cavebiomes.magic.SaneCarverAccess;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(ChunkGenerator.class)
public abstract class MixinChunkGenerator implements SaneCarverAccess {

    @Shadow public abstract int getSeaLevel();

    @Shadow @Final protected BiomeSource biomeSource;

    //TODO: fix this to not essentially be an overwrite
    @Override
    public void carve(long seed, ChunkRegion world, BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carver) {
        // try to register stuff for biomes registered after us
        BiomeHandler.attemptAddRemainingBiomes();

        //only generate in the overworld by default
        boolean shouldGenerate = true;
        Optional<RegistryKey<DimensionType>> key = ((DimensionTypeAccessor)world.getDimension()).getField_24765();
        if (!key.isPresent()) shouldGenerate = false;
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.get().getValue().toString())) shouldGenerate = false;

        biomeAccess = biomeAccess.withSource(this.biomeSource);
        ChunkRandom chunkRandom = new ChunkRandom();
        ChunkPos chunkPos = chunk.getPos();
        int j = chunkPos.x;
        int k = chunkPos.z;
        Biome biome = this.biomeSource.getBiomeForNoiseGen(chunkPos.x << 2, 0, chunkPos.z << 2);
        BitSet bitSet = ((ProtoChunk)chunk).method_28510(carver);

        for(int l = j - 8; l <= j + 8; ++l) {
            for(int m = k - 8; m <= k + 8; ++m) {
                List<ConfiguredCarver<?>> list = biome.getCarversForStep(carver);
                ListIterator listIterator = list.listIterator();

                Set<BlockPos> positions = new HashSet<>();

                while(listIterator.hasNext()) {
                    int n = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = (ConfiguredCarver)listIterator.next();
                    chunkRandom.setCarverSeed(seed + (long)n, l, m);

                    if (configuredCarver.shouldCarve(chunkRandom, l, m)) {
                        if (configuredCarver.carver instanceof CaveAirAccess) {
                            ((CaveAirAccess)configuredCarver.carver).reset();
                        }

                        configuredCarver.carve(chunk, biomeAccess::getBiome, chunkRandom, this.getSeaLevel(), l, m, j, k, bitSet);

                        if (configuredCarver.carver instanceof CaveAirAccess) {
                            positions.addAll(((CaveAirAccess)configuredCarver.carver).retrieve());
                        }
                    }
                }

                if (shouldGenerate) {

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
}
