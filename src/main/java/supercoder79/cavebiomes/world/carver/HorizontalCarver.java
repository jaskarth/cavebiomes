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

public class HorizontalCarver extends BaseCarver {
    public HorizontalCarver(Codec<SimpleCarverConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(CarverContext context, SimpleCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, AquiferSampler aquiferSampler, ChunkPos pos, BitSet carvingMask) {
        double x = (pos.x * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (pos.z * 16 + random.nextInt(16));
        float yaw = 1.5f + 4.5F + random.nextFloat() * 3.5F; // Base val: 1.5
        float pitch = yaw * 0.35f * (random.nextFloat() + 0.65f);

        for (int i = 0; i < random.nextInt(4) + 6; i++) {
            this.carveRegion(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x + 1.0D, y, z, yaw, pitch, carvingMask, this::isPositionExcluded);

            x += (random.nextDouble() - random.nextDouble()) * yaw;
            y += MathHelper.sin(pitch) * 0.25;
            z += (random.nextDouble() - random.nextDouble()) * yaw;

            yaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.5F;
            pitch += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.5F;
        }

        return true;
    }

    protected boolean isPositionExcluded(CarverContext context, double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.65D;
    }
}
