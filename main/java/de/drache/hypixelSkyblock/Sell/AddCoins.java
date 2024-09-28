package de.drache.hypixelSkyblock.Sell;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.entity.Player;

public class AddCoins {
    public static void addCoins(Player player, long amount) {
        PlayerStats.getInstance().Coins.put(player, PlayerStats.getInstance().Coins.getOrDefault(player, 0L) + amount);
    }
}
