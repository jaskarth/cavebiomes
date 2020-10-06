package supercoder79.cavebiomes.config;

import java.util.Arrays;
import java.util.List;

import supercoder79.cavebiomes.CaveBiomes;

public class ConfigData {
	public String configVersion = CaveBiomes.VERSION;
	public boolean guessCaveBiomeIfAbsent = true;
	public boolean generateOreCaves = true;
	public boolean generateFullObsidianCaves = true;
	public List<String> whitelistedDimensions = Arrays.asList("minecraft:overworld");
	public boolean generateUndergroundLootChests = true;
	public boolean generateNetherLootChests = true;
	public boolean generateUndergroundSpawners = true;
	public UndergroundFeatureData undergroundFeatures = new UndergroundFeatureData();
}
