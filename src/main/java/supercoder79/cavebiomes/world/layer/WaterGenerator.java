package supercoder79.cavebiomes.world.layer;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.layer.water.WaterBorderLayer;
import supercoder79.cavebiomes.world.layer.water.WaterInitLayer;

import java.util.List;

public class WaterGenerator {
    public static final int BORDER_BITS = (255 << 8);
    private static CaveLayer layer;
    private static long seed;

    public static int getSample(long worldSeed, int x, int z) {
        if (layer == null || seed != worldSeed) {
            layer = build(worldSeed);
            seed = worldSeed;
        }

        return layer.sample(x, z);
    }

    public static int getWaterLevel(int sample) {
        return sample & 255;
    }

    public static int getBorder(int sample) {
        return (sample & WaterGenerator.BORDER_BITS) >> 8;
    }

    private static CaveLayer build(long seed) {
        CaveLayer factory = new WaterInitLayer(seed, 150);

        for (int i = 0; i < 8; i++) {
            factory = new ScaleCaveLayer(seed, 100 + i, factory);
        }

        factory = new WaterBorderLayer(seed, 3).setParent(factory);

        return factory;
    }
}
