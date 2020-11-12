package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;

import java.util.Random;

public class JungleCaveDecorator extends CaveDecorator {

    private final boolean grass;
    private final boolean water;
    private final boolean vines;

    public JungleCaveDecorator(boolean grass, boolean water, boolean vines) {
        this.grass = grass;
        this.water = water;
        this.vines = vines;
    }

    @Override
    public void decorate(ChunkRegion world, Random random, BlockPos pos) {
        if (this.grass) {
            //grass generation
            if (world.getBlockState(pos.down()).isOpaque()) {
                if (random.nextInt(3) == 0) {
                    world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 3);
                    if (random.nextInt(2) == 0) {
                        world.setBlockState(pos, Blocks.GRASS.getDefaultState(), 3);
                    }
                }
            }
        }

        if (this.water) {
            //water generation (yes i know this is cursed)
            if (random.nextInt(4) == 0) {
                if (world.getBlockState(pos.down()).isOpaque() && world.getBlockState(pos).isAir()) {
                    if (shouldSpawnWater(world, pos)) {
                        world.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 3);
                    }
                }
            }
        }

        if (this.vines) {
            //vine generation (yeah this is even more cursed)
            int num = random.nextInt(4);
            if (num == 0) {
                if (world.getBlockState(pos.north()).isOpaque()) {
                    world.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.NORTH, true), 3);
                }
            }
            if (num == 1) {
                if (world.getBlockState(pos.south()).isOpaque()) {
                    world.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, true), 3);
                }
            }
            if (num == 2) {
                if (world.getBlockState(pos.west()).isOpaque()) {
                    world.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.WEST, true), 3);
                }
            }
            if (num == 3) {
                if (world.getBlockState(pos.east()).isOpaque()) {
                    world.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.EAST, true), 3);
                }
            }
        }
    }

    private static boolean shouldSpawnWater(ChunkRegion world, BlockPos pos) {
        return  isValidForWater(world, pos.down().north()) &&
                isValidForWater(world, pos.down().south()) &&
                isValidForWater(world, pos.down().west()) &&
                isValidForWater(world, pos.down().east());
    }

    private static boolean isValidForWater(ChunkRegion world, BlockPos pos) {
        return world.getBlockState(pos).isOpaque() || world.getBlockState(pos).isOf(Blocks.WATER);
    }
}
