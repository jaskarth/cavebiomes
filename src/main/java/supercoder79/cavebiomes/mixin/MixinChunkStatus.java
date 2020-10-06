package supercoder79.cavebiomes.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.cavebiomes.util.WorldCarverAccess;

import java.util.List;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(ChunkStatus.class)
public class MixinChunkStatus {
    @Inject(method = "method_16563", at = @At("HEAD"), remap = false, cancellable = true)
    private static void carveSanelyAir(ServerWorld world, ChunkGenerator generator, List<Chunk> list, Chunk chunk, CallbackInfo info) {
        if (generator instanceof WorldCarverAccess) {
            ((WorldCarverAccess)generator).carve(world.getSeed(), new ChunkRegion(world, list), world.getBiomeAccess().withSource(generator.getBiomeSource()), chunk, GenerationStep.Carver.AIR);
        }
        info.cancel();
    }
}
