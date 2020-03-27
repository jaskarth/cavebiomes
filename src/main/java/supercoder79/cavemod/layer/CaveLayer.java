package supercoder79.cavemod.layer;

import supercoder79.cavemod.magic.LayerRandom;

public abstract class CaveLayer {
    public abstract int operate(LayerRandom random, int prior, int x, int z);
}
