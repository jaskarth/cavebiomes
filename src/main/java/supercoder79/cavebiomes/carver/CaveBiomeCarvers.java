package supercoder79.cavebiomes.carver;

import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

public class CaveBiomeCarvers {
    public static final Carver<ProbabilityConfig> ROOM = new RoomCarver(ProbabilityConfig.CODEC);
    public static final Carver<ProbabilityConfig> VERTICAL = new VerticalCarver(ProbabilityConfig.CODEC);
    public static final Carver<ProbabilityConfig> HORIZONTAL = new HorizontalCarver(ProbabilityConfig.CODEC);
    public static final Carver<ProbabilityConfig> LAVA_ROOM = new LavaRoomCarver(ProbabilityConfig.CODEC);
}
