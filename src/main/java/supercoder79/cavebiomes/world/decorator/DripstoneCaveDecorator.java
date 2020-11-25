package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.class_5691;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;

import java.util.Random;

public class DripstoneCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, BlockPos pos) {
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
                if (world.getBlockState(local).isOpaque()) {
                    break;
                }

                height++;
            }

            int baseHeight = 0;
            int frustumHeight = height - 2;
            int tipHeight = height - 1;

            for (int i = 0; i < height; i++) {
                BlockPos local = pos.up(i);

                class_5691 dripstoneType = class_5691.field_28067;
                if (i == baseHeight) {
                    dripstoneType = class_5691.field_28068;
                }

                if (i == frustumHeight) {
                    dripstoneType = class_5691.field_28066;
                }

                if (i == tipHeight) {
                    dripstoneType = class_5691.field_28064;
                }

                world.setBlockState(local, Blocks.POINTED_DRIPSTONE.getDefaultState().with(Properties.field_28062, Direction.UP).with(Properties.field_28063, dripstoneType), 3);

            }
        }

        // Downwards spike
        if (random.nextInt(6) == 0 && world.getBlockState(pos.up()).isOpaque()) {
            int depth = 0;

            // Test height upwards
            for (int i = 0; i <= random.nextInt(6); i++) {
                BlockPos local = pos.down(i);
                if (world.getBlockState(local).isOpaque()) {
                    break;
                }

                depth++;
            }

            int baseDepth = 0;
            int frustumDepth = depth - 2;
            int tipDepth = depth - 1;

            for (int i = 0; i < depth; i++) {
                BlockPos local = pos.down(i);

                class_5691 dripstoneType = class_5691.field_28067;
                if (i == baseDepth) {
                    dripstoneType = class_5691.field_28068;
                }

                if (i == frustumDepth) {
                    dripstoneType = class_5691.field_28066;
                }

                if (i == tipDepth) {
                    dripstoneType = class_5691.field_28064;
                }

                world.setBlockState(local, Blocks.POINTED_DRIPSTONE.getDefaultState().with(Properties.field_28062, Direction.DOWN).with(Properties.field_28063, dripstoneType), 3);

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
        return state.isOpaque() || state.isOf(Blocks.POINTED_DRIPSTONE);
    }
}
