package de.drache.hypixelSkyblock.Sell;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerSaveLoadPurse implements Listener {
    @EventHandler
    public void onPlayerSave(PlayerQuitEvent event) {
        if (PlayerStats.getInstance().Coins.containsKey(event.getPlayer())){
            Player player = event.getPlayer();
            long coins = PlayerStats.getInstance().Coins.get(player);
            PlayerStatsFile.getInstance().set(player.getName(), coins);
        }
    }
    @EventHandler
    public void onPlayerLoad(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (PlayerStatsFile.getInstance().get(player.getName()) != null){
            PlayerStats.getInstance().Coins.put(player, (Long) PlayerStatsFile.getInstance().get(player.getName()));
        }
    }
    public static void load(Player player){
        if (PlayerStatsFile.getInstance().get(player.getName()) != null){
            PlayerStats.getInstance().Coins.put(player, (Long) PlayerStatsFile.getInstance().get(player.getName()));
        }
    }
    public static void save(Player player){
        if (PlayerStats.getInstance().Coins.containsKey(player)){
            long coins = PlayerStats.getInstance().Coins.get(player);
            PlayerStatsFile.getInstance().set(player.getName(), coins);
        }
    }
}
