package supercoder79.cavebiomes.world.layer;

import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.world.decorator.CaveDecorator;
import supercoder79.cavebiomes.world.layer.newlayers.*;
import supercoder79.cavebiomes.world.layer.newlayers.CaveLayer;

import java.util.List;

public class LayerGenerator {
    private static CaveLayer layer;
    private static long seed;

    public static CaveDecorator getDecorator(long worldSeed, int x, int z) {
        if (layer == null || seed != worldSeed) {
            layer = build(worldSeed);
            seed = worldSeed;
        }

        List<CaveDecorator> decorators = CaveBiomesAPI.getCaveDecorators();
        return decorators.get(layer.sample(x, z));
    }

    private static CaveLayer build(long worldSeed) {
        CaveLayer factory =  new BaseCavesLayer(worldSeed, 100);
        factory = new StoneCaveLayer(worldSeed, 200, factory);
        factory = new RareCaveLayer(worldSeed, 300, factory);

        // Scale upwards
        for (int i = 0; i < CaveBiomes.CONFIG.caveBiomeSize; i++) {
            factory = new ScaleCaveLayer(worldSeed, 10 + i, factory);

            if (i == 1) {
                factory = new OreCaveLayer(worldSeed, 50, factory);
            }
        }

        return factory;
    }
}
