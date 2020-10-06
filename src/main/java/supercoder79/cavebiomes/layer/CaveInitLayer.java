package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.util.LayerRandom;

import java.util.List;

public class CaveInitLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        random.setPosSeed(x, z, 1);

        List<CaveDecorator> decorators = CaveBiomesAPI.getBaseCaveDecorators();
        CaveDecorator chosen = decorators.get(random.nextInt(decorators.size()));

        return CaveBiomesAPI.indexOf(chosen);
    }
}
