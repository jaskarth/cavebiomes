package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.magic.LayerRandom;

public class RandomOreLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        return parent;
    }
}
