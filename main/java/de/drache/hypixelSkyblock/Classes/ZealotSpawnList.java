package de.drache.hypixelSkyblock.Classes;

import org.bukkit.Location;
import org.bukkit.entity.Enderman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZealotSpawnList {
    public static ZealotSpawnList instance;
    public ZealotSpawnList() {
        instance = this;
    }

    public static ZealotSpawnList getInstance() {
        if (instance == null) {
            instance = new ZealotSpawnList();
        }
        return instance;
    }
    public List<Location> spawnLocations = new ArrayList<>();
    public HashMap<Enderman, Location> aliveZealots = new HashMap<>();
}
