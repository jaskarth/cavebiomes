package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.magic.LayerRandom;

public class CaveInitLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        random.setPosSeed(x, z, 1);

        //marker for stone caves
        if (random.nextInt(5) == 0) return -1;

        //marker for rare caves
        if (random.nextInt(5) == 0) return -2;

        if (CaveBiomes.CONFIG.generateOreCaves) {
            //marker for ore caves
            if (random.nextInt(4) == 0) return -3;
        }

        return random.nextInt(LayerHolder.BASE_DECORATOR_LIST.size());
    }
}
