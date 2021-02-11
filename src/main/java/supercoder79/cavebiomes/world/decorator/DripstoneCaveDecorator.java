package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Thickness;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public class DripstoneCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, OpenSimplexNoise noise, BlockPos pos, DecorationContext context) {
        if (context != DecorationContext.AIR) {
            return;
        }

        // Try to set a block in every direction
        for (Direction direction : Direction.values()) {
            trySet(world, random, pos.offset(direction));
        }

        // Upwards spike
        if (random.nextInt(10) == 0 && world.getBlockState(pos.down()).isOpaque()) {
            int height = 0;

            // Test height upwards
            for (int i = 0; i <= random.nextInt(6); i++) {
                BlockPos local = pos.up(i);
                if (isBlocking(world.getBlockState(local))) {
                    break;
                }

                height++;
            }

            int baseHeight = 0;
            int frustumHeight = height - 2;
            int tipHeight = height - 1;

            for (int i = 0; i < height; i++) {
                BlockPos local = pos.up(i);
                boolean isWater = world.getFluidState(local).isIn(FluidTags.WATER);

                Thickness dripstoneType = Thickness.MIDDLE;
                if (i == baseHeight) {
                    dripstoneType = Thickness.BASE;
                }

                if (i == frustumHeight) {
                    dripstoneType = Thickness.FRUSTUM;
                }

                if (i == tipHeight) {
                    dripstoneType = Thickness.TIP;
                }

                world.setBlockState(local, Blocks.POINTED_DRIPSTONE.getDefaultState().with(Properties.VERTICAL_DIRECTION, Direction.UP).with(Properties.THICKNESS, dripstoneType).with(Properties.WATERLOGGED, isWater), 3);

            }
        }

        // Downwards spike
        if (random.nextInt(6) == 0 && world.getBlockState(pos.up()).isOpaque()) {
            int depth = 0;

            // Test height upwards
            for (int i = 0; i <= random.nextInt(6); i++) {
                BlockPos local = pos.down(i);
                if (isBlocking(world.getBlockState(local))) {
                    break;
                }

                depth++;
            }

            int baseDepth = 0;
            int frustumDepth = depth - 2;
            int tipDepth = depth - 1;

            for (int i = 0; i < depth; i++) {
                BlockPos local = pos.down(i);
                boolean isWater = world.getFluidState(local).isIn(FluidTags.WATER);

                Thickness dripstoneType = Thickness.MIDDLE;
                if (i == baseDepth) {
                    dripstoneType = Thickness.BASE;
                }

                if (i == frustumDepth) {
                    dripstoneType = Thickness.FRUSTUM;
                }

                if (i == tipDepth) {
                    dripstoneType = Thickness.TIP;
                }

                world.setBlockState(local, Blocks.POINTED_DRIPSTONE.getDefaultState().with(Properties.VERTICAL_DIRECTION, Direction.DOWN).with(Properties.THICKNESS, dripstoneType).with(Properties.WATERLOGGED, isWater), 3);
            }
        }

        // TODO: merge tip testing
    }

    private void trySet(ChunkRegion world, Random random, BlockPos pos) {
        if (random.nextInt(6) == 0) {
            if (world.getBlockState(pos).isOpaque()) {
                world.setBlockState(pos, Blocks.DRIPSTONE_BLOCK.getDefaultState(), 3);
            }
        }
    }

    private static boolean isBlocking(BlockState state) {
        return state.isOpaque() || state.isOf(Blocks.POINTED_DRIPSTONE) || state.getFluidState().isIn(FluidTags.LAVA);
    }
}
