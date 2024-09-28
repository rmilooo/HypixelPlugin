package de.drache.hypixelSkyblock.Classes;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Enderman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrystalSpawnList {
    public static CrystalSpawnList instance;
    public CrystalSpawnList() {
        instance = this;
    }

    public static CrystalSpawnList getInstance() {
        if (instance == null) {
            instance = new CrystalSpawnList();
        }
        return instance;
    }
    public List<Location> spawnLocations = new ArrayList<>();
    public HashMap<EnderCrystal, Location> aliveCrystals  = new HashMap<>();
}
