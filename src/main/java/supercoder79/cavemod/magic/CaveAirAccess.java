package supercoder79.cavemod.magic;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

//i regret making this
public interface CaveAirAccess {
    void reset();
    Set<BlockPos> retrieve();
}
