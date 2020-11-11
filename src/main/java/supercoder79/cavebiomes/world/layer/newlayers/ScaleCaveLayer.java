package supercoder79.cavebiomes.world.layer.newlayers;

import supercoder79.cavebiomes.util.LayerRandom;

public class ScaleCaveLayer extends CaveLayer {
    private final CaveLayer parent;

    public ScaleCaveLayer(long worldSeed, int salt, CaveLayer parent) {
        super(worldSeed, salt);
        this.parent = parent;
    }

    @Override
    protected int operate(LayerRandom random, int x, int z) {
        int tl = parent.sample(this.transformX(x), this.transformZ(z));
        int ix = x & 1;
        int iz = z & 1;

        if (ix == 0 && iz == 0) {
            return tl;
        }

        random.setPosSeed(x & ~1, z & ~1, this.salt);

        if (ix == 0) {
            int bl = parent.sample(this.transformX(x), this.transformZ(z + 1));
            return choose(random, tl, bl);
        }

        if (iz == 0) {
            int tr = parent.sample(this.transformX(x + 1), this.transformZ(z));
            return choose(random, tl, tr);
        }

        int bl = parent.sample(this.transformX(x), this.transformZ(z + 1));
        int tr = parent.sample(this.transformX(x + 1), this.transformZ(z));
        int br = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));

        return this.sample(random, tl, tr, bl, br);
    }

    public int transformX(int x) {
        return x >> 1;
    }

    public int transformZ(int y) {
        return y >> 1;
    }

    protected int sample(LayerRandom random, int i, int j, int k, int l) {
        if (j == k && k == l) {
            return j;
        } else if (i == j && i == k) {
            return i;
        } else if (i == j && i == l) {
            return i;
        } else if (i == k && i == l) {
            return i;
        } else if (i == j && k != l) {
            return i;
        } else if (i == k && j != l) {
            return i;
        } else if (i == l && j != k) {
            return i;
        } else if (j == k && i != l) {
            return j;
        } else if (j == l && i != k) {
            return j;
        } else {
            return k == l && i != j ? k : choose(random, i, j, k, l);
        }
    }
}
