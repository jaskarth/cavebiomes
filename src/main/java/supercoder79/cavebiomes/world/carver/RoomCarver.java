package supercoder79.cavebiomes.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.chunk.AquiferSampler;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

// Creates a small room
public class RoomCarver extends BaseCarver {
    public RoomCarver(Codec<SimpleCarverConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(CarverContext context, SimpleCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, AquiferSampler aquiferSampler, ChunkPos pos, BitSet carvingMask) {
        double x = (pos.x * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (pos.z * 16 + random.nextInt(16));
        float yaw = 1.75F + random.nextFloat() * 6.0F;
        this.carveCave(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, yaw, 0.5D, carvingMask);

        return true;
    }

    protected void carveCave(CarverContext context, SimpleCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, AquiferSampler aquiferSampler, double x, double y, double z, float yaw, double yawPitchRatio, BitSet carvingMask) {
        double d = 1.5D + (double)(MathHelper.sin(1.5707964F) * yaw);
        double e = d * yawPitchRatio;
        this.carveRegion(context, config, chunk, posToBiome, seed, aquiferSampler, x + 1.0D, y, z, yaw, yaw * yawPitchRatio, carvingMask, this::isPositionExcluded);
    }

    protected boolean isPositionExcluded(CarverContext context, double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeY <= -0.7D || // Make room floors flatter
                scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.9D; // Make rooms spherical
    }
}
