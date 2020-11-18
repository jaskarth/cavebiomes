package supercoder79.cavebiomes.world.layer;

import supercoder79.cavebiomes.util.LayerRandom;

public class SmoothLayer extends CrossSamplingCaveLayer {
    public SmoothLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int n, int e, int s, int w, int center) {
        boolean bl = e == w;
        boolean bl2 = n == s;
        if (bl == bl2) {
            if (bl) {
                return random.nextInt(2) == 0 ? w : n;
            } else {
                return center;
            }
        } else {
            return bl ? w : n;
        }
    }
}
