package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class FloorStateCaveDecorator extends CaveDecorator {
    private final BlockState state;
    private final int chance;

    public FloorStateCaveDecorator(BlockState state, int chance) {
        this.state = state;
        this.chance = chance;
    }

    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (chunk.getBlockState(pos.down()).isOpaque()) {
                if (world.getRandom().nextInt(this.chance) == 0) {
                    chunk.setBlockState(pos.down(), this.state, false);
                }
            }
        }
    }
}
