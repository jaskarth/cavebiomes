package supercoder79.cavebiomes.cave.lower;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavebiomes.cave.CaveDecorator;
import net.minecraft.world.ChunkRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WaterCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        List<BlockPos> waterPositions = new ArrayList<>();

        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(2) == 0) {
                if (chunk.getBlockState(pos.down()).isOpaque() && chunk.getBlockState(pos).isAir()) {
                    if (chunk.getBlockState(pos.down().north()).isOpaque() &&
                            chunk.getBlockState(pos.down().south()).isOpaque() &&
                            chunk.getBlockState(pos.down().west()).isOpaque() &&
                            chunk.getBlockState(pos.down().east()).isOpaque()) {
                        waterPositions.add(pos.down());
                    }
                }
            }
        }

        for (BlockPos pos : waterPositions) {
            chunk.setBlockState(pos, Blocks.WATER.getDefaultState(), false);
        }
    }
}
