package supercoder79.cavebiomes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.config.ConfigData;
import supercoder79.cavebiomes.config.ConfigIO;

import java.util.HashMap;
import java.util.Map;

public class CaveBiomes implements ModInitializer {
	public static final String VERSION = "0.2.1";

	public static ConfigData CONFIG;

	public static final Map<Biome.Category, CaveDecorator> BIOME_CATEGORY_2CD = new HashMap<>();

	@Override
	public void onInitialize() {
		CONFIG = ConfigIO.init();

		BiomeHandler.addVanillaBiomes();


	}
}
