package supercoder79.cavebiomes.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import java.util.Random;

public abstract class CaveDecorator {
    public abstract void decorate(ChunkRegion world, Random random, OpenSimplexNoise noise, BlockPos pos);

    public void spawn(ChunkRegion world, Random random, BlockPos pos, SpawnContext context) {

    }

    public boolean overrideUpper() {
        return false;
    }

    public enum SpawnContext {
        LOCAL_WATER_LEVEL_POOL(true),
        LOCAL_WATER_LEVEL_AREA(true);

        public final boolean inWater;

        SpawnContext(boolean inWater) {
            this.inWater = inWater;
        }
    }
}
