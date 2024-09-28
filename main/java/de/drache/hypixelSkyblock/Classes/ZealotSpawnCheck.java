package de.drache.hypixelSkyblock.Classes;

import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ZealotSpawnCheck {
    static int randomDelay = ThreadLocalRandom.current().nextInt(20 * 5, 20 * 10);

    // Method to start the repeating task
    public static void startRepeatingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                executeTask();
            }
        }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HypixelSkyblock")), 0L, randomDelay);
    }

    public static void startRepeatingTask2() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = ZealotSpawnList.getInstance().spawnLocations.get(1);
                    for (Entity all : loc.getWorld().getEntities()) {
                        if (all instanceof Enderman) {
                            all.remove();
                    }
                }
                ZealotSpawnList.getInstance().aliveZealots.clear();
            }
        }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HypixelSkyblock")), 0L, 20 * 120L);
    }

    private static void executeTask() {
        if (ZealotSpawnList.getInstance().spawnLocations != null) {
            for (Location location : ZealotSpawnList.getInstance().spawnLocations) {
                if (location != null && location.getWorld() != null) {
                    // Check if the location already has a Zealot (Enderman)
                    if (!ZealotSpawnList.getInstance().aliveZealots.containsValue(location)) {

                        // Double-check if no Zealot is present at this location
                        if (!ZealotSpawnList.getInstance().aliveZealots.containsValue(location)) {
                            Enderman enderman = location.getWorld().spawn(location, Enderman.class);
                            enderman.setCanPickupItems(false);

                            // First set max health, then health
                            enderman.setMaxHealth(1000);
                            enderman.setHealth(1000);

                            enderman.setCustomNameVisible(true);
                            enderman.setCustomName("§8[§7Lvl55§8] §cZealot");

                            // Add the spawned Zealot to the aliveZealots map
                            ZealotSpawnList.getInstance().aliveZealots.put(enderman, location);
                        }
                    }
                }
            }
        }
    }
}


