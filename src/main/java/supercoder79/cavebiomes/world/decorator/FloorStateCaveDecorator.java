package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;

import java.util.Random;

public class FloorStateCaveDecorator extends CaveDecorator {
    private final BlockState state;
    private final int chance;

    public FloorStateCaveDecorator(BlockState state, int chance) {
        this.state = state;
        this.chance = chance;
    }

    @Override
    public void decorate(ChunkRegion world, Random random, BlockPos pos) {
        if (world.getBlockState(pos.down()).isOpaque()) {
            if (random.nextInt(this.chance) == 0) {
                world.setBlockState(pos.down(), this.state, 3);
            }
        }
    }
}
