package supercoder79.cavebiomes.world.layer.cave;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;
import supercoder79.cavebiomes.world.layer.CaveLayer;
import supercoder79.cavebiomes.world.layer.SamplingCaveLayer;

public class StoneCaveLayer extends SamplingCaveLayer {
    public StoneCaveLayer(long worldSeed, int salt, CaveLayer parent) {
        super(worldSeed, salt, parent);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int sample) {
        if (random.nextInt(5) == 0) {
            int i = random.nextInt(3);
            if (i == 0) return CaveBiomesAPI.indexOf(CaveDecorators.ANDESITE);
            if (i == 1) return CaveBiomesAPI.indexOf(CaveDecorators.GRANITE);
            else return CaveBiomesAPI.indexOf(CaveDecorators.DIORITE);
        }

        return sample;
    }
}
