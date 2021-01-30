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
import net.minecraft.world.gen.carver.ConfiguredCarver;
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
import supercoder79.cavebiomes.world.carver.ConfiguredCarvers;
import supercoder79.cavebiomes.world.feature.CaveBiomesFeatures;
import supercoder79.cavebiomes.world.feature.ConfiguredFeatures;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void handleServerStart(Thread thread, DynamicRegistryManager.Impl manager, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        Registry<Biome> biomes = manager.get(Registry.BIOME_KEY);
        Registry<ConfiguredCarver<?>> carvers = manager.get(Registry.CONFIGURED_CARVER_WORLDGEN);
        Registry<ConfiguredFeature<?, ?>> features = manager.get(Registry.CONFIGURED_FEATURE_WORLDGEN);
        ConfigData config = CaveBiomes.CONFIG;

        //TODO: write an api for this
        for (Biome biome : biomes) {

            if (CaveBiomesFeatures.OVERWORLD.test(biome)) {
                if (config.generateOreNodules) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.LAKES,
                            features.get(ConfiguredFeatures.ORE_NODULE));
                }

                if (config.generateLocalWaterLevels) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.LAKES,
                            features.get(ConfiguredFeatures.LOCAL_WATER_LEVELS));
                }

                // Use step 2- better caves uses step 1
                CaveBiomesFeatures.addFeature(biome,
                        GenerationStep.Feature.LAKES,
                        features.get(ConfiguredFeatures.CAVE_BIOMES));

                if (config.generateNewCaves && CarverHelper.shouldAdd(biome)) {
                    CarverHelper.addTo(biome, carvers.get(ConfiguredCarvers.ROOM));
                    CarverHelper.addTo(biome, carvers.get(ConfiguredCarvers.VERTICAL));
                    CarverHelper.addTo(biome, carvers.get(ConfiguredCarvers.HORIZONTAL));
                    CarverHelper.addTo(biome, carvers.get(ConfiguredCarvers.LAVA_ROOM));
                }
            }

            if (config.generateUndergroundLootChests) {
                if (CaveBiomesFeatures.OVERWORLD.test(biome)) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            features.get(ConfiguredFeatures.CAVERN_CHEST));

                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            features.get(ConfiguredFeatures.SPELUNKERS_CHEST));
                }
            }

            if (config.generateUndergroundSpawners) {
                if (CaveBiomesFeatures.OVERWORLD.test(biome)) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            features.get(ConfiguredFeatures.CAVE_SPAWNER));

                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.VEGETAL_DECORATION,
                            features.get(ConfiguredFeatures.RARE_CAVE_SPAWNER));
                }
            }

            if (config.generateNetherLootChests) {
                if (CaveBiomesFeatures.NETHER.test(biome)) {
                    CaveBiomesFeatures.addFeature(biome,
                            GenerationStep.Feature.UNDERGROUND_DECORATION,
                            features.get(ConfiguredFeatures.NETHER_CHEST));
                }
            }
        }
    }
}
