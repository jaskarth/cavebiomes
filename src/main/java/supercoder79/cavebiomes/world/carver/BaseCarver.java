package supercoder79.cavebiomes.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.carver.Carver;

import java.util.Random;

public abstract class BaseCarver extends Carver<SimpleCarverConfig> {
    public BaseCarver(Codec<SimpleCarverConfig> configCodec) {
        super(configCodec);
    }

    protected int getCaveY(Random random) {
        return random.nextInt(random.nextInt(120) + 8);
    }

    @Override
    public boolean shouldCarve(SimpleCarverConfig config, Random random) {
        return random.nextFloat() <= config.probability;
    }
}
