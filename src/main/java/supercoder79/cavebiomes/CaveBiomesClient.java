package supercoder79.cavebiomes;

import net.fabricmc.api.ClientModInitializer;
import supercoder79.cavebiomes.client.GoVote;

public class CaveBiomesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GoVote.init();
    }
}
