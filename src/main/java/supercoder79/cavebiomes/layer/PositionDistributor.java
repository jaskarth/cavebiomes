package supercoder79.cavebiomes.layer;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.source.SeedMixer;
import supercoder79.cavebiomes.util.LayerRandom;

public class PositionDistributor {
    public static ChunkPos distribute(long seed, int x, int z) {
        LayerRandom random = new LayerRandom(seed);
        random.setPosSeed(x, z);
        x += random.nextInt(7) - 3;
        z += random.nextInt(7) - 3;

        int i = x - 2;
        int k = z - 2;
        int l = i >> 2;
        int n = k >> 2;
        double d = (double)(i & 3) / 4.0D;
        double f = (double)(k & 3) / 4.0D;
        double[] ds = new double[8];

        int t;
        int aa;
        for(t = 0; t < 8; ++t) {
            boolean bl = (t & 4) == 0;
            boolean bl2 = (t & 2) == 0;
            boolean bl3 = (t & 1) == 0;
            aa = bl ? l : l + 1;
            int r = bl3 ? n : n + 1;
            double g = bl ? d : d - 1.0D;
            double s = bl3 ? f : f - 1.0D;
            ds[t] = calcChance(seed, aa, r, g, s);
        }

        t = 0;
        double u = ds[0];

        int v;
        for(v = 1; v < 8; ++v) {
            if (u > ds[v]) {
                t = v;
                u = ds[v];
            }
        }

        v = (t & 4) == 0 ? l : l + 1;
        int ab = (t & 1) == 0 ? n : n + 1;
        return new ChunkPos(v, ab);
    }

    private static double calcChance(long seed, int x, int z, double xFraction, double zFraction) {
        long l = SeedMixer.mixSeed(seed, (long)x);
        l = SeedMixer.mixSeed(l, (long)z);
        l = SeedMixer.mixSeed(l, (long)x);
        l = SeedMixer.mixSeed(l, (long)z);
        double d = distribute(l);
        l = SeedMixer.mixSeed(l, seed);
        l = SeedMixer.mixSeed(l, seed);
        double f = distribute(l);
        return sqr(zFraction + f) + sqr(xFraction + d);
    }

    private static double distribute(long seed) {
        double d = (double)((int)Math.floorMod(seed >> 24, 1024L)) / 1024.0D;
        return (d - 0.5D) * 0.9D;
    }

    private static double sqr(double d) {
        return d * d;
    }
}
