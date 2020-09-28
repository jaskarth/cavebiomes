package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.magic.LayerRandom;

public class RandomStoneLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        if (random.nextInt(5) == 0) {
            int i = random.nextInt(3);
            if (i == 0) return CaveBiomesAPI.indexOf(CaveDecorators.ANDESITE);
            if (i == 1) return CaveBiomesAPI.indexOf(CaveDecorators.GRANITE);
            else return CaveBiomesAPI.indexOf(CaveDecorators.DIORITE);
        }

        return parent;
    }
}
