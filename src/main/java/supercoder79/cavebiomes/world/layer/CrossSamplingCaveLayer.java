package supercoder79.cavebiomes.world.layer;

import supercoder79.cavebiomes.util.LayerRandom;

public abstract class CrossSamplingCaveLayer extends SamplingCaveLayer {
    public CrossSamplingCaveLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int sample) {
        throw new UnsupportedOperationException("Call the correct operate method!");
    }

    @Override
    protected Cache initializeCache() {
        return new CrossSamplingCache(512);
    }

    protected abstract int operate(LayerRandom random, int x, int z, int n, int e, int s, int w, int center);

    private class CrossSamplingCache extends Cache {
        private CrossSamplingCache(int size) {
            super(size);
        }

        @Override
        protected int getValueForSample(int x, int z) {
            LayerRandom random = new LayerRandom(worldSeed);
            random.setPosSeed(x, z, salt);

            int centerSample = parent.sample(x, z);
            int northSample = parent.sample(x, z - 1);
            int eastSample = parent.sample(x + 1, z);
            int southSample = parent.sample(x, z + 1);
            int westSample = parent.sample(x - 1, z);

            return operate(random, x, z, northSample, eastSample, southSample, westSample, centerSample);
        }
    }
}
