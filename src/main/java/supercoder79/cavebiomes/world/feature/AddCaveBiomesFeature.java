package supercoder79.cavebiomes.world.feature;


import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.layer.LayerGenerator;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class AddCaveBiomesFeature extends Feature<DefaultFeatureConfig> {
    public AddCaveBiomesFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        RegistryKey<World> key = world.toServerWorld().getRegistryKey();
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.getValue().toString())) {
            return false;
        }

        ChunkPos chunkPos = new ChunkPos(pos);
        Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);

        BitSet mask = ((ProtoChunk)chunk).getCarvingMask(GenerationStep.Carver.AIR);
        Set<BlockPos> positions = new HashSet<>();

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int x = 0; x < 16; x++) {
            mutable.setX(pos.getX() + x);
            for (int z = 0; z < 16; z++) {
                mutable.setZ(pos.getZ() + z);
                for (int y = world.getBottomHeightLimit(); y < world.getTopHeightLimit(); y++) {
                    mutable.setY(y);

                    int packed = x | z << 4 | y << 8;

                    if (mask.get(packed)) {
                        if (world.getBlockState(mutable).isOf(Blocks.CAVE_AIR)) {
                            positions.add(mutable.toImmutable());
                        }
                    }
                }
            }
        }

        int threshold = CaveBiomes.CONFIG.caveLayerThreshold;

        Biome biome = chunkGenerator.getBiomeSource().getBiomeForNoiseGen(chunkPos.x << 2, 0, chunkPos.z << 2);

        //regular biome based decoration
        Set<BlockPos> upperPos = positions.stream().filter(p -> p.getY() > threshold).collect(Collectors.toSet());

        Registry<Biome> biomes = world.toServerWorld().getServer().getRegistryManager().get(Registry.BIOME_KEY);
        CaveDecorator decorator = CaveBiomesAPI.getCaveDecoratorForBiome(biomes, biome);

        // TODO: block based generation instead of chunk based
        for (BlockPos biomePos : upperPos) {
            random.setSeed((long)biomePos.getX() * 341873128712L + (long)biomePos.getZ() * 132897987541L + (long)biomePos.getY() * 3153265741L);
            decorator.decorate((ChunkRegion) world, random, biomePos);
        }

        //epic underground biome based decoration
        Set<BlockPos> lowerPositions = positions.stream().filter(p -> p.getY() <= threshold).collect(Collectors.toSet());

        CaveDecorator[] caveBiomes = new CaveDecorator[256];
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                caveBiomes[x * 16 + z] = LayerGenerator.getDecorator(world.getSeed(), chunkPos.x * 16 + x, chunkPos.z * 16 + z);
            }
        }

        for (BlockPos layerPos : lowerPositions) {
            random.setSeed((long)layerPos.getX() * 341873128712L + (long)layerPos.getZ() * 132897987541L + (long)layerPos.getY() * 3153265741L);

            caveBiomes[(layerPos.getX() & 15) * 16 + (layerPos.getZ() & 15)].decorate((ChunkRegion) world, random, layerPos);
        }

        return false;
    }
}
