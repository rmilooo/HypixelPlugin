package de.drache.hypixelSkyblock.Classes;

import de.drache.hypixelSkyblock.ArmorSystem.ArmorStatListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemCheck {
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
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (AbilityUse.Ability.get(p) == null){
                AbilityUse.Ability.put(p, false);
                continue;
            }

            Double playerMana = PlayerStats.getInstance().PlayerMana.get(p);
            Double maxPlayerMana = PlayerStats.getInstance().MaxPlayerMana.get(p);
            if (playerMana == null){
                PlayerStats.getInstance().PlayerMana.put(p, 0.0);
                continue;
            }
            if (AbilityUse.Ability.get(p)){
                return;
            }
            // Ensure player mana and max mana aren't null
            //if (playerMana != null && maxPlayerMana != null) {
                String healthText = "§7" + Math.round(p.getHealth() * 10) + " / " + Math.round(p.getMaxHealth() * 10) + "§4♥";
                String manaText = "§9" + Math.round(playerMana) + " §7/ §9" + Math.round(maxPlayerMana) +"§9✎";
                p.sendActionBar(healthText + "                  " + manaText);

                ArmorStatListener.getInstance().updateArmorStats(p);
            //}
        }
    }
}
