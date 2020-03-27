package supercoder79.cavebiomes.cave.lower;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavebiomes.cave.CaveDecorator;
import net.minecraft.world.ChunkRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LavaCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        List<BlockPos> lavaPositions = new ArrayList<>();

        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(3) == 0) {
                if (chunk.getBlockState(pos.down()).isOpaque() && chunk.getBlockState(pos).isAir()) {
                    if (chunk.getBlockState(pos.down().north()).isOpaque() &&
                            chunk.getBlockState(pos.down().south()).isOpaque() &&
                            chunk.getBlockState(pos.down().west()).isOpaque() &&
                            chunk.getBlockState(pos.down().east()).isOpaque()) {
                        lavaPositions.add(pos.down());
                    }
                }
            }
        }

        for (BlockPos pos : lavaPositions) {
            chunk.setBlockState(pos, Blocks.LAVA.getDefaultState(), false);
        }
    }
}
