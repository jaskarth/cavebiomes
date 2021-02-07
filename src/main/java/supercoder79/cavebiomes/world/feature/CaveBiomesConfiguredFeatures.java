package supercoder79.cavebiomes.world.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import supercoder79.cavebiomes.config.ConfigData;

public class CaveBiomesConfiguredFeatures {
    public static RegistryKey<ConfiguredFeature<?, ?>> CAVE_BIOMES;
    public static RegistryKey<ConfiguredFeature<?, ?>> LOCAL_WATER_LEVELS;
    public static RegistryKey<ConfiguredFeature<?, ?>> ORE_NODULE;
    public static RegistryKey<ConfiguredFeature<?, ?>> CAVERN_CHEST;
    public static RegistryKey<ConfiguredFeature<?, ?>> SPELUNKERS_CHEST;
    public static RegistryKey<ConfiguredFeature<?, ?>> NETHER_CHEST;
    public static RegistryKey<ConfiguredFeature<?, ?>> CAVE_SPAWNER;
    public static RegistryKey<ConfiguredFeature<?, ?>> RARE_CAVE_SPAWNER;
    public static RegistryKey<ConfiguredFeature<?, ?>> EMERALD_GEODE;

    public static void init(ConfigData config) {
        CAVE_BIOMES = register(new Identifier("cavebiomes", "cave_biomes"), CaveBiomesFeatures.CAVE_BIOMES.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE)));
        LOCAL_WATER_LEVELS = register(new Identifier("cavebiomes", "local_water_levels"), CaveBiomesFeatures.LOCAL_WATER_LEVELS.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE)));
        ORE_NODULE = register(new Identifier("cavebiomes", "ore_nodule"), CaveBiomesFeatures.ORE_NODULE.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE)));
        CAVERN_CHEST = register(new Identifier("cavebiomes", "cavern_chest"), CaveBiomesFeatures.CAVERN_CHEST.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.cavernChestRarity));
        SPELUNKERS_CHEST = register(new Identifier("cavebiomes", "spelunkers_chest"), CaveBiomesFeatures.SPELUNKERS_CHEST.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.spelunkersChestRarity));
        NETHER_CHEST = register(new Identifier("cavebiomes", "nether_chest"), CaveBiomesFeatures.NETHER_CHEST.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.netherChestRarity));
        CAVE_SPAWNER = register(new Identifier("cavebiomes", "cave_spawner"), CaveBiomesFeatures.CAVE_SPAWNER.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.normalSpawnerRarity));
        RARE_CAVE_SPAWNER = register(new Identifier("cavebiomes", "rare_cave_spawner"), CaveBiomesFeatures.RARE_CAVE_SPAWNER.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.rareSpawnerRarity));
        EMERALD_GEODE = register(new Identifier("cavebiomes", "emerald_geode"), EMERALD_GEODE_FEATURE);

    }

    private static RegistryKey<ConfiguredFeature<?, ?>> register(Identifier name, ConfiguredFeature<?, ?> value) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, value);
        return BuiltinRegistries.CONFIGURED_FEATURE.getKey(value).orElseThrow(() -> new IllegalStateException("How did we get here?"));
    }

    private static final ConfiguredFeature<?, ?> EMERALD_GEODE_FEATURE = Feature.GEODE.configure(
            new GeodeFeatureConfig(
                    new GeodeLayerConfig(
                            new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
                            new SimpleBlockStateProvider(Blocks.CALCITE.getDefaultState()),
                            new SimpleBlockStateProvider(Blocks.EMERALD_ORE.getDefaultState()),
                            new SimpleBlockStateProvider(Blocks.CALCITE.getDefaultState()),
                            new SimpleBlockStateProvider(Blocks.CALCITE.getDefaultState()),
                            ImmutableList.of(Blocks.EMERALD_ORE.getDefaultState())),
                    new GeodeLayerThicknessConfig(1.7D, 2.2D, 3.2D, 4.2D),
                    new GeodeCrackConfig(0.8D, 2.75D, 2),
                    0.125D, 0.1D, true,
                    6, 8, 4, 6,
                    2, 5, -24, 24, 0.045D))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(6, 0, 31)))
            .spreadHorizontally()
            .applyChance(144);
}