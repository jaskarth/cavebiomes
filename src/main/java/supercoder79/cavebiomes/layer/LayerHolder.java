package supercoder79.cavebiomes.layer;

import net.minecraft.util.Pair;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.magic.LayerRandom;

import java.util.ArrayList;
import java.util.List;

public class LayerHolder {
    static final List<CaveDecorator> BASE_DECORATOR_LIST = new ArrayList<>();
    static final List<CaveDecorator> MASTER_DECORATOR_LIST = new ArrayList<>();
    static {
        //init layer
        BASE_DECORATOR_LIST.add(CaveDecorators.NONE);
        BASE_DECORATOR_LIST.add(CaveDecorators.WATER);
        BASE_DECORATOR_LIST.add(CaveDecorators.LAVA);
        BASE_DECORATOR_LIST.add(CaveDecorators.VINES);
        BASE_DECORATOR_LIST.add(CaveDecorators.OBSIDIAN);
        BASE_DECORATOR_LIST.add(CaveDecorators.MAGMA);
        BASE_DECORATOR_LIST.add(CaveDecorators.COBBLESTONE);
        BASE_DECORATOR_LIST.add(CaveDecorators.GRAVEL);

        MASTER_DECORATOR_LIST.addAll(BASE_DECORATOR_LIST);

        //stone layers
        MASTER_DECORATOR_LIST.add(CaveDecorators.ANDESITE);
        MASTER_DECORATOR_LIST.add(CaveDecorators.DIORITE);
        MASTER_DECORATOR_LIST.add(CaveDecorators.GRANITE);

        //rare layers
        MASTER_DECORATOR_LIST.add(CaveDecorators.COBWEB);
        MASTER_DECORATOR_LIST.add(CaveDecorators.FULL_OBSIDIAN);
        MASTER_DECORATOR_LIST.add(CaveDecorators.MUSHROOM);

        //ore layers
        MASTER_DECORATOR_LIST.add(CaveDecorators.COAL);
        MASTER_DECORATOR_LIST.add(CaveDecorators.IRON);
        MASTER_DECORATOR_LIST.add(CaveDecorators.GOLD);
        MASTER_DECORATOR_LIST.add(CaveDecorators.REDSTONE);
        MASTER_DECORATOR_LIST.add(CaveDecorators.LAPIS);
        MASTER_DECORATOR_LIST.add(CaveDecorators.DIAMOND);
    }

    public static CaveDecorator getDecorator(long worldSeed, int x, int z) {
        Pair<Integer, Integer> pair = VoronoiAccess.getVoronoi(worldSeed, x >> 2, 0, z >> 2);
        return MASTER_DECORATOR_LIST.get(operate(new LayerRandom(worldSeed), pair.getLeft(), pair.getRight()));
    }

    private static int operate(LayerRandom random, int x, int z) {
        int sample = INIT_LAYER.operate(random, 0, x, z);;
        sample = STONE_LAYER.operate(random, sample, x, z);
        sample = RARE_CAVE_LAYER.operate(random, sample, x, z);
        sample = ORE_LAYER.operate(random, sample, x, z);
        return sample;
    }


    private static final CaveLayer INIT_LAYER = new CaveInitLayer();
    private static final CaveLayer STONE_LAYER = new RandomStoneLayer();
    private static final CaveLayer RARE_CAVE_LAYER = new RareCaveLayer();
    private static final CaveLayer ORE_LAYER = new OreLayer();
}
