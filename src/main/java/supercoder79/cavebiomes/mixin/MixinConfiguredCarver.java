package supercoder79.cavebiomes.mixin;

import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import supercoder79.cavebiomes.magic.PublicCarverAccess;

@Mixin(ConfiguredCarver.class)
public class MixinConfiguredCarver<WC extends CarverConfig> implements PublicCarverAccess {

    @Shadow @Final private Carver<WC> carver;

    @Override
    public Carver<?> getCarver() {
        return this.carver;
    }
}
