package supercoder79.cavebiomes.config;

import supercoder79.cavebiomes.CaveBiomes;

import java.util.Arrays;
import java.util.List;

public class ConfigData {
    public String configVersion = CaveBiomes.VERSION;
    public boolean generateOreCaves = true;
    public List<String> whitelistedDimensions = Arrays.asList("minecraft:overworld");
}
