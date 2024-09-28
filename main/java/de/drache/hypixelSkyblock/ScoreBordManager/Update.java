package de.drache.hypixelSkyblock.ScoreBordManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Update {
    public static void startRepeatingTask() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HypixelSkyblock");

        // Ensure the plugin is not null
        if (plugin != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    executeTask();
                }
            }.runTaskTimer(plugin, 0L, 5L);
        } else {
            Bukkit.getLogger().severe("HypixelSkyblock plugin not found!");
        }
    }
    public static void executeTask() {
        ScoreboardManagment.updateScoreboards();
    }
}
