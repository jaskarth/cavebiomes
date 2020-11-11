package supercoder79.cavebiomes.world.carver;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import supercoder79.cavebiomes.mixin.GenerationSettingsAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CarverHelper {
    public static boolean shouldAdd(Biome biome) {
        return biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.NONE;
    }

    public static void addTo(Biome biome, ConfiguredCarver<?> carver) {
        GenerationSettingsAccessor accessor = (GenerationSettingsAccessor)biome.getGenerationSettings();
        Map<GenerationStep.Carver, List<Supplier<ConfiguredCarver<?>>>> map = accessor.getCarvers();

        // Mutable map
        map = new HashMap<>(map);

        for (Map.Entry<GenerationStep.Carver, List<Supplier<ConfiguredCarver<?>>>> entry : map.entrySet()) {
            List<Supplier<ConfiguredCarver<?>>> carvers = entry.getValue();

            // Make list mutable
            if (carvers instanceof ImmutableList) {
                carvers = new ArrayList<>(carvers);
            }

            // Add our carver
            carvers.add(() -> carver);

            // Replace entry
            map.put(entry.getKey(), carvers);
        }

        // Replace map
        accessor.setCarvers(map);
    }
}
