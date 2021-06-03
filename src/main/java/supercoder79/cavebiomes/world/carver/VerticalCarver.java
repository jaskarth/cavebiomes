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

public class VerticalCarver extends BaseCarver {
    public VerticalCarver(Codec<SimpleCarverConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(CarverContext context, SimpleCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, AquiferSampler aquiferSampler, ChunkPos pos, BitSet carvingMask) {
        double x = (pos.x * 16 + random.nextInt(16));
        double y = this.getCaveY(random);
        double z = (pos.z * 16 + random.nextInt(16));
        float yaw = 1.5f + 3.5F + random.nextFloat() * 2.0F; // Base val: 1.5
        float pitch = yaw * 0.75f;
        for (int i = 0; i < random.nextInt(4) + 4; i++) {
            this.carveRegion(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x + 1.0D, y, z, yaw, pitch, carvingMask, this::isPositionExcluded);

            float pitchChange = MathHelper.cos(pitch) * 2;
            x += MathHelper.cos(yaw) * pitchChange;
            y -= Math.max(1.25, Math.abs(MathHelper.sin(pitch) * 3)); // Force caves downwards
            z += MathHelper.sin(yaw) * pitchChange;

            yaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.75F;
            pitch += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.75F;
        }

        return true;
    }

    protected boolean isPositionExcluded(CarverContext context, double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.8D;
    }
}
