package supercoder79.cavebiomes.world.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CarverDebugConfig;

public class SimpleCarverConfig extends CarverConfig {
    public static final Codec<SimpleCarverConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("probability").forGetter(c -> c.probability)
    ).apply(instance, SimpleCarverConfig::new));

    public SimpleCarverConfig(float probability) {
        super(probability, null, null, YOffset.aboveBottom(10), false, CarverDebugConfig.create(false, Blocks.CAVE_AIR.getDefaultState()));
    }
}
