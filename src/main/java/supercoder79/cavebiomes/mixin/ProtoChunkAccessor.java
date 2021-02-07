package supercoder79.cavebiomes.mixin;

import net.minecraft.world.HeightLimitView;
import net.minecraft.world.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ProtoChunk.class)
public interface ProtoChunkAccessor {
    @Accessor(value = "world")
    HeightLimitView getWorld();
}
