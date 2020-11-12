package supercoder79.cavebiomes.impl;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.compat.VanillaCompat;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;

import java.util.*;

public final class CaveBiomesImpl {
    private static final Map<RegistryKey<Biome>, CaveDecorator> BIOME_DECORATORS = new HashMap<>();
    private static final List<CaveDecorator> DECORATORS = new ArrayList<>();
    private static final List<CaveDecorator> BASE_DECORATORS = new ArrayList<>();

    private CaveBiomesImpl() {

    }

    public static void addBiomeCaveDecorator(RegistryKey<Biome> biome, CaveDecorator decorator) {
        BIOME_DECORATORS.put(biome, decorator);
    }

    public static void registerCaveDecorator(CaveDecorator decorator) {
        if (!DECORATORS.contains(decorator)) {
            DECORATORS.add(decorator);
        }
    }

    public static void registerBaseCaveDecorator(CaveDecorator decorator) {
        if (!BASE_DECORATORS.contains(decorator)) {
            BASE_DECORATORS.add(decorator);
        }
    }

    public static List<CaveDecorator> getCaveDecorators() {
        return ImmutableList.copyOf(DECORATORS);
    }

    public static List<CaveDecorator> getBaseCaveDecorators() {
        return ImmutableList.copyOf(BASE_DECORATORS);
    }

    public static int indexOf(CaveDecorator decorator) {
        return DECORATORS.indexOf(decorator);
    }

    public static CaveDecorator getCaveDecoratorForBiome(Registry<Biome> registry, Biome biome) {
        Optional<RegistryKey<Biome>> key = registry.getKey(biome);

        // Return the decorator if it exists, guess if it doesn't
        if (key.isPresent()) {
            return BIOME_DECORATORS.computeIfAbsent(key.get(), registryKey -> VanillaCompat.guessCaveDecorator(biome));
        }

        // Default to no-op
        return CaveDecorators.NONE;
    }
}
