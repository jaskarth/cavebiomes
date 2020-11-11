package supercoder79.cavebiomes.world.layer.newlayers;

import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import supercoder79.cavebiomes.util.LayerRandom;

import java.util.Arrays;

public abstract class CaveLayer {

    protected final long worldSeed;
    protected final int salt;
    private final ThreadLocal<Cache> cache;

    public CaveLayer(long worldSeed, int salt) {
        this.worldSeed = worldSeed;
        this.salt = salt;
        this.cache = ThreadLocal.withInitial(this::initializeCache);
    }

    public int sample(int x, int z) {
        return cache.get().get(x, z);
    }

    protected int choose(LayerRandom random, int a, int b, int c, int d) {
        int i = random.nextInt(4);
        if (i == 0) {
            return a;
        } else if (i == 1) {
            return b;
        } else {
            return i == 2 ? c : d;
        }
    }

    protected int choose(LayerRandom random, int a, int b) {
        return random.nextInt(2) == 0 ? a : b;
    }

    protected abstract int operate(LayerRandom random, int x, int z);

    protected Cache initializeCache() {
        return new Cache(256);
    }

    protected class Cache {
        protected final long[] keys;
        protected final int[] values;

        protected final int mask;

        protected Cache(int size) {
            size = MathHelper.smallestEncompassingPowerOfTwo(size);
            this.mask = size - 1;

            this.keys = new long[size];
            Arrays.fill(this.keys, Long.MIN_VALUE);
            this.values = new int[size];
        }

        public final int get(int x, int z) {
            long key = key(x, z);
            int idx = hash(key) & this.mask;

            // if the entry here has a key that matches ours, we have a cache hit
            if (this.keys[idx] == key) {
                return this.values[idx];
            }

            int sampled = getValueForSample(x, z);

            this.values[idx] = sampled;
            this.keys[idx] = key;

            return sampled;
        }

        protected int getValueForSample(int x, int z){
            // cache miss: sample the source and put the result into our cache entry
            LayerRandom random = new LayerRandom(worldSeed);
            random.setPosSeed(x, z, salt);

            return operate(random, x, z);
        }

        protected final int hash(long key) {
            return (int) HashCommon.mix(key);
        }

        protected final long key(int x, int z) {
            return ChunkPos.toLong(x, z);
        }
    }
}
