package supercoder79.cavebiomes.world.carver;

import net.minecraft.world.gen.carver.Carver;

public class CaveBiomeCarvers {
    public static final Carver<SimpleCarverConfig> ROOM = new RoomCarver(SimpleCarverConfig.CODEC);
    public static final Carver<SimpleCarverConfig> VERTICAL = new VerticalCarver(SimpleCarverConfig.CODEC);
    public static final Carver<SimpleCarverConfig> HORIZONTAL = new HorizontalCarver(SimpleCarverConfig.CODEC);
    public static final Carver<SimpleCarverConfig> LAVA_ROOM = new LavaRoomCarver(SimpleCarverConfig.CODEC);
    public static final Carver<SimpleCarverConfig> PERLERP = new PerlerpCarver(SimpleCarverConfig.CODEC);
}
