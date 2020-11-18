package supercoder79.cavebiomes.world.layer.water;

import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.layer.CrossSamplingCaveLayer;

public class WaterBorderLayer extends CrossSamplingCaveLayer {

    public WaterBorderLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int n, int e, int s, int w, int center) {
        int max = Math.max(Math.max(n, e), Math.max(s, w));
        if (max > center) {
            return center | max << 8;
        }

        return center;
    }
}
