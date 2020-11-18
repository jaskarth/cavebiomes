package supercoder79.cavebiomes.world.layer;

import supercoder79.cavebiomes.util.LayerRandom;

public abstract class SamplingCaveLayer extends CaveLayer {
    protected CaveLayer parent;

    public SamplingCaveLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
        this.parent = null;
    }

    public SamplingCaveLayer setParent(CaveLayer parent) {
        if (this.parent == null) {
            this.parent = parent;
        } else {
            throw new IllegalStateException("Cannot modify existing parent!");
        }

        return this;
    }

    @Override
    protected int operate(LayerRandom random, int x, int z) {
        throw new UnsupportedOperationException("Call the correct operate method!");
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
