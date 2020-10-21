package supercoder79.cavebiomes.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import supercoder79.cavebiomes.config.ConfigData;
import supercoder79.cavebiomes.mixin.GenerationSettingsAccessor;

public final class CaveBiomesFeatures {
	private CaveBiomesFeatures() {
	}

	public static void addEnabledFeatures(ConfigData config) {
		// register features
		registerFeature("cavern_chest", CAVERN_CHEST);
		registerFeature("spelunkers_chest", SPELUNKERS_CHEST);

		registerFeature("nether_chest", NETHER_CHEST);

		registerFeature("cave_spawner", CAVE_SPAWNER);
		registerFeature("rare_cave_spawner", RARE_CAVE_SPAWNER);

		registerFeature("cave_biomes", CAVE_BIOMES);
		registerFeature("local_water_levels", LOCAL_WATER_LEVELS);

		// add generation
	}

	private static <C extends FeatureConfig, F extends Feature<C>, T extends ConfiguredFeature<C, F>> void addFeatureTo(final GenerationStep.Feature step, final T feature, final Predicate<Biome> predicate) {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			server.getRegistryManager().get(Registry.BIOME_KEY).forEach(biome -> {
				if (predicate.test(biome)) {
					addFeature(biome, step, feature);
				}
			});
		});
	}

	private static void registerFeature(String id, Feature<?> feature) {
		Registry.register(Registry.FEATURE, new Identifier("cavebiomes", id), feature);
	}

	public static void addFeature(Biome biome, GenerationStep.Feature step, ConfiguredFeature<?, ?> feature) {
		List<List<Supplier<ConfiguredFeature<?, ?>>>> featureSteps = biome.getGenerationSettings().getFeatures();

		// Mutable List
		List<List<Supplier<ConfiguredFeature<?, ?>>>> newFeatures = new ArrayList<>();

		for (GenerationStep.Feature featureStep : GenerationStep.Feature.values()) {
			int index = featureStep.ordinal();

			// create a mutable list
			List<Supplier<ConfiguredFeature<?, ?>>> features = index < featureSteps.size() ? new ArrayList<>(featureSteps.get(index)) : new ArrayList<>();

			if (step == featureStep) {
				// Add our feature if we're in the correct step
				features.add(() -> feature);
			}

			// Add entry
			newFeatures.add(features);
		}

		// Replace list
		((GenerationSettingsAccessor) biome.getGenerationSettings()).setFeatures(newFeatures);
	}

	public static final Predicate<Biome> OVERWORLD = biome -> {
		Biome.Category category = biome.getCategory();
		return !(category == Biome.Category.NETHER || category == Biome.Category.THEEND);
	};

	public static final Predicate<Biome> NETHER = biome -> {
		Biome.Category category = biome.getCategory();
		return category == Biome.Category.NETHER;
	};

	public static final AddCaveBiomesFeature CAVE_BIOMES = new AddCaveBiomesFeature();
	public static final LocalWaterLevelsFeature LOCAL_WATER_LEVELS = new LocalWaterLevelsFeature();

	public static final TreasureChestFeature CAVERN_CHEST = new TreasureChestFeature(0, TreasureChestFeature.Type.CAVERN);
	public static final TreasureChestFeature SPELUNKERS_CHEST = new TreasureChestFeature(1, TreasureChestFeature.Type.SPELUNKERS);

	public static final TreasureChestFeature NETHER_CHEST = new TreasureChestFeature(2, TreasureChestFeature.Type.NETHER);

	public static final MobSpawnerFeature CAVE_SPAWNER = new MobSpawnerFeature(3, 50, EntityType.ZOMBIE, EntityType.SPIDER);
	public static final MobSpawnerFeature RARE_CAVE_SPAWNER = new MobSpawnerFeature(4, 35, EntityType.SKELETON, EntityType.SKELETON, EntityType.CAVE_SPIDER, EntityType.SPIDER, EntityType.SILVERFISH);
}
