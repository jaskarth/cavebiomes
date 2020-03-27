package supercoder79.cavemod.layer;

import supercoder79.cavemod.cave.CaveDecorators;
import supercoder79.cavemod.magic.LayerRandom;

public class RandomStoneLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int prior, int x, int z) {
        random.setPosSeed(x, z, 2);
        if (prior == -1) { //stone layer marker
            int i = random.nextInt(3);
            if (i == 0) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.ANDESITE);
            if (i == 1) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.GRANITE);
            else return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.DIORITE);
        }

        return prior;
    }
}
