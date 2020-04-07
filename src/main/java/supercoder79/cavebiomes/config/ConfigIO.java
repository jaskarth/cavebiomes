package supercoder79.cavebiomes.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import supercoder79.cavebiomes.CaveBiomes;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigIO {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static ConfigData init() {
        ConfigData configData = null;
        try {
            Path configDir;
            configDir = Paths.get("", "config", "cavebiomes.json");
            if (Files.exists(configDir)) {
                configData = gson.fromJson(new FileReader(configDir.toFile()), ConfigData.class);
                //save new values
                if (!configData.configVersion.equals(CaveBiomes.VERSION)) {
                    configData.configVersion = CaveBiomes.VERSION;
                    BufferedWriter writer = new BufferedWriter(new FileWriter(configDir.toFile()));
                    writer.write(gson.toJson(configData));
                    writer.close();
                }
            } else {
                configData = new ConfigData();
                Paths.get("", "config").toFile().mkdirs();
                BufferedWriter writer = new BufferedWriter(new FileWriter(configDir.toFile()));
                writer.write(gson.toJson(configData));

                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configData;
    }
}
