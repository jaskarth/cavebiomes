package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;

import java.util.Random;

public class FluidCaveDecorator extends CaveDecorator {
    private final BlockState state;
    private final int chance;

    public FluidCaveDecorator(BlockState state, int chance) {
        this.state = state;
        this.chance = chance;
    }

    @Override
    public void decorate(ChunkRegion world, Random random, BlockPos pos) {
        if (random.nextInt(chance) == 0) {
            if (world.getBlockState(pos.down()).isOpaque() && world.getBlockState(pos).isAir()) {
                if (shouldSpawn(world, pos)) {
                    world.setBlockState(pos.down(), this.state, 3);
                }
            }
        }
    }

    private boolean shouldSpawn(ChunkRegion world, BlockPos pos) {
        // Check 4 sides and bottom
        return  isValid(world, pos.down().north()) &&
                isValid(world, pos.down().south()) &&
                isValid(world, pos.down().west()) &&
                isValid(world, pos.down().east()) &&
                isValid(world, pos.down(2));
    }

    private boolean isValid(ChunkRegion world, BlockPos pos) {
        return world.getBlockState(pos).isOpaque() || world.getBlockState(pos).isOf(this.state.getBlock());
    }
}
