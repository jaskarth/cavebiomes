package supercoder79.cavebiomes.cave;

import net.minecraft.block.Blocks;
import supercoder79.cavebiomes.cave.lower.*;

public class CaveDecorators {
    //global
    public static final CaveDecorator NONE = new NoOpCaveDecorator();

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
    public static final CaveDecorator OBSIDIAN = new RandomBlockStateCaveDecorator(Blocks.OBSIDIAN.getDefaultState(), 8);
    public static final CaveDecorator MAGMA = new RandomBlockStateCaveDecorator(Blocks.MAGMA_BLOCK.getDefaultState(), 12);
    public static final CaveDecorator COBBLESTONE = new RandomBlockStateCaveDecorator(Blocks.COBBLESTONE.getDefaultState(), 6);
    public static final CaveDecorator GRAVEL = new GravelCaveDecorator();

    //ore caves (lower)
    public static final CaveDecorator COAL = new RandomBlockStateCaveDecorator(Blocks.COAL_ORE.getDefaultState(), 14);
    public static final CaveDecorator IRON = new RandomBlockStateCaveDecorator(Blocks.IRON_ORE.getDefaultState(), 16);
    public static final CaveDecorator GOLD = new RandomBlockStateCaveDecorator(Blocks.GOLD_ORE.getDefaultState(), 24);
    public static final CaveDecorator REDSTONE = new RandomBlockStateCaveDecorator(Blocks.REDSTONE_ORE.getDefaultState(), 12);
    public static final CaveDecorator LAPIS = new RandomBlockStateCaveDecorator(Blocks.LAPIS_ORE.getDefaultState(), 16);
    public static final CaveDecorator DIAMOND = new RandomBlockStateCaveDecorator(Blocks.DIAMOND_ORE.getDefaultState(), 48);

    //TODO: big mushrooms
    //TODO: honey cave
    //TODO: emerald
}
