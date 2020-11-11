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

// Creates a small room
public class RoomCarver extends BaseCarver {
    public RoomCarver(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {
        double x = (chunkX * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (chunkZ * 16 + random.nextInt(16));
        float yaw = 1.75F + random.nextFloat() * 6.0F;
        this.carveCave(chunk, posToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, yaw, 0.5D, carvingMask);

        return true;
    }

    protected void carveCave(Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float yaw, double yawPitchRatio, BitSet carvingMask) {
        double d = 1.5D + (double)(MathHelper.sin(1.5707964F) * yaw);
        double e = d * yawPitchRatio;
        this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x + 1.0D, y, z, d, e, carvingMask);
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeY <= -0.7D || // Make room floors flatter
                scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.9D; // Make rooms spherical
    }
}
