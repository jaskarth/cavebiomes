package supercoder79.cavebiomes.api;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import supercoder79.cavebiomes.impl.CaveBiomesImpl;

import java.util.List;

public final class CaveBiomesAPI {
    private CaveBiomesAPI() {

    }

    public static void addBiomeCaveDecorator(RegistryKey<Biome> biome, CaveDecorator decorator) {
        CaveBiomesImpl.addBiomeCaveDecorator(biome, decorator);
    }

    public static void registerCaveDecorator(CaveDecorator decorator) {
        CaveBiomesImpl.registerCaveDecorator(decorator);
    }

    public static void registerBaseCaveDecorator(CaveDecorator decorator) {
        // Register the decorator as normal first
        registerCaveDecorator(decorator);

        // Register it as a base decorator next
        CaveBiomesImpl.registerBaseCaveDecorator(decorator);
    }

    public static List<CaveDecorator> getCaveDecorators() {
        return CaveBiomesImpl.getCaveDecorators();
    }

    public static List<CaveDecorator> getBaseCaveDecorators() {
        return CaveBiomesImpl.getBaseCaveDecorators();
    }

    public static int indexOf(CaveDecorator decorator) {
        return CaveBiomesImpl.indexOf(decorator);
    }

    public static CaveDecorator getCaveDecoratorForBiome(Registry<Biome> registry, Biome biome) {
        return CaveBiomesImpl.getCaveDecoratorForBiome(registry, biome);
    }
}
