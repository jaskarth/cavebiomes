package supercoder79.cavebiomes.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.cavebiomes.carver.CarverHelper;
import supercoder79.cavebiomes.carver.CaveBiomeCarvers;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void handleServerStart(Thread thread, DynamicRegistryManager.Impl manager, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        Registry<Biome> biomes = manager.get(Registry.BIOME_KEY);

        for (Biome biome : biomes) {
            if (CarverHelper.shouldAdd(biome)) {
                CarverHelper.addTo(biome, CaveBiomeCarvers.ROOM.method_28614(new ProbabilityConfig(1 / 6.0f)));
                CarverHelper.addTo(biome, CaveBiomeCarvers.VERTICAL.method_28614(new ProbabilityConfig(1 / 6.0f)));
                CarverHelper.addTo(biome, CaveBiomeCarvers.HORIZONTAL.method_28614(new ProbabilityConfig(1 / 8.0f)));
                CarverHelper.addTo(biome, CaveBiomeCarvers.LAVA_ROOM.method_28614(new ProbabilityConfig(1 / 20.0f)));
            }
        }
    }
}
