package supercoder79.cavebiomes.cave;

import net.minecraft.block.Blocks;
import supercoder79.cavebiomes.cave.lower.*;

public class CaveDecorators {
    //global
    public static final CaveDecorator NONE = new DummyCaveDecorator();

    //upper
    public static final CaveDecorator ICE = new IceCaveDecorator();
    public static final CaveDecorator TAIGA = new TaigaCaveDecorator();
    public static final CaveDecorator DIRT_GRASS = new DirtGrassCaveDecorator();
    public static final CaveDecorator SWAMP = new SwampCaveDecorator();
    public static final CaveDecorator JUNGLE = new JungleCaveDecorator();
    public static final CaveDecorator SAND = new SandCaveDecorator();
    public static final CaveDecorator RED_SAND = new RedSandCaveDecorator();

    //lower
    public static final CaveDecorator LAVA = new LavaCaveDecorator();
    public static final CaveDecorator WATER = new WaterCaveDecorator();
    public static final CaveDecorator VINES = new VineCaveDecorator();
    public static final CaveDecorator MUSHROOM = new MushroomCaveDecorator();
    public static final CaveDecorator COBWEB = new CobwebCaveDecorator();
    public static final CaveDecorator ANDESITE = new SingleBlockStateCaveDecorator(Blocks.ANDESITE.getDefaultState());
    public static final CaveDecorator DIORITE = new SingleBlockStateCaveDecorator(Blocks.DIORITE.getDefaultState());
    public static final CaveDecorator GRANITE = new SingleBlockStateCaveDecorator(Blocks.GRANITE.getDefaultState());
    public static final CaveDecorator FULL_OBSIDIAN = new SingleBlockStateCaveDecorator(Blocks.OBSIDIAN.getDefaultState());
    public static final CaveDecorator OBSIDIAN = new ObsidianCaveDecorator();
    public static final CaveDecorator MAGMA = new MagmaCaveDecorator();

    //TODO: big mushrooms
    //TODO: honey cave
}
