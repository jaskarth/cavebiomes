package supercoder79.cavebiomes.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.block.Blocks;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.config.ConfigData;
import supercoder79.cavebiomes.world.carver.CarverHelper;
import supercoder79.cavebiomes.world.carver.CaveBiomeCarvers;
import supercoder79.cavebiomes.world.feature.CaveBiomesFeatures;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void handleServerStart(Thread thread, DynamicRegistryManager.Impl manager, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        Registry<Biome> biomes = manager.get(Registry.BIOME_KEY);
        ConfigData config = CaveBiomes.CONFIG;

        //TODO: write an api for this
        for (Biome biome : biomes) {

            if (CaveBiomesFeatures.OVERWORLD.test(biome)) {
                if (config.generateOreNodules) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.LAKES,
                            CaveBiomesFeatures.ORE_NODULE.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE)));
                }

                if (config.generateEmeraldGeodes) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.LAKES,
                            Feature.GEODE.configure(
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
                                    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(6, 0, 31))).spreadHorizontally().applyChance(96));
                }

                if (config.generateLocalWaterLevels) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.LAKES,
                            CaveBiomesFeatures.LOCAL_WATER_LEVELS.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE)));
                }

                // Use step 2- better caves uses step 1
                CaveBiomesFeatures.addFeature(biome,
                        GenerationStep.Feature.LAKES,
                        CaveBiomesFeatures.CAVE_BIOMES.configure(FeatureConfig.DEFAULT).decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE)));

                if (config.generateCaverns) {
                    CarverHelper.addTo(biome, CaveBiomeCarvers.PERLERP.configure(new ProbabilityConfig(1)));
                }

                if (config.generateNewCaves && CarverHelper.shouldAdd(biome)) {
                    CarverHelper.addTo(biome, CaveBiomeCarvers.ROOM.configure(new ProbabilityConfig(1 / 6.0f)));
                    CarverHelper.addTo(biome, CaveBiomeCarvers.VERTICAL.configure(new ProbabilityConfig(1 / 6.0f)));
                    CarverHelper.addTo(biome, CaveBiomeCarvers.HORIZONTAL.configure(new ProbabilityConfig(1 / 8.0f)));
                    CarverHelper.addTo(biome, CaveBiomeCarvers.LAVA_ROOM.configure(new ProbabilityConfig(1 / 32.0f)));
                }
            }

            if (config.generateUndergroundLootChests) {
                if (CaveBiomesFeatures.OVERWORLD.test(biome)) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            CaveBiomesFeatures.CAVERN_CHEST.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.cavernChestRarity));

                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            CaveBiomesFeatures.SPELUNKERS_CHEST.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.spelunkersChestRarity));
                }
            }

            if (config.generateUndergroundSpawners) {
                if (CaveBiomesFeatures.OVERWORLD.test(biome)) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            CaveBiomesFeatures.CAVE_SPAWNER.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.normalSpawnerRarity));

                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            CaveBiomesFeatures.RARE_CAVE_SPAWNER.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.rareSpawnerRarity));
                }
            }

            if (config.generateNetherLootChests) {
                if (CaveBiomesFeatures.NETHER.test(biome)) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.UNDERGROUND_DECORATION,
                            CaveBiomesFeatures.NETHER_CHEST.configure(FeatureConfig.DEFAULT).spreadHorizontally().applyChance(config.netherChestRarity));
                }
            }
        }
    }
}
