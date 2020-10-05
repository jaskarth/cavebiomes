package supercoder79.cavebiomes.carver;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

import java.util.Random;

public abstract class BaseCarver extends Carver<ProbabilityConfig> {
    public BaseCarver(Codec<ProbabilityConfig> configCodec) {
        super(configCodec, 256);
    }

    protected int getCaveY(Random random) {
        return random.nextInt(random.nextInt(120) + 8);
    }

    @Override
    public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config) {
        return random.nextFloat() <= config.probability;
    }
}
