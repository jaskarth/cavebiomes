package supercoder79.cavebiomes.world.layer;

import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.impl.CaveBiomesImpl;
import supercoder79.cavebiomes.world.layer.cave.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        LayerDispatcher dispatcher = new LayerDispatcher();
        List<LayerDispatcher.DispatchFunction> functions = CaveBiomesImpl.getDispatchFunctions();

        for (LayerDispatcher.DispatchFunction function : functions) {
            function.apply(dispatcher, worldSeed);
        }

        Map<Integer, List<SamplingCaveLayer>> layers = dispatcher.getLayers();

        CaveLayer factory =  new BaseCavesLayer(worldSeed, 100);

        // -1 is the marker for base cave layers. I am good at coding
        for (SamplingCaveLayer layer : layers.getOrDefault(-1, new ArrayList<>())) {
            layer.setParent(factory);

            factory = layer;
        }

        // Scale upwards
        for (int i = 0; i < CaveBiomes.CONFIG.caveBiomeSize; i++) {
            factory = new ScaleCaveLayer(worldSeed, 10 + i, factory);

            for (SamplingCaveLayer layer : layers.getOrDefault(i, new ArrayList<>())) {
                layer.setParent(factory);

                factory = layer;
            }
        }

        return factory;
    }
}
