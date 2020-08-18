package supercoder79.cavebiomes;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import supercoder79.cavebiomes.cave.CaveDecorators;

import static supercoder79.cavebiomes.CaveBiomes.BIOME2CD;

public class BiomeHandler {
    private static boolean fullyInitialized = false;

    public static void addVanillaBiomes() {
        //ice biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.ICE_SPIKES), CaveDecorators.ICE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_BEACH), CaveDecorators.ICE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_MOUNTAINS), CaveDecorators.ICE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_TAIGA), CaveDecorators.ICE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_TAIGA_HILLS), CaveDecorators.ICE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_TAIGA_MOUNTAINS), CaveDecorators.ICE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_TUNDRA), CaveDecorators.ICE);

        //taiga biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.TAIGA), CaveDecorators.TAIGA);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.TAIGA_HILLS), CaveDecorators.TAIGA);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.TAIGA_MOUNTAINS), CaveDecorators.TAIGA);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.GIANT_SPRUCE_TAIGA), CaveDecorators.TAIGA);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS), CaveDecorators.TAIGA);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.GIANT_TREE_TAIGA), CaveDecorators.TAIGA);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.GIANT_TREE_TAIGA_HILLS), CaveDecorators.TAIGA);

        //grassy biomes (fallback)
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.PLAINS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.FOREST), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.WOODED_HILLS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BIRCH_FOREST), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BIRCH_FOREST_HILLS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.TALL_BIRCH_FOREST), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.TALL_BIRCH_HILLS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.DARK_FOREST), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.DARK_FOREST_HILLS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MOUNTAINS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MOUNTAIN_EDGE), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.GRAVELLY_MOUNTAINS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.WOODED_MOUNTAINS), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SAVANNA), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SAVANNA_PLATEAU), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SHATTERED_SAVANNA), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SHATTERED_SAVANNA_PLATEAU), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.FLOWER_FOREST), CaveDecorators.DIRT_GRASS);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SUNFLOWER_PLAINS), CaveDecorators.DIRT_GRASS);

        //swamp biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SWAMP), CaveDecorators.SWAMP);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.SWAMP_HILLS), CaveDecorators.SWAMP);

        //jungle biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.JUNGLE), CaveDecorators.JUNGLE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.JUNGLE_EDGE), CaveDecorators.JUNGLE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.JUNGLE_HILLS), CaveDecorators.JUNGLE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BAMBOO_JUNGLE), CaveDecorators.JUNGLE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MODIFIED_JUNGLE), CaveDecorators.JUNGLE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BAMBOO_JUNGLE_HILLS), CaveDecorators.JUNGLE);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MODIFIED_JUNGLE_EDGE), CaveDecorators.JUNGLE);

        //sand biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.DESERT), CaveDecorators.SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.DESERT_HILLS), CaveDecorators.SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.DESERT_LAKES), CaveDecorators.SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BEACH), CaveDecorators.SAND);

        //red sand biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BADLANDS), CaveDecorators.RED_SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.BADLANDS_PLATEAU), CaveDecorators.RED_SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.ERODED_BADLANDS), CaveDecorators.RED_SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MODIFIED_BADLANDS_PLATEAU), CaveDecorators.RED_SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU), CaveDecorators.RED_SAND);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.WOODED_BADLANDS_PLATEAU), CaveDecorators.RED_SAND);

        //mushroom biomes
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MUSHROOM_FIELDS), CaveDecorators.MUSHROOM);
        BIOME2CD.put(BuiltinRegistries.BIOME.get(BiomeKeys.MUSHROOM_FIELD_SHORE), CaveDecorators.MUSHROOM);
    }

    public static void attemptAddRemainingBiomes() {
        if (!CaveBiomes.CONFIG.guessCaveBiomeIfAbsent) return;

        if (fullyInitialized) return;

        fullyInitialized = true;

        for (Biome biome : BuiltinRegistries.BIOME) {
            BIOME2CD.computeIfAbsent(biome, b -> {
               //attempt to add biome heuristically

                //if it's icy, or has snow, register snowy cave
                if (b.getCategory() == Biome.Category.ICY || b.getPrecipitation() == Biome.Precipitation.SNOW) {
                    return CaveDecorators.ICE;
                }

                //if it's a jungle, register jungle cave
                if (b.getCategory() == Biome.Category.JUNGLE) {
                    return CaveDecorators.JUNGLE;
                }

                //if it's a swamp, register swamp cave
                if (b.getCategory() == Biome.Category.SWAMP) {
                    return CaveDecorators.SWAMP;
                }

                //if it's a taiga, register taiga cave
                if (b.getCategory() == Biome.Category.TAIGA) {
                    return CaveDecorators.TAIGA;
                }

                //if it's a desert or beach, register sand cave
                if (b.getCategory() == Biome.Category.DESERT || b.getCategory() == Biome.Category.BEACH) {
                    return CaveDecorators.SAND;
                }

                //if it's a mesa, register red sand cave
                if (b.getCategory() == Biome.Category.MESA) {
                    return CaveDecorators.RED_SAND;
                }

                //So at this point we've gone through every catagory and tried our best to guess which cave would fit it.
                //Now if we still don't know at this point, we look at the surface config and try to guess that way.

                SurfaceConfig sc = b.getGenerationSettings().getSurfaceConfig();

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
            });
        }
    }
}
