package de.drache.hypixelSkyblock.Commands;

import de.drache.hypixelSkyblock.Kudra.Kudra;
import de.drache.hypixelSkyblock.Kudra.KudraManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class spawnKudra implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player){
            if (commandSender.hasPermission("kudra.spawn")){
                Player player = (Player) commandSender;
                Kudra kudra = new Kudra();
                KudraManager.spawnKudra(kudra, player);
            }
        }
        return false;
    }
}
