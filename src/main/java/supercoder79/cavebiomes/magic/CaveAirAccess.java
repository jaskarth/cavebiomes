package supercoder79.cavebiomes.magic;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public interface CaveAirAccess {
    void reset();
    Set<BlockPos> retrieve();
}
