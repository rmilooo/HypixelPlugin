package de.drache.hypixelSkyblock.DamageSystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class DamageHoloGram implements Listener {
    private static final Random random = new Random();

    public static void showDamageHoloGram(Double damage, Location location) {
        // Get the location of the entity and add some randomness to it
        Vector offset = new Vector(
                (random.nextDouble() - 0.5) * 0.5, // Random X offset between -0.25 and 0.25
                (random.nextDouble() * 0.2) + 0.5,  // Random Y offset between 0.5 and 0.7, so the armor stand is near the eye level
                (random.nextDouble() - 0.5) * 0.5  // Random Z offset between -0.25 and 0.25
        );

        Location spawnLocation = location.add(offset);

        // Spawn the ArmorStand with pre-set properties
        ArmorStand armorStand = location.getWorld().spawn(spawnLocation, ArmorStand.class, stand -> {
            stand.setInvisible(true);
            stand.setGravity(false);
            stand.setMarker(true);
            stand.setCustomName("§c§l" + Math.round(damage));
            stand.setCustomNameVisible(true);
        });

        // Schedule removal of the ArmorStand after 3 seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                if (armorStand.isValid()) {
                    armorStand.remove();
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("HypixelSkyblock"), 60L); // 60 ticks = 3 seconds
    }
}
