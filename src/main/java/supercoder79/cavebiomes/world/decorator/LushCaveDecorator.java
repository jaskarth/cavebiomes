package supercoder79.cavebiomes.world.decorator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public class LushCaveDecorator extends CaveDecorator {
    @Override
    public void decorate(ChunkRegion world, Random random, OpenSimplexNoise noise, BlockPos pos) {
        // Set Moss and clay
        double density = noise.sample(pos, 44.0);
        setBlocksAround(world, random, pos, 0.1 + Math.abs(density / 2.5), Blocks.MOSS_BLOCK.getDefaultState());
        setBlocksAround(world, random, pos, 0.15 + (density / 3.0), Blocks.CLAY.getDefaultState());

        boolean validCeiling = true;
        boolean validFloor = true;
        // Place vines
        if (random.nextInt(3) == 0) {
            Direction direction = Direction.Type.HORIZONTAL.random(random);
            generateVines(world, random, pos, direction);
            validCeiling = false;
        }

        if (random.nextInt((int) (12 - Math.abs(density * 3))) == 0) {
            generateCaveVines(world, random, pos);
            validCeiling = false;
        }


        // Spawn Water
        int waterChance = (int) Math.max(1, 4 - density * 5.35);
        if (random.nextInt(waterChance) == 0) {
            if (world.getBlockState(pos.down()).isOpaque() && world.getBlockState(pos).isAir()) {
                if (shouldSpawnWater(world, pos)) {
                    validFloor = false;
                    world.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 3);
                }
            }
        }

        // if we can always spawn water here, have a rare chance of spawning axolotl
        if (waterChance == 1) {
            if (random.nextInt(128) == 0) {
                spawnAxolotls(world, random, pos, 1);
            }
        }

        if (validFloor) {
            if (random.nextInt(16) == 0) {
                if (world.getBlockState(pos.down()).isOf(Blocks.MOSS_BLOCK)) {
                    if (random.nextInt(3) == 0) {
                        world.setBlockState(pos, Blocks.FLOWERING_AZALEA.getDefaultState(), 3);
                    } else {
                        world.setBlockState(pos, Blocks.AZALEA.getDefaultState(), 3);
                    }
                }
            } else if (random.nextInt(6) == 0) {
                if (isOpaque(world.getBlockState(pos.down()))) {
                    world.setBlockState(pos, Blocks.MOSS_CARPET.getDefaultState(), 3);
                }
            }
        }

        if (validCeiling) {
            if (random.nextInt(128) == 0) {
                if (world.getBlockState(pos.up()).isOpaque()) {
                    world.setBlockState(pos, Blocks.SPORE_BLOSSOM.getDefaultState(), 3);
                }
            }
        }

        // TODO: dripleaf
        // TODO: roots
    }

    @Override
    public void spawn(ChunkRegion world, Random random, BlockPos pos, SpawnContext context) {
        if (context.inWater) {
            if (random.nextInt(2) == 0) {
                if (world.getBlockState(pos).getFluidState().isEmpty()) {
                    return;
                }

                int count = 2 + random.nextInt(3);
                spawnAxolotls(world, random, pos, count);
            }
        }
    }

    private static void spawnAxolotls(ChunkRegion world, Random random, BlockPos pos, int count) {
        for (int i = 0; i < count; i++) {
            AxolotlEntity axolotl = EntityType.AXOLOTL.create(world.toServerWorld());
            axolotl.refreshPositionAndAngles(pos, random.nextFloat() * 360,  0);
            axolotl.initialize(world, world.getLocalDifficulty(pos), SpawnReason.CHUNK_GENERATION, null, null);
            world.spawnEntityAndPassengers(axolotl);
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

    private void generateVines(ChunkRegion world, Random random, BlockPos pos, Direction direction) {
        int height = random.nextInt(2) + 1;

        if (isOpaque(world.getBlockState(pos.offset(direction))) && world.getBlockState(pos).isAir()) {
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

    private static void generateCaveVines(ChunkRegion world, Random random, BlockPos pos) {
        int height = random.nextInt(6) + 1;

        // Don't generate if top is not solid
        if (!world.getBlockState(pos.up()).isOpaque()) {
            return;
        }

        // Generate vine
        for (int i = 0; i <= height; i++) {
            BlockPos local = pos.down(i);

            // If we detect stone below, place head and return
            if (!world.getBlockState(local.down()).isAir() || height == i) {
                world.setBlockState(local, Blocks.CAVE_VINES_HEAD.getDefaultState().with(Properties.BERRIES, random.nextInt(8) == 0), 3);
                return;
            }

            if (world.getBlockState(local).isAir()) {
                world.setBlockState(local, Blocks.CAVE_VINES_BODY.getDefaultState().with(Properties.BERRIES, random.nextInt(8) == 0), 3);
            }
        }
    }

    private static boolean shouldSpawnWater(ChunkRegion world, BlockPos pos) {
        // Check 4 sides and bottom
        return isValidForWater(world, pos.down().north()) &&
               isValidForWater(world, pos.down().south()) &&
               isValidForWater(world, pos.down().west()) &&
               isValidForWater(world, pos.down().east()) &&
               isValidForWater(world, pos.down(2));
    }

    private static boolean isValidForWater(ChunkRegion world, BlockPos pos) {
        return isOpaque(world.getBlockState(pos)) || world.getBlockState(pos).isOf(Blocks.WATER);
    }

    private static boolean isOpaque(BlockState state) {
        return state.isOpaque() && !state.isOf(Blocks.MOSS_CARPET);
    }
}
