package supercoder79.cavebiomes.layer;

import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.magic.LayerRandom;

public class RareCaveLayer extends CaveLayer {
    @Override
    public int operate(LayerRandom random, int parent, int x, int z) {
        if (random.nextInt(5) == 0) {
            int i = random.nextInt(3);
            if (i == 0) return CaveBiomes.CONFIG.generateFullObsidianCaves ? CaveBiomesAPI.indexOf(CaveDecorators.FULL_OBSIDIAN) : CaveBiomesAPI.indexOf(CaveDecorators.OBSIDIAN);
            if (i == 1) return CaveBiomesAPI.indexOf(CaveDecorators.MUSHROOM);
            return CaveBiomesAPI.indexOf(CaveDecorators.COBWEB);
        }

        return parent;
    }
}
