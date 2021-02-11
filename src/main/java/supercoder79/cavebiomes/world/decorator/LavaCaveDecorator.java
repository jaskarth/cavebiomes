package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public class LavaCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, OpenSimplexNoise noise, BlockPos pos, DecorationContext context) {
        double density = noise.sample(pos, 30.0);

        if (context == DecorationContext.AIR) {
            int waterChance = (int) Math.max(1, 3 - density * 7.35);
            if (random.nextInt(waterChance) == 0) {
                if (world.getBlockState(pos.down()).isOpaque() && world.getBlockState(pos).isAir()) {
                    if(shouldSpawnLava(world, pos)) {
                        world.setBlockState(pos.down(), Blocks.LAVA.getDefaultState(), 3);
                        setBlocksAround(world, random, pos.down(), 0.35 + (density / 2.8), Blocks.MAGMA_BLOCK.getDefaultState());
                    }
                }
            }
        } else if (context == DecorationContext.LAVA) {

        }
    }

    private static void setBlocksAround(ChunkRegion world, Random random, BlockPos pos, double chance, BlockState state) {
        for (Direction direction : Direction.values()) {
            BlockPos local = pos.offset(direction);
            if (random.nextDouble() < chance) {
                if (world.getBlockState(local).isOf(Blocks.STONE)) {
                    world.setBlockState(local, state, 3);
                }
            }
        }
    }

    private static boolean shouldSpawnLava(ChunkRegion world, BlockPos pos) {
        // Check 4 sides and bottom
        return isValidForLava(world, pos.down().north()) &&
                isValidForLava(world, pos.down().south()) &&
                isValidForLava(world, pos.down().west()) &&
                isValidForLava(world, pos.down().east()) &&
                isValidForLava(world, pos.down(2));
    }

    private static boolean isValidForLava(ChunkRegion world, BlockPos pos) {
        return world.getBlockState(pos).isOpaque() || world.getBlockState(pos).isOf(Blocks.LAVA);
    }
}
