package de.drache.hypixelSkyblock.Classes;

import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;


public class AbilityUse {
        private static final Map<Player, BukkitTask> actionBarTasks = new HashMap<>();
        public static HashMap<Player, Boolean> Ability = new HashMap<>();
        public static Boolean useAbility(String abilityName, Integer ManaUsage, Player player) {
            if (PlayerStats.getInstance().PlayerMana.get(player) >= ManaUsage) {
                Ability.put(player, true);
                PlayerStats.getInstance().PlayerMana.put(player, PlayerStats.getInstance().PlayerMana.get(player) - ManaUsage);
                player.sendActionBar("§b- " + ManaUsage + " §bMana (§6" + abilityName + "§b)");

                // Falls es eine vorherige Aufgabe gibt, diese abbrechen
                if (actionBarTasks.containsKey(player)) {
                    actionBarTasks.get(player).cancel();
                }

                // Neue Aufgabe zum Leeren der Actionbar nach 1 Sekunde planen
                BukkitTask task = Bukkit.getScheduler().runTaskLater(main.instance, () -> {
                    Ability.put(player, false); // Aktuelle Aufgabe abbrechen
                    player.sendActionBar(""); // Actionbar löschen
                    actionBarTasks.remove(player); // Task nach Ablauf entfernen
                }, 20L); // 20 Ticks = 1 Sekunde

                // Task in die Map einfügen
                actionBarTasks.put(player, task);

                return true;
            }
            return false;
        }
    }

