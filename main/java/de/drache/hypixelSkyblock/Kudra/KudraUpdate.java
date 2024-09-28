package de.drache.hypixelSkyblock.Kudra;

import de.drache.hypixelSkyblock.main; // Adjust this import to match your actual main plugin class import
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KudraUpdate {
    private static BukkitTask task; // Store the task reference

    public static void startTask() {
        if (task == null || task.isCancelled()) { // Only start if not already running
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (Kudra.getInstance() != null) {
                        Kudra.getInstance().update();
                    } else {
                        main.instance.log("dwdada");
                    }
                }
            }.runTaskTimer(main.instance, 0L, 1L); // 0L is the delay, 1L is the period (1 tick)
        }
    }

    public static void cancelTask() {
        if (task != null && !task.isCancelled()) {
            task.cancel(); // Cancel the task
        }
    }
}
