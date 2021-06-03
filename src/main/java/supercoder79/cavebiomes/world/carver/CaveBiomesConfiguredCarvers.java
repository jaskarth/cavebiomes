package supercoder79.cavebiomes.world.carver;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.carver.ConfiguredCarver;

public class CaveBiomesConfiguredCarvers {
    public static RegistryKey<ConfiguredCarver<?>> ROOM;
    public static RegistryKey<ConfiguredCarver<?>> VERTICAL;
    public static RegistryKey<ConfiguredCarver<?>> HORIZONTAL;
    public static RegistryKey<ConfiguredCarver<?>> LAVA_ROOM;
    public static RegistryKey<ConfiguredCarver<?>> PERLERP;

    public static void init() {
        ROOM = register(new Identifier("cavebiomes", "room"), CaveBiomeCarvers.ROOM.configure(new SimpleCarverConfig(1 / 6.0f)));
        VERTICAL = register(new Identifier("cavebiomes", "vertical"), CaveBiomeCarvers.VERTICAL.configure(new SimpleCarverConfig(1 / 6.0f)));
        HORIZONTAL = register(new Identifier("cavebiomes", "horizontal"), CaveBiomeCarvers.HORIZONTAL.configure(new SimpleCarverConfig(1 / 8.0f)));
        LAVA_ROOM = register(new Identifier("cavebiomes", "lava_room"), CaveBiomeCarvers.LAVA_ROOM.configure(new SimpleCarverConfig(1 / 32.0f)));
        PERLERP = register(new Identifier("cavebiomes", "perlerp"), CaveBiomeCarvers.PERLERP.configure(new SimpleCarverConfig(1)));
    }

    private static RegistryKey<ConfiguredCarver<?>> register(Identifier name, ConfiguredCarver<?> value) {
        Registry.register(BuiltinRegistries.CONFIGURED_CARVER, name, value);
        return BuiltinRegistries.CONFIGURED_CARVER.getKey(value).orElseThrow(() -> new IllegalStateException("How did we get here?"));
    }
}