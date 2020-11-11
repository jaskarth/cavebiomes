package supercoder79.cavebiomes.world.feature;

import com.google.common.collect.ImmutableSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.util.FloodFill;

import java.util.Random;

public class LocalWaterLevelsFeature extends Feature<DefaultFeatureConfig> {
    public LocalWaterLevelsFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        RegistryKey<World> key = world.toServerWorld().getRegistryKey();
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.getValue().toString())) {
            return false;
        }

        ((ChunkRandom)random).setPopulationSeed(world.getSeed(), pos.getX(), pos.getZ());

        int x = pos.getX() + random.nextInt(16);
        int z = pos.getZ() + random.nextInt(16);
        int y = random.nextInt(50) + 12;
        FloodFill.floodFill((ChunkRegion) world, random, new BlockPos(x, y, z), ImmutableSet.of());
        return true;
    }
}
