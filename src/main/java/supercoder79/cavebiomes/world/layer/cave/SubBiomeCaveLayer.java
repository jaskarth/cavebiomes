package supercoder79.cavebiomes.world.layer.cave;

import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;
import supercoder79.cavebiomes.world.layer.SamplingCaveLayer;

public class SubBiomeCaveLayer extends SamplingCaveLayer {
    public SubBiomeCaveLayer(long worldSeed, int salt) {
        super(worldSeed, salt);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int sample) {
        if (random.nextInt(3) == 0 && sample == CaveBiomesAPI.indexOf(CaveDecorators.OBSIDIAN) && CaveBiomes.CONFIG.generateFullObsidianCaves) {
            return CaveBiomesAPI.indexOf(CaveDecorators.FULL_OBSIDIAN);
        }

        return sample;
    }
}
