package supercoder79.cavebiomes.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.chunk.AquiferSampler;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class LavaRoomCarver extends BaseCarver {
    public LavaRoomCarver(Codec<SimpleCarverConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(CarverContext context, SimpleCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, AquiferSampler aquiferSampler, ChunkPos pos, BitSet carvingMask) {
        double x = (pos.x * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (pos.z * 16 + random.nextInt(16));
        float yaw = 12.5f + random.nextFloat() * 7.5F;
        float pitch = yaw * 0.825f;

        double initialX = x;
        double initialY = y;
        double initialZ = z;

        for (int i = 0; i < random.nextInt(4) + 3; i++) {
            this.carveRegion(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x + 1.0D, y, z, yaw, pitch, carvingMask, this::isPositionExcluded);

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


    protected boolean isPositionExcluded(CarverContext context, double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.5D;
    }
}
