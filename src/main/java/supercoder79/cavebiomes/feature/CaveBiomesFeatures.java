package supercoder79.cavebiomes.feature;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public final class CaveBiomesFeatures {
	private CaveBiomesFeatures() {
	}

	public static void addEnabledFeatures() {
	}

	private static <C extends FeatureConfig, F extends Feature<C>, T extends ConfiguredFeature<C, F>> void addFeatureTo(final GenerationStep.Feature step, final T feature, final Predicate<Biome> predicate) {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			server.getRegistryManager().get(Registry.BIOME_KEY).forEach(biome -> {
				if (predicate.test(biome)) {
					addFeature(biome, step, feature);
				}
			});
		});
		/*RegistryEntryAddedCallback.event(Registry.BIOME).register((rawId, id, biome) -> {
			if (predicate.test(biome)) {
				biome.addFeature(step, feature);
			}
		});*/
	}

	private static void registerFeature(String id, Feature<?> feature) {
		Registry.register(Registry.FEATURE, new Identifier("cavebiomes", id), feature);
	}

	private static void addFeature(Biome biome, GenerationStep.Feature step, ConfiguredFeature<?, ?> feature) {
		
	}

	public static final TreasureChestFeature CAVERN_CHEST = new TreasureChestFeature(TreasureChestFeature.Type.CAVERN);
	public static final TreasureChestFeature SPELUNKERS_CHEST = new TreasureChestFeature(TreasureChestFeature.Type.SPELUNKERS);

	public static final TreasureChestFeature NETHER_CHEST = new TreasureChestFeature(TreasureChestFeature.Type.NETHER);

	public static final MobSpawnerFeature CAVE_SPAWNER = new MobSpawnerFeature(50, EntityType.ZOMBIE, EntityType.SPIDER);
	public static final MobSpawnerFeature RARE_CAVE_SPAWNER = new MobSpawnerFeature(35, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.CAVE_SPIDER, EntityType.CREEPER);
}
