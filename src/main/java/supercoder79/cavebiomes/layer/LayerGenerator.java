package supercoder79.cavebiomes.layer;

import net.minecraft.util.math.ChunkPos;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.util.LayerRandom;

import java.util.List;

public class LayerGenerator {
    public static CaveDecorator getDecorator(long worldSeed, int x, int z) {
        ChunkPos normalized = PositionDistributor.distribute(worldSeed, x >> 2,  z >> 2);
        List<CaveDecorator> decorators = CaveBiomesAPI.getCaveDecorators();
        return decorators.get(operate(new LayerRandom(worldSeed), normalized.x, normalized.z));
    }

    private static int operate(LayerRandom random, int x, int z) {
        int sample = 0;

        // Go through the layers and operate
        List<CaveLayer> layers = CaveBiomesAPI.getCaveLayers();
        for (int i = 0; i < layers.size(); i++) {
            random.setPosSeed(x, z, i);

            sample = layers.get(i).operate(random, sample, x, z);
        }

        return sample;
    }
}
