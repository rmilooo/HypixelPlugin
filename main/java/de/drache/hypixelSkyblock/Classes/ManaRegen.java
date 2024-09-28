package de.drache.hypixelSkyblock.Classes;

import de.drache.hypixelSkyblock.ArmorSystem.ArmorStatListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegen {
    public static void startRepeatingTask() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HypixelSkyblock");

        // Ensure the plugin is not null
        if (plugin != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    doSomething();
                }
            }.runTaskTimer(plugin, 0L, 5L);
        } else {
            Bukkit.getLogger().severe("HypixelSkyblock plugin not found!");
        }
    }

    public static void doSomething() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Double maxMana = PlayerStats.getInstance().MaxPlayerMana.get(p);
            Double mana = PlayerStats.getInstance().PlayerMana.get(p);
            if (mana == null){
                ArmorStatListener.getInstance().updateArmorStats(p);
                return;
            }

            if (mana != maxMana){
                Double regen = maxMana / 100 * 2;
                PlayerStats.getInstance().PlayerMana.put(p, Math.min(maxMana, mana + regen));
            }
        }
    }
}