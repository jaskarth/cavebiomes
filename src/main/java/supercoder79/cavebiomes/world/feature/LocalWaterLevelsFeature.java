package supercoder79.cavebiomes.world.feature;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.cavebiomes.CaveBiomes;
import supercoder79.cavebiomes.util.FloodFill;
import supercoder79.cavebiomes.world.layer.WaterGenerator;
import supercoder79.cavebiomes.world.layer.water.WaterBorderLayer;

import java.util.Random;

public class LocalWaterLevelsFeature extends Feature<DefaultFeatureConfig> {
    public LocalWaterLevelsFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();

        RegistryKey<World> key = world.toServerWorld().getRegistryKey();
        if (!CaveBiomes.CONFIG.whitelistedDimensions.contains(key.getValue().toString())) {
            return false;
        }

        ChunkRandom random2 = new ChunkRandom();
        random2.setPopulationSeed(world.getSeed(), pos.getX(), pos.getZ());

        FloodFill.floodFill((ChunkRegion) world, random2, new BlockPos(pos.getX() + random2.nextInt(16), random2.nextInt(50) + 12, pos.getZ() + random2.nextInt(16)), ImmutableSet.of());

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int x = 0; x < 16; x++) {
            mutable.setX(pos.getX() + x);

            for (int z = 0; z < 16; z++) {
                mutable.setZ(pos.getZ() + z);

                int sample = WaterGenerator.getSample(world.getSeed(), mutable.getX(), mutable.getZ());

                int waterLevel = WaterGenerator.getWaterLevel(sample);
                int border = WaterGenerator.getBorder(sample);

                if (border == 0) {
                    int maxVal = 0;
                    double total = 0;
                    for (int x1 = -2; x1 <= 2; x1++) {
                        for (int z1 = -2; z1 <= 2; z1++) {
                            int val = WaterGenerator.getBorder(WaterGenerator.getSample(world.getSeed(), mutable.getX() + x1, mutable.getZ() + z1));
                            maxVal = Math.max(maxVal, val);

                            total += val;
                        }
                    }

                    // No idea why I'm doing this. But it works, so... job well done I guess?
                    border = (int) (total / (0.275 * Math.E * Math.log(maxVal)));
                    maxVal = (int) (maxVal * 0.9125);
                    border = Math.min(border, maxVal);
                }

                // TODO: carving mask
                for (int y = 0; y < border; y++) {
                    mutable.setY(y);

                    BlockState state = world.getBlockState(mutable);
                    if (world.getBlockState(mutable).isAir() || state.isOf(Blocks.LAVA)) {
                        world.setBlockState(mutable, Blocks.STONE.getDefaultState(), 3);

                        if (y == border - 1 && random.nextInt(4) == 0) {
                            world.setBlockState(mutable, Blocks.WATER.getDefaultState(), 3);
                            world.getFluidTickScheduler().schedule(mutable, Fluids.WATER, 0);
                        }
                    }
                }

                for (int y = 0; y < waterLevel; y++) {
                    mutable.setY(y);

                    BlockState state = world.getBlockState(mutable);
                    if (state.isAir()) {
                        world.setBlockState(mutable, Blocks.WATER.getDefaultState(), 3);
                    } else if (state.isOf(Blocks.LAVA) && y == 10) {
                        world.setBlockState(mutable, Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
