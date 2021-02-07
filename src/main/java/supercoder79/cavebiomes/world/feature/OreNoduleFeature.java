package supercoder79.cavebiomes.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public class OreNoduleFeature extends Feature<DefaultFeatureConfig> {
    private long seed;
    private OpenSimplexNoise noise;
    private OpenSimplexNoise oreNoise;

    public OreNoduleFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();

        if (this.noise == null || this.seed != world.getSeed()) {
            this.noise = new OpenSimplexNoise(world.getSeed() + 443);
            this.oreNoise = new OpenSimplexNoise(world.getSeed() - 321);
            this.seed = world.getSeed();
        }

        int startX = pos.getX();
        int startZ = pos.getZ();

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int x = 0; x < 16; x++) {
            mutable.setX(startX + x);

            for (int z = 0; z < 16; z++) {
                mutable.setZ(startZ + z);

                int maxY = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());

                for (int y = 0; y < maxY; y++) {
                    mutable.setY(y);
                    double noiseAt = this.noise.sample(mutable.getX() / 24.0, y / 24.0, mutable.getZ() / 24.0);
                    noiseAt += Math.max((8.5 / y) - 1, 0); // lower bound
                    noiseAt += Math.max((5.0 / (maxY - y)) - 1, 0); // upper bound

                    if (noiseAt < -0.7665) {

                        // Don't replace water
                        if (!world.getBlockState(mutable).getFluidState().isIn(FluidTags.WATER)) {
                            world.setBlockState(mutable, Blocks.AIR.getDefaultState(), 3);
                        }

                        for (Direction direction : Direction.values()) {
                            BlockPos local = mutable.offset(direction);

                            if (random.nextInt(Math.max(y / 2, 12)) == 0) {
                                if (world.getBlockState(local).isOpaque()) {
                                    double oreSelector = this.oreNoise.sample(local.getX() / 140.0, local.getZ() / 140.0);
                                    BlockState state = oreSelector > 0 ? Blocks.DIAMOND_ORE.getDefaultState() : Blocks.EMERALD_ORE.getDefaultState();

                                    // Normalize for selection
                                    if (random.nextDouble() > Math.abs(oreSelector)) {
                                        state = Blocks.LAPIS_ORE.getDefaultState();
                                    }

                                    world.setBlockState(local, state, 3);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
