package supercoder79.cavebiomes.cave;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkRegion;

import java.util.HashSet;
import java.util.Set;

public class SingleBlockStateCaveDecorator extends CaveDecorator {
    private BlockState state;
    //TODO: make a second, less icy cave decorator
    
    public SingleBlockStateCaveDecorator(BlockState state) {
        this.state = state;
    }

    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (chunk.getBlockState(pos.east()).isOpaque()) {
                chunk.setBlockState(pos.east(), state, false);
            }
            if (chunk.getBlockState(pos.west()).isOpaque()) {
                chunk.setBlockState(pos.west(), state, false);
            }
            if (chunk.getBlockState(pos.north()).isOpaque()) {
                chunk.setBlockState(pos.north(), state, false);
            }
            if (chunk.getBlockState(pos.south()).isOpaque()) {
                chunk.setBlockState(pos.south(), state, false);
            }
            if (chunk.getBlockState(pos.up()).isOpaque()) {
                chunk.setBlockState(pos.up(), state, false);
            }
            if (chunk.getBlockState(pos.down()).isOpaque()) {
                chunk.setBlockState(pos.down(), state, false);
            }
        }
    }
}
