package supercoder79.cavebiomes.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.ChunkStatus;
import supercoder79.cavebiomes.world.noise.OpenSimplexNoise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class MapOreNodulesCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("maporenodules")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);

        ServerWorld world = source.getWorld();

        OpenSimplexNoise noise = new OpenSimplexNoise(world.getSeed() + 443);
        OpenSimplexNoise oreNoise = new OpenSimplexNoise(world.getSeed() - 321);

        for (int x = -512; x < 512; x++) {
            if (x % 128 == 0) {
                source.sendFeedback(new LiteralText(((x + 512) / 1024.0) * 100 + "%"), false);
            }

            for (int z = -512; z < 512; z++) {

                boolean generating = false;
                double oreSelector = -1;

                // TODO: I accidentally wrote a chunk pregenerator while making this
//                int maxY = world.getChunk(x >> 4, z >> 4).sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, x, z);
                int maxY = 64;

                for (int y = 0; y < maxY; y++) {
                    double noiseAt = noise.sample(x / 24.0, y / 24.0, z / 24.0);

                    noiseAt += Math.max((8.5 / y) - 1, 0); // lower bound
                    noiseAt += Math.max((5.0 / (maxY - y)) - 1, 0); // upper bound

                    if (noiseAt < -0.7665) {
                        oreSelector = oreNoise.sample(x / 140.0, z / 140.0);
                        generating = true;

                        break;
                    }
                }

                if (generating) {
                    int color;

                    if (oreSelector > 0) {
                        color = getIntFromColor((int)MathHelper.lerp(oreSelector, 0, 0), (int)MathHelper.lerp(oreSelector, 0, 255), (int)MathHelper.lerp(oreSelector, 255, 255));
                    } else {
                        color = getIntFromColor((int)MathHelper.lerp(-oreSelector, 0, 0), (int)MathHelper.lerp(-oreSelector, 0, 255), (int)MathHelper.lerp(-oreSelector, 255, 0));
                    }

                    img.setRGB(x + 512, z + 512, color);
                } else {
                    img.setRGB(x + 512, z + 512, 0x919191);
                }
            }
        }

        // save the image
        Path p = Paths.get("orenodulemap.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        source.sendFeedback(new LiteralText("Mapped ore nodules!"), false);

        return 0;
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
