package supercoder79.cavebiomes.world.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayerDispatcher {
    private final Map<Integer, List<SamplingCaveLayer>> layers = new HashMap<>();

    public void addLayer(int depth, SamplingCaveLayer layer) {
        List<SamplingCaveLayer> list = layers.getOrDefault(depth, new ArrayList<>());
        list.add(layer);

        layers.put(depth, list);
    }

    public void addBaseLayer(SamplingCaveLayer layer) {
        addLayer(-1, layer);
    }

    public Map<Integer, List<SamplingCaveLayer>> getLayers() {
        return layers;
    }

    @FunctionalInterface
    public interface DispatchFunction {
        void apply(LayerDispatcher dispatcher, long seed);
    }
}
