package supercoder79.cavebiomes.cave;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

public class IceCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            // Try to set a packed ice block in every direction
            for (Direction direction : Direction.values()) {
                trySet(world, chunk, pos.offset(direction));
            }

            // Set snow on top of solid blocks
            BlockState state = chunk.getBlockState(pos.down());
            if (state.isOpaque() && state.isOf(Blocks.SNOW)) {
                if (world.getRandom().nextInt(6) == 0) {
                    chunk.setBlockState(pos, Blocks.SNOW.getDefaultState(), false);
                }
            }
        }
    }

    private static void trySet(ChunkRegion world, Chunk chunk, BlockPos pos) {
        if (world.getRandom().nextInt(16) == 0) {
            BlockState state = chunk.getBlockState(pos);
            if (state.isOpaque() && state.isOf(Blocks.SNOW)) {
                chunk.setBlockState(pos, Blocks.PACKED_ICE.getDefaultState(), false);
            }
        }
    }
}
