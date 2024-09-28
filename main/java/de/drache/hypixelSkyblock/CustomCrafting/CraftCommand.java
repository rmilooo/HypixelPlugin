package de.drache.hypixelSkyblock.CustomCrafting;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the command sender is a player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Open the crafting inventory for the player
            CreateCraftingGui.initializeInventory(player);
            player.openInventory(CreateCraftingGui.inventoryHashMap.get(player));
            return true;
        } else {
            // Notify console that the command can only be executed by a player
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return false;
        }
    }
}
