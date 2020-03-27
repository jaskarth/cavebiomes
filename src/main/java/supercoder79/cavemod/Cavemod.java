package supercoder79.cavemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import supercoder79.cavemod.cave.CaveDecorator;
import supercoder79.cavemod.cave.CaveDecorators;

import java.util.HashMap;
import java.util.Map;

public class Cavemod implements ModInitializer {
	public static final Map<Biome, CaveDecorator> BIOME2CD = new HashMap<>();

	@Override
	public void onInitialize() {
		//ice biomes
		BIOME2CD.put(Biomes.ICE_SPIKES, CaveDecorators.ICE);
		BIOME2CD.put(Biomes.SNOWY_BEACH, CaveDecorators.ICE);
		BIOME2CD.put(Biomes.SNOWY_MOUNTAINS, CaveDecorators.ICE);
		BIOME2CD.put(Biomes.SNOWY_TAIGA, CaveDecorators.ICE);
		BIOME2CD.put(Biomes.SNOWY_TAIGA_HILLS, CaveDecorators.ICE);
		BIOME2CD.put(Biomes.SNOWY_TAIGA_MOUNTAINS, CaveDecorators.ICE);
		BIOME2CD.put(Biomes.SNOWY_TUNDRA, CaveDecorators.ICE);

		//taiga biomes
		BIOME2CD.put(Biomes.TAIGA, CaveDecorators.TAIGA);
		BIOME2CD.put(Biomes.TAIGA_HILLS, CaveDecorators.TAIGA);
		BIOME2CD.put(Biomes.TAIGA_MOUNTAINS, CaveDecorators.TAIGA);
		BIOME2CD.put(Biomes.GIANT_SPRUCE_TAIGA, CaveDecorators.TAIGA);
		BIOME2CD.put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, CaveDecorators.TAIGA);
		BIOME2CD.put(Biomes.GIANT_TREE_TAIGA, CaveDecorators.TAIGA);
		BIOME2CD.put(Biomes.GIANT_TREE_TAIGA_HILLS, CaveDecorators.TAIGA);

		//grassy biomes (fallback)
		BIOME2CD.put(Biomes.PLAINS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.FOREST, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.WOODED_HILLS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.BIRCH_FOREST, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.BIRCH_FOREST_HILLS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.TALL_BIRCH_FOREST, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.TALL_BIRCH_HILLS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.DARK_FOREST, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.DARK_FOREST_HILLS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.MOUNTAINS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.MOUNTAIN_EDGE, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.MODIFIED_GRAVELLY_MOUNTAINS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.GRAVELLY_MOUNTAINS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.WOODED_MOUNTAINS, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.SAVANNA, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.SAVANNA_PLATEAU, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.SHATTERED_SAVANNA, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.SHATTERED_SAVANNA_PLATEAU, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.FLOWER_FOREST, CaveDecorators.DIRT_GRASS);
		BIOME2CD.put(Biomes.SUNFLOWER_PLAINS, CaveDecorators.DIRT_GRASS);

		//swamp biomes
		BIOME2CD.put(Biomes.SWAMP, CaveDecorators.SWAMP);
		BIOME2CD.put(Biomes.SWAMP_HILLS, CaveDecorators.SWAMP);

		//jungle biomes
		BIOME2CD.put(Biomes.JUNGLE, CaveDecorators.JUNGLE);
		BIOME2CD.put(Biomes.JUNGLE_EDGE, CaveDecorators.JUNGLE);
		BIOME2CD.put(Biomes.JUNGLE_HILLS, CaveDecorators.JUNGLE);
		BIOME2CD.put(Biomes.BAMBOO_JUNGLE, CaveDecorators.JUNGLE);
		BIOME2CD.put(Biomes.MODIFIED_JUNGLE, CaveDecorators.JUNGLE);
		BIOME2CD.put(Biomes.BAMBOO_JUNGLE_HILLS, CaveDecorators.JUNGLE);
		BIOME2CD.put(Biomes.MODIFIED_JUNGLE_EDGE, CaveDecorators.JUNGLE);

		//sand biomes
		BIOME2CD.put(Biomes.DESERT, CaveDecorators.SAND);
		BIOME2CD.put(Biomes.DESERT_HILLS, CaveDecorators.SAND);
		BIOME2CD.put(Biomes.DESERT_LAKES, CaveDecorators.SAND);
		BIOME2CD.put(Biomes.BEACH, CaveDecorators.SAND);

		//red sand biomes
		BIOME2CD.put(Biomes.BADLANDS, CaveDecorators.RED_SAND);
		BIOME2CD.put(Biomes.BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
		BIOME2CD.put(Biomes.ERODED_BADLANDS, CaveDecorators.RED_SAND);
		BIOME2CD.put(Biomes.MODIFIED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
		BIOME2CD.put(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);
		BIOME2CD.put(Biomes.WOODED_BADLANDS_PLATEAU, CaveDecorators.RED_SAND);


	}
}
