package supercoder79.cavebiomes;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.cave.CaveDecorators;

import static supercoder79.cavebiomes.CaveBiomes.BIOME_CATEGORY_2CD;

public class BiomeHandler {

    public static void addVanillaBiomes() {
        BIOME_CATEGORY_2CD.put(Biome.Category.ICY, CaveDecorators.ICE);
        BIOME_CATEGORY_2CD.put(Biome.Category.JUNGLE, CaveDecorators.JUNGLE);
        BIOME_CATEGORY_2CD.put(Biome.Category.SWAMP, CaveDecorators.SWAMP);
        BIOME_CATEGORY_2CD.put(Biome.Category.TAIGA, CaveDecorators.TAIGA);
        BIOME_CATEGORY_2CD.put(Biome.Category.DESERT, CaveDecorators.SAND);
        BIOME_CATEGORY_2CD.put(Biome.Category.MESA, CaveDecorators.RED_SAND);
        BIOME_CATEGORY_2CD.put(Biome.Category.BEACH, CaveDecorators.SAND);
        BIOME_CATEGORY_2CD.put(Biome.Category.EXTREME_HILLS, CaveDecorators.DIRT_GRASS);
        BIOME_CATEGORY_2CD.put(Biome.Category.FOREST, CaveDecorators.JUNGLE);
        BIOME_CATEGORY_2CD.put(Biome.Category.MUSHROOM, CaveDecorators.MUSHROOM);
        BIOME_CATEGORY_2CD.put(Biome.Category.PLAINS, CaveDecorators.DIRT_GRASS);
        BIOME_CATEGORY_2CD.put(Biome.Category.SAVANNA, CaveDecorators.RED_SAND);
        BIOME_CATEGORY_2CD.put(Biome.Category.RIVER, CaveDecorators.DIRT_GRASS);
        BIOME_CATEGORY_2CD.put(Biome.Category.OCEAN, CaveDecorators.NONE);
    }
}
