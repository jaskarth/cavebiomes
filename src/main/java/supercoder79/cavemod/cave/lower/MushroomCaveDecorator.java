package supercoder79.cavemod.cave.lower;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavemod.cave.CaveDecorator;
import net.minecraft.world.ChunkRegion;

import java.util.Set;

public class MushroomCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (chunk.getBlockState(pos.down()).isOpaque()) {
                if (world.getRandom().nextInt(3) == 0) {
                    chunk.setBlockState(pos.down(), Blocks.MYCELIUM.getDefaultState(), false);
                    if (world.getRandom().nextInt(2) == 0) {
                        if (world.getRandom().nextInt(2) == 0) {
                            chunk.setBlockState(pos, Blocks.BROWN_MUSHROOM.getDefaultState(), false);
                        } else {
                            chunk.setBlockState(pos, Blocks.RED_MUSHROOM.getDefaultState(), false);
                        }
                    }
                }
            }
        }
    }
}
