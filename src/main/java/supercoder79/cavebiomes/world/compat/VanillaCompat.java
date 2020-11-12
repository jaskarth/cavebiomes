package supercoder79.cavebiomes.world.compat;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;

public class VanillaCompat {
    public static void addVanillaBiomes() {
        //ice biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.ICE_SPIKES, CaveDecorators.ICE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SNOWY_BEACH, CaveDecorators.ICE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SNOWY_MOUNTAINS, CaveDecorators.ICE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SNOWY_TAIGA, CaveDecorators.ICE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SNOWY_TAIGA_HILLS, CaveDecorators.ICE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, CaveDecorators.ICE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SNOWY_TUNDRA, CaveDecorators.ICE);

        //taiga biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.TAIGA, CaveDecorators.TAIGA);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.TAIGA_HILLS, CaveDecorators.TAIGA);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.TAIGA_MOUNTAINS, CaveDecorators.TAIGA);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.GIANT_SPRUCE_TAIGA, CaveDecorators.TAIGA);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, CaveDecorators.TAIGA);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.GIANT_TREE_TAIGA, CaveDecorators.TAIGA);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.GIANT_TREE_TAIGA_HILLS, CaveDecorators.TAIGA);

        //grassy biomes (fallback)
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.PLAINS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.FOREST, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.WOODED_HILLS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BIRCH_FOREST, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BIRCH_FOREST_HILLS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.TALL_BIRCH_FOREST, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.TALL_BIRCH_HILLS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DARK_FOREST, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DARK_FOREST_HILLS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MOUNTAINS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MOUNTAIN_EDGE, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.GRAVELLY_MOUNTAINS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.WOODED_MOUNTAINS, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SAVANNA, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SAVANNA_PLATEAU, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SHATTERED_SAVANNA, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.FLOWER_FOREST, CaveDecorators.DIRT_GRASS);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SUNFLOWER_PLAINS, CaveDecorators.DIRT_GRASS);

        //swamp biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SWAMP, CaveDecorators.SWAMP);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.SWAMP_HILLS, CaveDecorators.SWAMP);

        //jungle biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.JUNGLE, CaveDecorators.JUNGLE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.JUNGLE_EDGE, CaveDecorators.JUNGLE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.JUNGLE_HILLS, CaveDecorators.JUNGLE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BAMBOO_JUNGLE, CaveDecorators.JUNGLE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MODIFIED_JUNGLE, CaveDecorators.JUNGLE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BAMBOO_JUNGLE_HILLS, CaveDecorators.JUNGLE);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MODIFIED_JUNGLE_EDGE, CaveDecorators.JUNGLE);

        //sand biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DESERT, CaveDecorators.SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DESERT_HILLS, CaveDecorators.SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DESERT_LAKES, CaveDecorators.SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BEACH, CaveDecorators.SAND);

        //red sand biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BADLANDS, CaveDecorators.RED_SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.ERODED_BADLANDS, CaveDecorators.RED_SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MODIFIED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.WOODED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);

        //mushroom biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MUSHROOM_FIELDS, CaveDecorators.MUSHROOM);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.MUSHROOM_FIELD_SHORE, CaveDecorators.MUSHROOM);

        //ocean biomes
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.WARM_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.LUKEWARM_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.COLD_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.FROZEN_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DEEP_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DEEP_WARM_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DEEP_LUKEWARM_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DEEP_COLD_OCEAN, CaveDecorators.WATER);
        CaveBiomesAPI.addBiomeCaveDecorator(BiomeKeys.DEEP_FROZEN_OCEAN, CaveDecorators.WATER);
    }

    public static CaveDecorator guessCaveDecorator(Biome biome) {
        //attempt to add biome heuristically

        //if it's icy, or has snow, register snowy cave
        if (biome.getCategory() == Biome.Category.ICY || biome.getPrecipitation() == Biome.Precipitation.SNOW) {
            return CaveDecorators.ICE;
        }

        //if it's a jungle, register jungle cave
        if (biome.getCategory() == Biome.Category.JUNGLE) {
            return CaveDecorators.JUNGLE;
        }

        //if it's a swamp, register swamp cave
        if (biome.getCategory() == Biome.Category.SWAMP) {
            return CaveDecorators.SWAMP;
        }

        //if it's an ocean, register water cave
        if (biome.getCategory() == Biome.Category.OCEAN) {
            return CaveDecorators.WATER;
        }

        //if it's a taiga, register taiga cave
        if (biome.getCategory() == Biome.Category.TAIGA) {
            return CaveDecorators.TAIGA;
        }

        //if it's a desert or beach, register sand cave
        if (biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.BEACH) {
            return CaveDecorators.SAND;
        }

        //if it's a mesa, register red sand cave
        if (biome.getCategory() == Biome.Category.MESA) {
            return CaveDecorators.RED_SAND;
        }

        //So at this point we've gone through every category and tried our best to guess which cave would fit it.
        //Now if we still don't know at this point, we look at the surface config and try to guess that way.

        SurfaceConfig sc = biome.getGenerationSettings().getSurfaceConfig();

        //grassy surface - grassy cave
        if (sc.getTopMaterial() == Blocks.GRASS_BLOCK.getDefaultState()) {
            return CaveDecorators.DIRT_GRASS;
        }

        //sandy surface - sandy cave
        if (sc.getTopMaterial() == Blocks.SAND.getDefaultState()) {
            return CaveDecorators.SAND;
        }

        //sandy surface - sandy cave
        if (sc.getTopMaterial() == Blocks.RED_SAND.getDefaultState()) {
            return CaveDecorators.RED_SAND;
        }

        return CaveDecorators.NONE;
    }
}
