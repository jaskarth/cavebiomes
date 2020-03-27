package supercoder79.cavebiomes.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import supercoder79.cavebiomes.magic.CaveAirAccess;

import java.util.HashSet;
import java.util.Set;

@Mixin(Carver.class)
public class MixinCarver implements CaveAirAccess {
    @Unique
    private Set<BlockPos> positions = new HashSet<>();

    @Redirect(method = "carveAtPoint",
            at = @At(
                    target = "Lnet/minecraft/world/chunk/Chunk;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;",
            value = "INVOKE", ordinal = 1))
    public BlockState hook(Chunk chunk, BlockPos pos, BlockState state, boolean moved) {
        positions.add(pos.toImmutable()); //this is a mutable so we need toImmutable
        return chunk.setBlockState(pos, Blocks.CAVE_AIR.getDefaultState(), false);
    }

    @Override
    public void reset() {
        positions = new HashSet<>();
    }

    @Override
    public Set<BlockPos> retrieve() {
        return positions;
    }
}
