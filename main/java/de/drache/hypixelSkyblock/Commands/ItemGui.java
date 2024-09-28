package de.drache.hypixelSkyblock.Commands;

import de.drache.hypixelSkyblock.Classes.InventoryCreator;
import de.drache.hypixelSkyblock.Classes.ItemsList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ItemGui implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player){
            if (commandSender.hasPermission("ci.gui")){
                Player player = (Player) commandSender;
                int i = Integer.parseInt(strings[0]);
                if (i == 3){
                    player.sendMessage(ItemsList.getInstance().ItemMana.toString());
                    if (ItemsList.getInstance().ItemMana.containsKey(player.getItemInHand())){
                        player.sendMessage("Mana: " + ItemsList.getInstance().ItemMana.get(player.getItemInHand()));
                    }
                    return true;
                }
                InventoryCreator.getInstance().initializeItems(i);
                InventoryCreator.getInstance().openInventory(player);
            }
        }
        return false;
    }

}
