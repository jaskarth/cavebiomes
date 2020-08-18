package supercoder79.cavebiomes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import supercoder79.cavebiomes.cave.CaveDecorator;
import supercoder79.cavebiomes.cave.CaveDecorators;
import supercoder79.cavebiomes.config.ConfigData;
import supercoder79.cavebiomes.config.ConfigIO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CaveBiomes implements ModInitializer {
	public static final String VERSION = "0.2.1";

	public static ConfigData CONFIG;

	private static final Map<RegistryKey<Biome>, CaveDecorator> BIOME2CD = new HashMap<>();
	
	public static void putCaveDecorator(RegistryKey<Biome> key, CaveDecorator decorator) {
	    BIOME2CD.put(key, decorator);
    }
    
    public static CaveDecorator getDecorator(ChunkRegion world, Biome biome) {
        Optional<RegistryKey<Biome>> id = world.toServerWorld().getServer().getRegistryManager().get(Registry.BIOME_KEY).getKey(biome);
        return id.map(biomeRegistryKey -> BIOME2CD.computeIfAbsent(biomeRegistryKey, key -> {
            if (!CaveBiomes.CONFIG.guessCaveBiomeIfAbsent) {
                return CaveDecorators.NONE;
            }
            //attempt to add biome heuristically
        
            //if it's icy, or has snow, register snowy cave
            if (biome.getCategory() == Biome.Category.ICY || biome.getPrecipitation() == Biome.Precipitation.SNOW) {
                return CaveDecorators.ICE;
            }
        
            //if it's a jungle, register jungle cave
            if (biome.getCategory() == Biome.Category.JUNGLE) {
                return CaveDecorators.JUNGLE;
            }
        
            //if it's a swamp, register swamp cave
            if (biome.getCategory() == Biome.Category.SWAMP) {
                return CaveDecorators.SWAMP;
            }
        
            //if it's a taiga, register taiga cave
            if (biome.getCategory() == Biome.Category.TAIGA) {
                return CaveDecorators.TAIGA;
            }
        
            //if it's a desert or beach, register sand cave
            if (biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.BEACH) {
                return CaveDecorators.SAND;
            }
        
            //if it's a mesa, register red sand cave
            if (biome.getCategory() == Biome.Category.MESA) {
                return CaveDecorators.RED_SAND;
            }
        
            //So at this point we've gone through every catagory and tried our best to guess which cave would fit it.
            //Now if we still don't know at this point, we look at the surface config and try to guess that way.
        
            SurfaceConfig sc = biome.getGenerationSettings().getSurfaceConfig();
        
            //grassy surface - grassy cave
            if (sc.getTopMaterial() == Blocks.GRASS_BLOCK.getDefaultState()) {
                return CaveDecorators.DIRT_GRASS;
            }
        
            //sandy surface - sandy cave
            if (sc.getTopMaterial() == Blocks.SAND.getDefaultState()) {
                return CaveDecorators.SAND;
            }
        
            //sandy surface - sandy cave
            if (sc.getTopMaterial() == Blocks.RED_SAND.getDefaultState()) {
                return CaveDecorators.RED_SAND;
            }
        
            return CaveDecorators.NONE;
        })).orElse(CaveDecorators.NONE);
    }

	@Override
	public void onInitialize() {
		CONFIG = ConfigIO.init();

		BiomeHandler.addVanillaBiomes();


	}
}
