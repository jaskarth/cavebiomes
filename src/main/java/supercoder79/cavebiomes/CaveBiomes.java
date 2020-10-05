package supercoder79.cavebiomes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import supercoder79.cavebiomes.api.CaveBiomesAPI;
import supercoder79.cavebiomes.carver.CaveBiomeCarvers;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.compat.VanillaCompat;
import supercoder79.cavebiomes.config.ConfigData;
import supercoder79.cavebiomes.config.ConfigIO;
import supercoder79.cavebiomes.feature.CaveBiomesFeatures;
import supercoder79.cavebiomes.layer.CaveInitLayer;
import supercoder79.cavebiomes.layer.OreCaveLayer;
import supercoder79.cavebiomes.layer.RandomStoneLayer;
import supercoder79.cavebiomes.layer.RareCaveLayer;

public class CaveBiomes implements ModInitializer {
	public static final String VERSION = "0.4.0";

	public static ConfigData CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = ConfigIO.init();

		VanillaCompat.addVanillaBiomes();

		// Base cave decorators
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.NONE);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.WATER);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.LAVA);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.VINES);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.OBSIDIAN);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.MAGMA);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.COBBLESTONE);
		CaveBiomesAPI.registerBaseCaveDecorator(CaveDecorators.GRAVEL);

		// Stone cave decorators
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.ANDESITE);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.DIORITE);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.GRANITE);

		// Rare cave decorators
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.COBWEB);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.FULL_OBSIDIAN);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.MUSHROOM);

		// Ore cave decorators
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.COAL);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.IRON);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.GOLD);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.REDSTONE);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.LAPIS);
		CaveBiomesAPI.registerCaveDecorator(CaveDecorators.DIAMOND);

		// Register cave layers
		CaveBiomesAPI.registerCaveLayer(new CaveInitLayer());
		CaveBiomesAPI.registerCaveLayer(new RandomStoneLayer());
		CaveBiomesAPI.registerCaveLayer(new RareCaveLayer());
		CaveBiomesAPI.registerCaveLayer(new OreCaveLayer());

		// Carver stuff
		Registry.register(Registry.CARVER, new Identifier("cave_biomes", "room_carver"), CaveBiomeCarvers.ROOM);
		Registry.register(Registry.CARVER, new Identifier("cave_biomes", "vertical_carver"), CaveBiomeCarvers.VERTICAL);
		Registry.register(Registry.CARVER, new Identifier("cave_biomes", "horizontal_carver"), CaveBiomeCarvers.HORIZONTAL);
		Registry.register(Registry.CARVER, new Identifier("cave_biomes", "lava_room_carver"), CaveBiomeCarvers.LAVA_ROOM);

		// Add enabled chests and spawners
		CaveBiomesFeatures.addEnabledFeatures();
	}
}
