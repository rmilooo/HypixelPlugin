package de.drache.hypixelSkyblock.Classes;

import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FileManagement {
    private File dataFile;
    private YamlConfiguration dataConfig;
    public static FileManagement instance;

    public FileManagement() {
        instance = this;
    }

    public static FileManagement getInstance() {
        if (instance == null) {
            instance = new FileManagement();
        }
        return instance;
    }

    public void initInventory(){
        File dataFolder = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HypixelSkyblock")).getDataFolder();
        if (dataFolder.mkdirs()) {
            main.instance.log("Directory created successfully");
        } else {
            main.instance.log("Failed to create directory");
        }

        dataFile = new File(dataFolder, "zealot-spawns.yml");
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
        loadFile();
    }


    public void saveFile() {
        saveList(ZealotSpawnList.getInstance().spawnLocations);
        try {
            dataConfig.save(dataFile);
            main.instance.log("Zealot spawns saved successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveList(List<Location> zealotSpawns) {
        dataConfig.set("Zealot_Spawns", zealotSpawns);
    }

    public void loadFile(){
        if (dataConfig.isSet("Zealot_Spawns")) {
            main.instance.log("Loading saved Zealot spawns...");
            List<Location> spawnLoc = (List<Location>) dataConfig.getList("Zealot_Spawns", new ArrayList<>());
            main.instance.log(spawnLoc.size() + " spawns loaded successfully");
            ZealotSpawnList.getInstance().spawnLocations = (List<Location>) dataConfig.getList("Zealot_Spawns", new ArrayList<>());
        }else {
            main.instance.log("No saved Zealot spawns found");
        }
    }
}