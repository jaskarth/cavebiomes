package supercoder79.cavebiomes.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.Vec2ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec2f;
import supercoder79.cavebiomes.api.CaveDecorator;
import supercoder79.cavebiomes.world.decorator.CaveDecorators;
import supercoder79.cavebiomes.world.layer.LayerGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MapCaveBiomesCommand {
    // TODO: expose this to the api
    public static final Map<CaveDecorator, Integer> COLORS = new HashMap<>();
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapcavebiomes")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(context -> execute(context.getSource(), 0, 0))
                    .then(CommandManager.argument("pos", Vec2ArgumentType.vec2())
                    .executes(context -> {
                        Vec2f vec = Vec2ArgumentType.getVec2(context, "pos");
                        return execute(context.getSource(), (int) vec.x, (int) vec.y);
                    }));

            dispatcher.register(builder);

        });

        COLORS.put(CaveDecorators.NONE, 0xeeeeee);
        COLORS.put(CaveDecorators.WATER, 0x0033bb);
        COLORS.put(CaveDecorators.LAVA, 0xdd6600);
        COLORS.put(CaveDecorators.OVERGROWN, 0x00ff33);
        COLORS.put(CaveDecorators.OBSIDIAN, 0x333333);
        COLORS.put(CaveDecorators.MAGMA, 0xdd9900);
        COLORS.put(CaveDecorators.COBBLESTONE, 0x777777);
        COLORS.put(CaveDecorators.GRAVEL, 0x999999);
        COLORS.put(CaveDecorators.SANDSTONE, 0xf5f2a4);

        COLORS.put(CaveDecorators.ANDESITE, 0xd6cee0);
        COLORS.put(CaveDecorators.DIORITE, 0xe0e0da);
        COLORS.put(CaveDecorators.GRANITE, 0xe0a77e);

        COLORS.put(CaveDecorators.COBWEB, 0xc4c4c4);
        COLORS.put(CaveDecorators.FULL_OBSIDIAN, 0x000000);
        COLORS.put(CaveDecorators.MUSHROOM, 0x7d7b43);

        COLORS.put(CaveDecorators.COAL, 0x686964);
        COLORS.put(CaveDecorators.IRON, 0x9b9c7c);
        COLORS.put(CaveDecorators.GOLD, 0xdbc200);
        COLORS.put(CaveDecorators.REDSTONE, 0xe60058);
        COLORS.put(CaveDecorators.LAPIS, 0x2d02d9);
        COLORS.put(CaveDecorators.DIAMOND, 0x00ffff);

        COLORS.put(CaveDecorators.DRIPSTONE, 0x9c7a5d);
        COLORS.put(CaveDecorators.LUSH, 0x5de378);
    }

    private static int execute(ServerCommandSource source, int xOffset, int zOffset) {
        BufferedImage img = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);

        long seed = source.getWorld().getSeed();

        for (int x = -1024; x < 1024; x++) {
            if (x % 256 == 0) {
                source.sendFeedback(new LiteralText(((x + 1024) / 2048.0) * 100 + "%"), false);
            }

            for (int z = -1024; z < 1024; z++) {
                CaveDecorator decorator = LayerGenerator.getDecorator(seed, x + xOffset, z + zOffset);
                int color = COLORS.getOrDefault(decorator, 0xFFFFFF);

                img.setRGB(x + 1024, z + 1024, color);
            }
        }

        // save the biome map
        Path p = Paths.get("cavebiomemap.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        source.sendFeedback(new LiteralText("Mapped cave biomes!"), false);

        return 0;
    }
}
