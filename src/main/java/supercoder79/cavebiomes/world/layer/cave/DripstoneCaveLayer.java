package supercoder79.cavebiomes.world.layer.cave;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;
import supercoder79.cavebiomes.world.layer.SamplingCaveLayer;

public class DripstoneCaveLayer extends SamplingCaveLayer {
    public DripstoneCaveLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int sample) {
        random.setPosSeed(x >> 1, z >> 1, this.salt);
        if (random.nextInt(8) == 0) {
            return CaveBiomesAPI.indexOf(CaveDecorators.DRIPSTONE);
        }

        return sample;
    }
}
