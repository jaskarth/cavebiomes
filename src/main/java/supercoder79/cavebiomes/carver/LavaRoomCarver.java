package supercoder79.cavebiomes.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class LavaRoomCarver extends BaseCarver {
    public LavaRoomCarver(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {
        double x = (chunkX * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (chunkZ * 16 + random.nextInt(16));
        float yaw = 12.5f + random.nextFloat() * 7.5F;
        float pitch = yaw * 0.825f;

        double initialX = x;
        double initialY = y;
        double initialZ = z;

        for (int i = 0; i < random.nextInt(4) + 3; i++) {
            this.carveRegion(chunk, posToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x + 1.0D, y, z, yaw, pitch, carvingMask);

            x = initialX + (random.nextDouble() - random.nextDouble()) * yaw;
            y = initialY + (random.nextDouble() - random.nextDouble()) * (pitch / 2.0);
            z = initialZ + (random.nextDouble() - random.nextDouble()) * yaw;

            yaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.5F;
            pitch += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.5F;
        }

        return true;
    }

    @Override
    protected int getCaveY(Random random) {
        return 12 + random.nextInt(12);
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.5D;
    }
}
