package supercoder79.cavebiomes.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;
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
        ChunkRandom chunkRandom = new ChunkRandom(seed);
        OctavePerlinNoiseSampler caveNoise = new OctavePerlinNoiseSampler(chunkRandom, IntStream.rangeClosed(-5, 0));
        OctavePerlinNoiseSampler offsetNoise = new OctavePerlinNoiseSampler(chunkRandom, IntStream.rangeClosed(-2, 0));
        OctavePerlinNoiseSampler scaleNoise = new OctavePerlinNoiseSampler(chunkRandom, IntStream.rangeClosed(-0, 0));

        for (int x = -512; x < 512; x++) {
            if (x % 128 == 0) {
                source.sendFeedback(new LiteralText(((x + 512) / 1024.0) * 100 + "%"), false);
            }

            for (int z = -512; z < 512; z++) {
                double[] buffer = new double[9];
                PerlerpCarver.sampleNoiseColumn(buffer, x, z, caveNoise, offsetNoise, scaleNoise);

                boolean isAir = false;
                int sections = 0;

                for (double density : buffer) {
                    if (density < 0) {
                        isAir = true;
                        sections++;
                    }
                }

                double lerp = (sections / 9.0);
                img.setRGB(x + 512, z + 512, isAir ? getIntFromColor((int)MathHelper.lerp(lerp, 242, 235), (int)MathHelper.lerp(lerp, 160, 35), (int)MathHelper.lerp(lerp, 233, 213)) : 0xadadad);
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

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
