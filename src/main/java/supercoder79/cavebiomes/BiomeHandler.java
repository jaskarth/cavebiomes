package supercoder79.cavebiomes;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import supercoder79.cavebiomes.cave.CaveDecorators;

import static supercoder79.cavebiomes.CaveBiomes.putCaveDecorator;

public class BiomeHandler {
    public static void addVanillaBiomes() {
        //ice biomes
        putCaveDecorator(BiomeKeys.ICE_SPIKES, CaveDecorators.ICE);
        putCaveDecorator(BiomeKeys.SNOWY_BEACH, CaveDecorators.ICE);
        putCaveDecorator(BiomeKeys.SNOWY_MOUNTAINS, CaveDecorators.ICE);
        putCaveDecorator(BiomeKeys.SNOWY_TAIGA, CaveDecorators.ICE);
        putCaveDecorator(BiomeKeys.SNOWY_TAIGA_HILLS, CaveDecorators.ICE);
        putCaveDecorator(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, CaveDecorators.ICE);
        putCaveDecorator(BiomeKeys.SNOWY_TUNDRA, CaveDecorators.ICE);

        //taiga biomes
        putCaveDecorator(BiomeKeys.TAIGA, CaveDecorators.TAIGA);
        putCaveDecorator(BiomeKeys.TAIGA_HILLS, CaveDecorators.TAIGA);
        putCaveDecorator(BiomeKeys.TAIGA_MOUNTAINS, CaveDecorators.TAIGA);
        putCaveDecorator(BiomeKeys.GIANT_SPRUCE_TAIGA, CaveDecorators.TAIGA);
        putCaveDecorator(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, CaveDecorators.TAIGA);
        putCaveDecorator(BiomeKeys.GIANT_TREE_TAIGA, CaveDecorators.TAIGA);
        putCaveDecorator(BiomeKeys.GIANT_TREE_TAIGA_HILLS, CaveDecorators.TAIGA);

        //grassy biomes (fallback)
        putCaveDecorator(BiomeKeys.PLAINS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.FOREST, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.WOODED_HILLS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.BIRCH_FOREST, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.BIRCH_FOREST_HILLS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.TALL_BIRCH_FOREST, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.TALL_BIRCH_HILLS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.DARK_FOREST, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.DARK_FOREST_HILLS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.MOUNTAINS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.MOUNTAIN_EDGE, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.GRAVELLY_MOUNTAINS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.WOODED_MOUNTAINS, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.SAVANNA, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.SAVANNA_PLATEAU, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.SHATTERED_SAVANNA, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.FLOWER_FOREST, CaveDecorators.DIRT_GRASS);
        putCaveDecorator(BiomeKeys.SUNFLOWER_PLAINS, CaveDecorators.DIRT_GRASS);

        //swamp biomes
        putCaveDecorator(BiomeKeys.SWAMP, CaveDecorators.SWAMP);
        putCaveDecorator(BiomeKeys.SWAMP_HILLS, CaveDecorators.SWAMP);

        //jungle biomes
        putCaveDecorator(BiomeKeys.JUNGLE, CaveDecorators.JUNGLE);
        putCaveDecorator(BiomeKeys.JUNGLE_EDGE, CaveDecorators.JUNGLE);
        putCaveDecorator(BiomeKeys.JUNGLE_HILLS, CaveDecorators.JUNGLE);
        putCaveDecorator(BiomeKeys.BAMBOO_JUNGLE, CaveDecorators.JUNGLE);
        putCaveDecorator(BiomeKeys.MODIFIED_JUNGLE, CaveDecorators.JUNGLE);
        putCaveDecorator(BiomeKeys.BAMBOO_JUNGLE_HILLS, CaveDecorators.JUNGLE);
        putCaveDecorator(BiomeKeys.MODIFIED_JUNGLE_EDGE, CaveDecorators.JUNGLE);

        //sand biomes
        putCaveDecorator(BiomeKeys.DESERT, CaveDecorators.SAND);
        putCaveDecorator(BiomeKeys.DESERT_HILLS, CaveDecorators.SAND);
        putCaveDecorator(BiomeKeys.DESERT_LAKES, CaveDecorators.SAND);
        putCaveDecorator(BiomeKeys.BEACH, CaveDecorators.SAND);

        //red sand biomes
        putCaveDecorator(BiomeKeys.BADLANDS, CaveDecorators.RED_SAND);
        putCaveDecorator(BiomeKeys.BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
        putCaveDecorator(BiomeKeys.ERODED_BADLANDS, CaveDecorators.RED_SAND);
        putCaveDecorator(BiomeKeys.MODIFIED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
        putCaveDecorator(BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
        putCaveDecorator(BiomeKeys.WOODED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);

        //mushroom biomes
        putCaveDecorator(BiomeKeys.MUSHROOM_FIELDS, CaveDecorators.MUSHROOM);
        putCaveDecorator(BiomeKeys.MUSHROOM_FIELD_SHORE, CaveDecorators.MUSHROOM);
    }
}
