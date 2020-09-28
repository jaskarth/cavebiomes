package supercoder79.cavebiomes.cave.lower;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import supercoder79.cavebiomes.cave.CaveDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FluidCaveDecorator extends CaveDecorator {
    private final BlockState state;
    private final int chance;

    public FluidCaveDecorator(BlockState state, int chance) {
        this.state = state;
        this.chance = chance;
    }

    @Override
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        List<BlockPos> setPositions = new ArrayList<>();

        for (BlockPos pos : positions) {
            if (world.getRandom().nextInt(chance) == 0) {
                if (chunk.getBlockState(pos.down()).isOpaque() && chunk.getBlockState(pos).isAir()) {
                    if (chunk.getBlockState(pos.down().north()).isOpaque() &&
                            chunk.getBlockState(pos.down().south()).isOpaque() &&
                            chunk.getBlockState(pos.down().west()).isOpaque() &&
                            chunk.getBlockState(pos.down().east()).isOpaque()) {
                        setPositions.add(pos.down());
                    }
                }
            }
        }

        for (BlockPos pos : setPositions) {
            chunk.setBlockState(pos, this.state, false);
        }
    }
}
