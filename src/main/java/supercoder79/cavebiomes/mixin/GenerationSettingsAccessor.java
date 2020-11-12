package supercoder79.cavebiomes.mixin;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(GenerationSettings.class)
public interface GenerationSettingsAccessor {
	@Accessor
	Map<GenerationStep.Carver, List<Supplier<ConfiguredCarver<?>>>> getCarvers();

	@Mutable
	@Accessor
	void setCarvers(Map<GenerationStep.Carver, List<Supplier<ConfiguredCarver<?>>>> carvers);

	@Mutable
	@Accessor
	void setFeatures(List<List<Supplier<ConfiguredFeature<?, ?>>>> features);
}
