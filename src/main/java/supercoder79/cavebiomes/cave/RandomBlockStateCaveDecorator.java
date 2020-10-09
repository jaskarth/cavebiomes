package supercoder79.cavebiomes.cave;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class RandomBlockStateCaveDecorator extends CaveDecorator {
    private final BlockState state;
    private final int chance;

    public RandomBlockStateCaveDecorator(BlockState state, int chance) {
        this.state = state;
        this.chance = chance;
    }

    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            // Try to set a block in every direction
            for (Direction direction : Direction.values()) {
                trySet(world, chunk, pos.offset(direction));
            }
        }
    }

    private void trySet(ChunkRegion world, Chunk chunk, BlockPos pos) {
        if (world.getRandom().nextInt(this.chance) == 0) {
            if (chunk.getBlockState(pos).isOpaque()) {
                chunk.setBlockState(pos, this.state, false);
            }
        }
    }
}
