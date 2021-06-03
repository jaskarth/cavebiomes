package supercoder79.cavebiomes.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import supercoder79.cavebiomes.world.layer.WaterGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapWaterCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapwater")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);

        long seed = source.getWorld().getSeed();

        for (int x = -1024; x < 1024; x++) {
            if (x % 256 == 0) {
                source.sendFeedback(new LiteralText(((x + 1024) / 2048.0) * 100 + "%"), false);
            }

            for (int z = -1024; z < 1024; z++) {
                int sample = WaterGenerator.getSample(seed, x, z);

                int borderHeight = (sample & WaterGenerator.BORDER_BITS) >> 8;
                int waterHeight = (sample & 255);

                img.setRGB(x + 1024, z + 1024, sample > 0 ? getIntFromColor(borderHeight > 0 ? 128 + borderHeight * 2 : 0, 0, waterHeight * 8) : 0xadadad);
            }
        }

        // save the image
        Path p = Paths.get("watermap.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        source.sendFeedback(new LiteralText("Mapped water!"), false);

        return 0;
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
