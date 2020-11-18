package supercoder79.cavebiomes.world.layer.water;

import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.layer.CaveLayer;

public class WaterInitLayer extends CaveLayer {
    public WaterInitLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z) {
        return random.nextInt(4) == 0 ? random.nextInt(10) + 12: 0;
    }
}
