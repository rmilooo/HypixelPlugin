package de.drache.hypixelSkyblock.Commands;

import de.drache.hypixelSkyblock.Classes.CrystalSpawnList;
import de.drache.hypixelSkyblock.Classes.ZealotSpawnList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class addCrystalSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("ci.crystalspawn")) {
            if (commandSender instanceof Player player){
                CrystalSpawnList.getInstance().spawnLocations.add(player.getLocation());
            }
        }
        return false;
    }
}
