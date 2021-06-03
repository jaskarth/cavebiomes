package supercoder79.cavebiomes.world.feature;


import net.minecraft.block.BlockState;
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
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.layer.LayerGenerator;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

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
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        ChunkGenerator chunkGenerator = context.getGenerator();
        Random random = context.getRandom();

        RegistryKey<World> key = world.toServerWorld().getRegistryKey();
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.getValue().toString())) {
            return false;
        }

        ChunkPos chunkPos = new ChunkPos(pos);
        Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);

        BitSet mask = ((ProtoChunk)chunk).getCarvingMask(GenerationStep.Carver.AIR);
        Set<PosEntry> positions = new HashSet<>();

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int x = 0; x < 16; x++) {
            mutable.setX(pos.getX() + x);
            for (int z = 0; z < 16; z++) {
                mutable.setZ(pos.getZ() + z);
                for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                    mutable.setY(y);
                    boolean generate = false;
                    BlockState state = world.getBlockState(mutable);

                    int packed = x | z << 4 | y << 8;
                    // Check cave mask for initial check
                    if (packed > 0) {
                        if (mask.get(packed)) {
                            if (state.isOf(Blocks.CAVE_AIR)) {
                                generate = true;
                            }
                        }
                    }

                    // If we're not in the cave mask, check if we're below sea level and air, noise cave
                    if (!generate) {
                        if (y < chunkGenerator.getSeaLevel()) {
                            if (state.isAir()) {
                                generate = true;
                            }
                        }
                    }

                    // If generate, add position for chunk generation
                    if (generate) {
                        positions.add(new PosEntry(mutable.toImmutable(), CaveDecorator.DecorationContext.AIR));
                    }
                }
            }
        }

        int threshold = CaveBiomes.CONFIG.caveLayerThreshold;

        Biome biome = chunkGenerator.getBiomeSource().getBiomeForNoiseGen(chunkPos.x << 2, 0, chunkPos.z << 2);

        //regular biome based decoration
        Set<PosEntry> upperPositions = positions.stream().filter(p -> p.pos.getY() > threshold).collect(Collectors.toSet());

        Registry<Biome> biomes = world.toServerWorld().getServer().getRegistryManager().get(Registry.BIOME_KEY);
        CaveDecorator decorator = CaveBiomesAPI.getCaveDecoratorForBiome(biomes, biome);

        OpenSimplexNoise noise = new OpenSimplexNoise(world.getSeed());

        //epic underground biome based decoration
        Set<PosEntry> lowerPositions = positions.stream().filter(p -> p.pos.getY() <= threshold).collect(Collectors.toSet());

        CaveDecorator[] caveBiomes = new CaveDecorator[256];
        BitSet overrides = new BitSet(256);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                CaveDecorator lower = LayerGenerator.getDecorator(world.getSeed(), chunkPos.x * 16 + x, chunkPos.z * 16 + z);
                caveBiomes[x * 16 + z] = lower;
                if (lower.overrideUpper()) {
                    overrides.set(z | x << 4);
                }
            }
        }

        for (PosEntry entry : lowerPositions) {
            BlockPos layerPos = entry.pos;
            random.setSeed((long)layerPos.getX() * 341873128712L + (long)layerPos.getZ() * 132897987541L + (long)layerPos.getY() * 3153265741L);

            caveBiomes[(layerPos.getX() & 15) * 16 + (layerPos.getZ() & 15)].decorate((ChunkRegion) world, random, noise, layerPos, entry.context);
        }

        // TODO: block based generation instead of chunk based
        for (PosEntry entry : upperPositions) {
            BlockPos biomePos = entry.pos;
            int localX = biomePos.getX() & 15;
            int localZ = biomePos.getZ() & 15;

            random.setSeed((long)biomePos.getX() * 341873128712L + (long)biomePos.getZ() * 132897987541L + (long)biomePos.getY() * 3153265741L);

            // If we have an override, generate that
            if (overrides.get(localZ | localX << 4)) {
                caveBiomes[localX * 16 + localZ].decorate((ChunkRegion) world, random, noise, biomePos, entry.context);
            } else {
                decorator.decorate((ChunkRegion) world, random, noise, biomePos, entry.context);
            }
        }

        return false;
    }

    private static class PosEntry {
        private final BlockPos pos;
        private final CaveDecorator.DecorationContext context;

        private PosEntry(BlockPos pos, CaveDecorator.DecorationContext context) {
            this.pos = pos;
            this.context = context;
        }
    }
}
