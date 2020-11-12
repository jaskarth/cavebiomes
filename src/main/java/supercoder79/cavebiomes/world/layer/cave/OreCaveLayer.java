package supercoder79.cavebiomes.world.layer.cave;

import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.util.LayerRandom;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;
import supercoder79.cavebiomes.world.layer.CaveLayer;
import supercoder79.cavebiomes.world.layer.SamplingCaveLayer;

public class OreCaveLayer extends SamplingCaveLayer {
    public OreCaveLayer(long worldSeed, int salt, CaveLayer parent) {
        super(worldSeed, salt, parent);
    }

    @Override
    protected int operate(LayerRandom random, int x, int z, int sample) {
        if (CaveBiomes.CONFIG.generateOreCaves && random.nextInt(32) == 0) {
            int i = random.nextInt(6);
            if (i == 0) return CaveBiomesAPI.indexOf(CaveDecorators.COAL);
            if (i == 1) return CaveBiomesAPI.indexOf(CaveDecorators.IRON);
            if (i == 2) return CaveBiomesAPI.indexOf(CaveDecorators.GOLD);
            if (i == 3) return CaveBiomesAPI.indexOf(CaveDecorators.REDSTONE);
            if (i == 4) return CaveBiomesAPI.indexOf(CaveDecorators.LAPIS);
            return CaveBiomesAPI.indexOf(CaveDecorators.DIAMOND);
        }

        return sample;
    }
}
