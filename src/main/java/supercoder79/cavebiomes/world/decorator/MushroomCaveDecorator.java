package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public class MushroomCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, OpenSimplexNoise noise, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos local = pos.offset(direction);
            if (!world.getBlockState(local).isOpaque()) {
                continue;
            }

            double dirtDensity = noise.sample(local, 32.0) * 0.65 + (random.nextDouble() * 0.85);
            if (dirtDensity > 0.45) {
                world.setBlockState(local, Blocks.DIRT.getDefaultState(), 3);
            }
        }

        if (world.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
            if (random.nextInt(3) == 0) {
                world.setBlockState(pos.down(), Blocks.MYCELIUM.getDefaultState(), 3);
                if (random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        world.setBlockState(pos, Blocks.BROWN_MUSHROOM.getDefaultState(), 3);
                    } else {
                        world.setBlockState(pos, Blocks.RED_MUSHROOM.getDefaultState(), 3);
                    }
                }
            }
        }
    }
}
