package supercoder79.cavebiomes.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class VerticalCarver extends BaseCarver {
    public VerticalCarver(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {
        double x = (chunkX * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (chunkZ * 16 + random.nextInt(16));
        float yaw = 1.5f + 3.5F + random.nextFloat() * 2.0F; // Base val: 1.5
        float pitch = yaw * 0.75f;
        for (int i = 0; i < random.nextInt(4) + 4; i++) {
            this.carveRegion(chunk, posToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x + 1.0D, y, z, yaw, pitch, carvingMask);

            float pitchChange = MathHelper.cos(pitch) * 2;
            x += MathHelper.cos(yaw) * pitchChange;
            y -= Math.max(1.25, Math.abs(MathHelper.sin(pitch) * 3)); // Force caves downwards
            z += MathHelper.sin(yaw) * pitchChange;

            yaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.75F;
            pitch += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.75F;
        }

        return true;
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.8D;
    }
}
