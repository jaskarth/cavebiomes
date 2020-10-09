package supercoder79.cavebiomes.cave;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class SingleBlockStateCaveDecorator extends CaveDecorator {
    private final BlockState state;
    
    public SingleBlockStateCaveDecorator(BlockState state) {
        this.state = state;
    }

    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            // Try to set a  block in every direction
            for (Direction direction : Direction.values()) {
                trySet(chunk, pos.offset(direction));
            }
        }
    }

    private void trySet(Chunk chunk, BlockPos pos) {
        if (chunk.getBlockState(pos).isOpaque()) {
            chunk.setBlockState(pos, this.state, false);
        }
    }
}
