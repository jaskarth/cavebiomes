package supercoder79.cavebiomes.world.layer.cave;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.layer.CaveLayer;

import java.util.List;

public class BaseCavesLayer extends CaveLayer {
    public BaseCavesLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z) {
        List<CaveDecorator> decorators = CaveBiomesAPI.getBaseCaveDecorators();
        CaveDecorator chosen = decorators.get(random.nextInt(decorators.size()));

        return CaveBiomesAPI.indexOf(chosen);
    }
}
