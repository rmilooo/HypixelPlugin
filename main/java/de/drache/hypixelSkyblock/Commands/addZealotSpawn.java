package de.drache.hypixelSkyblock.Commands;

import de.drache.hypixelSkyblock.Classes.ZealotSpawnList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class addZealotSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("ci.zealotspawn")) {
            if (commandSender instanceof Player player){
                ZealotSpawnList.getInstance().spawnLocations.add(player.getLocation());
            }
        }
        return false;
    }
}
