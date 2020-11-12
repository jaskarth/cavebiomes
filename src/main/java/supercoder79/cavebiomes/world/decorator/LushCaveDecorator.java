package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;

import java.util.Random;

public class LushCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, BlockPos pos) {
        // Try to set a dirt block in every direction
        for (Direction direction : Direction.values()) {
            trySet(world, random, pos.offset(direction));
        }

        // make shrub
        // ecotones moment
        if (world.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
            if (random.nextInt(32) == 0) {
                generateShrub(world, random, pos);
            }
        }

        Direction direction = Direction.Type.HORIZONTAL.random(random);
        generateVines(world, random, pos, direction);
    }

    private void trySet(ChunkRegion world, Random random, BlockPos pos) {
        // Generate dirt 75% of the time
        if (random.nextInt(4) != 0) {
            if (world.getBlockState(pos).isOf(Blocks.STONE)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
                // Generate grass sometimes
                if (random.nextInt(2) == 0) {
                    if (!world.getBlockState(pos.up()).isOpaque()) {
                        world.setBlockState(pos.up(), Blocks.GRASS.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    private void generateVines(ChunkRegion world, Random random, BlockPos pos, Direction direction) {

        int height = random.nextInt(4) + 2;

        if (canGenerateVine(world.getBlockState(pos.offset(direction))) && world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true), 3);
        } else {
            return;
        }

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int i = 0; i < height; i++) {
            mutable.move(Direction.DOWN);

            if (!world.getBlockState(mutable).isAir()) {
                return;
            }

            world.setBlockState(mutable, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true), 3);
        }
    }

    private void generateShrub(ChunkRegion world, Random random, BlockPos pos) {
        int height = random.nextInt(2) + 1;

        BlockPos.Mutable mutable = pos.mutableCopy();

        // Check to make sure that there's space
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y < height + 2; y++) {
                    mutable.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

                    if (world.getBlockState(mutable).isOpaque()) {
                        return;
                    }
                }
            }
        }

        for (int y = 0; y < height; y++) {
            world.setBlockState(pos.up(y), Blocks.OAK_LOG.getDefaultState(), 3);
        }

        for (Direction direction : Direction.values()) {
            BlockPos local = pos.up(height - 1).offset(direction);

            if (world.getBlockState(local).getMaterial().isReplaceable()) {
                world.setBlockState(local, Blocks.OAK_LEAVES.getDefaultState(), 3);
            }
        }
    }

    private static boolean canGenerateVine(BlockState state) {
        return state.isOpaque() || state.isIn(BlockTags.LEAVES);
    }
}
