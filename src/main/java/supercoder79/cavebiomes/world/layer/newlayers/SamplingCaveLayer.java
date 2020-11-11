package supercoder79.cavebiomes.world.layer.newlayers;

import supercoder79.cavebiomes.util.LayerRandom;

public abstract class SamplingCaveLayer extends CaveLayer {
    protected final CaveLayer parent;

    public SamplingCaveLayer(long worldSeed, int salt, CaveLayer parent) {
        super(worldSeed, salt);
        this.parent = parent;
    }

    @Override
    protected int operate(LayerRandom random, int x, int z) {
        return -1; // No-op: use abstract method
    }

    @Override
    protected Cache initializeCache() {
        return new SamplingCache(512);
    }

    protected abstract int operate(LayerRandom random, int x, int z, int sample);

    private class SamplingCache extends Cache {
        private SamplingCache(int size) {
            super(size);
        }

        @Override
        protected int getValueForSample(int x, int z) {
            LayerRandom random = new LayerRandom(worldSeed);
            random.setPosSeed(x, z, salt);

            int parentSample = parent.sample(x, z);

            return operate(random, x, z, parentSample);
        }
    }
}
