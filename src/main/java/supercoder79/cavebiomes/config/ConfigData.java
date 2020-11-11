package supercoder79.cavebiomes.config;

import java.util.Arrays;
import java.util.List;

import supercoder79.cavebiomes.CaveBiomes;

public class ConfigData {
	public String configVersion = CaveBiomes.VERSION;
	public boolean generateOreCaves = true;
	public boolean generateFullObsidianCaves = true;
	public boolean generateWebCaves = true;
	public List<String> whitelistedDimensions = Arrays.asList("minecraft:overworld");
	public int caveLayerThreshold = 28;
	public int caveBiomeSize = 7;
	public boolean generateNewCaves = true;
	public boolean generateCaverns = true;
	public boolean generateLocalWaterLevels = true;
	public boolean generateOreNodules = true;
	public boolean generateUndergroundLootChests = true;
	public boolean generateNetherLootChests = true;
	public boolean generateUndergroundSpawners = true;

	public int cavernChestRarity = 12;
	public int spelunkersChestRarity = 24;
	public int netherChestRarity = 16;
	public int normalSpawnerRarity = 28;
	public int rareSpawnerRarity = 42;
}
