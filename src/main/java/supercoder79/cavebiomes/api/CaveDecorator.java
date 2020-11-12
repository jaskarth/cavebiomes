package supercoder79.cavebiomes.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;

import java.util.Random;

public abstract class CaveDecorator {
    public abstract void decorate(ChunkRegion world, Random random, BlockPos pos);
}
