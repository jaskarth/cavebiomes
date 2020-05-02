package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.magic.LayerRandom;

public class OreLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        random.setPosSeed(x, z, 4);
        if (parent == -3) { //ore layer marker
            int i = random.nextInt(6);
            if (i == 0) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.COAL);
            if (i == 1) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.IRON);
            if (i == 2) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.GOLD);
            if (i == 3) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.REDSTONE);
            if (i == 4) return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.LAPIS);
            return LayerHolder.MASTER_DECORATOR_LIST.indexOf(CaveDecorators.DIAMOND);
        }

        return parent;
    }
}
