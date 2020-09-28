package supercoder79.cavebiomes.cave;

import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void decorate(ChunkRegion world, Chunk chunk, Set<BlockPos> positions) {
        List<BlockPos> waterPositions = new ArrayList<>();

        for (BlockPos pos : positions) {
            if (this.grass) {
                //grass generation
                if (chunk.getBlockState(pos.down()).isOpaque()) {
                    if (world.getRandom().nextInt(3) == 0) {
                        chunk.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), false);
                        if (world.getRandom().nextInt(2) == 0) {
                            chunk.setBlockState(pos, Blocks.GRASS.getDefaultState(), false);
                        }
                    }
                }
            }

            if (this.water) {
                //water generation (yes i know this is cursed)
                if (world.getRandom().nextInt(4) == 0) {
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
        }

        for (BlockPos pos : waterPositions) {
            chunk.setBlockState(pos, Blocks.WATER.getDefaultState(), false);
        }

        if (this.vines) {
            //vine generation (yeah this is even more cursed)
            for (BlockPos pos : positions) {
                if (world.getRandom().nextInt(4) == 0 && (pos.getX() & 15) != 15 && (pos.getZ() & 15) != 15 && (pos.getX() & 15) != 0 && (pos.getZ() & 15) != 0) {
                    int num = world.getRandom().nextInt(4);
                    if (num == 0) {
                        if (chunk.getBlockState(pos.north()).isOpaque()) {
                            chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.NORTH, true), false);
                        }
                    }
                    if (num == 1) {
                        if (chunk.getBlockState(pos.south()).isOpaque()) {
                            chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, true), false);
                        }
                    }
                    if (num == 2) {
                        if (chunk.getBlockState(pos.west()).isOpaque()) {
                            chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.WEST, true), false);
                        }
                    }
                    if (num == 3) {
                        if (chunk.getBlockState(pos.east()).isOpaque()) {
                            chunk.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.EAST, true), false);
                        }
                    }
                }
            }
        }
    }
}
