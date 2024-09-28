package de.drache.hypixelSkyblock.Sell;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PlayerStatsFile {
    private File dataFile;
    private YamlConfiguration dataConfig;
    public static PlayerStatsFile instance;

    public PlayerStatsFile() {
        instance = this;
    }

    public static PlayerStatsFile getInstance() {
        if (instance == null) {
            instance = new PlayerStatsFile();
        }
        return instance;
    }

    public void initFile() {
        File dataFolder = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HypixelSkyblock")).getDataFolder();
        if (dataFolder.mkdirs()) {
            main.instance.log("Directory created successfully");
        } else {
            main.instance.log("Failed to create directory");
        }

        dataFile = new File(dataFolder, "player-stats.yml");
        if (!dataFile.exists()) {
            try {
                if (dataFile.createNewFile()) {
                    main.instance.log("File created successfully");
                } else {
                    main.instance.log("File already exists");
                }
            } catch (IOException e) {
                main.instance.log("Error creating file: " + e.getMessage());
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }


    public void saveFile() {
        try {
            dataConfig.save(dataFile);
            main.instance.log("Player Stats saved successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void set(String name, Object value) {
        if (name == null || value == null) {
            return;
        }
        dataConfig.set(name, value);
    }
    public Object get(String name) {
        return dataConfig.get(name);
    }
}
