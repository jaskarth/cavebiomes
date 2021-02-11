package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public class MushroomCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, OpenSimplexNoise noise, BlockPos pos, DecorationContext context) {
        if (context != DecorationContext.AIR) {
            return;
        }

        for (Direction direction : Direction.values()) {
            BlockPos local = pos.offset(direction);
            BlockState state = world.getBlockState(local);
            if (!state.isOpaque()) {
                continue;
            }

            if (state.isOf(Blocks.BROWN_MUSHROOM_BLOCK) || state.isOf(Blocks.RED_MUSHROOM_BLOCK) || state.isOf(Blocks.MUSHROOM_STEM)) {
                continue;
            }

            double dirtDensity = noise.sample(local, 32.0) * 0.65 + (random.nextDouble() * 0.95);
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

            if (random.nextInt(20) == 0) {
                generateStandingMushroom(world, random, pos);
            }
        }

        tryPlaceSideMushroom(world, random, pos);
    }

    private static void tryPlaceSideMushroom(ChunkRegion world, Random random, BlockPos pos) {
        if (random.nextInt(40) == 0) {
            Direction direction = Direction.Type.HORIZONTAL.random(random);
            BlockPos local = pos.offset(direction);

            if (world.getBlockState(local).isOpaque() && world.getBlockState(pos.down()).isAir()) {
                generateSideMushroom(world, random, local);
            }
        }
    }

    private static void generateSideMushroom(ChunkRegion world, Random random, BlockPos pos) {
        BlockState state = random.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState() : Blocks.RED_MUSHROOM_BLOCK.getDefaultState();

        for(int x = -2; x <= 2; x++) {
            for(int z = -2; z <= 2; z++) {
                if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                    continue;
                }

                BlockPos local = pos.add(x, 0, z);
                if (world.getBlockState(local).getMaterial().isReplaceable()) {
                    world.setBlockState(local, state, 3);
                }
            }
        }
    }

    private static void generateStandingMushroom(ChunkRegion world, Random random, BlockPos pos) {
        BlockState state = random.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState() : Blocks.RED_MUSHROOM_BLOCK.getDefaultState();
        int obstructed = 0;

        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                for(int y = 0; y <= 3; y++) {
                    if (world.getBlockState(pos.add(x, y, z)).isOpaque()) {
                        obstructed++;

                        if (obstructed > 6) {
                            return;
                        }
                    }
                }
            }
        }

        int height = random.nextInt(2) + 1;
        for (int i = 0; i < height; i++) {
            world.setBlockState(pos.up(i), Blocks.MUSHROOM_STEM.getDefaultState(), 3);
        }

        for(int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.setBlockState(pos.add(x, height, z), state, 3);
            }
        }
    }
}
