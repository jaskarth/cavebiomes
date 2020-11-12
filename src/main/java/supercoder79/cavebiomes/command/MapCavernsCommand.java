package supercoder79.cavebiomes.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;
import supercoder79.cavebiomes.world.carver.PerlerpCarver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class MapCavernsCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapcaverns")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);

        long seed = source.getWorld().getSeed();
        OctavePerlinNoiseSampler caveNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed), IntStream.rangeClosed(-5, 0));
        OctavePerlinNoiseSampler offsetNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed - 576), IntStream.rangeClosed(-2, 0));

        for (int x = -512; x < 512; x++) {
            if (x % 128 == 0) {
                source.sendFeedback(new LiteralText(((x + 512) / 1024.0) * 100 + "%"), false);
            }

            for (int z = -512; z < 512; z++) {
                double[] buffer = new double[9];
                PerlerpCarver.sampleNoiseColumn(buffer, x, z, caveNoise, offsetNoise);

                boolean isAir = false;

                for (double density : buffer) {
                    if (density < 0) {
                        isAir = true;
                        break;
                    }
                }

                img.setRGB(x + 512, z + 512, isAir ? 0xe09fd9 : 0xadadad);
            }
        }

        // save the image
        Path p = Paths.get("cavernmap.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        source.sendFeedback(new LiteralText("Mapped caverns!"), false);

        return 0;
    }
}
