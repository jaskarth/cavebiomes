package supercoder79.cavebiomes.mixin;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(DimensionType.class)
public interface DimensionTypeAccessor {
    @Accessor
    Optional<RegistryKey<DimensionType>> getField_24765();
}
