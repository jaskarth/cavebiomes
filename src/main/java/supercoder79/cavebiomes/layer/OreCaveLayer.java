package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.magic.LayerRandom;

public class OreCaveLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        if (random.nextInt(4) == 0) {
            int i = random.nextInt(6);
            if (i == 0) return CaveBiomesAPI.indexOf(CaveDecorators.COAL);
            if (i == 1) return CaveBiomesAPI.indexOf(CaveDecorators.IRON);
            if (i == 2) return CaveBiomesAPI.indexOf(CaveDecorators.GOLD);
            if (i == 3) return CaveBiomesAPI.indexOf(CaveDecorators.REDSTONE);
            if (i == 4) return CaveBiomesAPI.indexOf(CaveDecorators.LAPIS);
            return CaveBiomesAPI.indexOf(CaveDecorators.DIAMOND);
        }

        return parent;
    }
}
