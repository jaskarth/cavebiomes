package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.magic.LayerRandom;

public class RareCaveLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int prior, int x, int z) {
        random.setPosSeed(x, z, 3);
        if (prior == -2) { //stone layer marker
            int i = random.nextInt(3);
            if (i == 0) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.FULL_OBSIDIAN);
            if (i == 1) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.MUSHROOM);
            return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.COBWEB);
        }

        return prior;
    }
}
