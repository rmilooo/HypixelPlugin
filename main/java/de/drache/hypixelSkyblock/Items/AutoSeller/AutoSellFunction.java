package de.drache.hypixelSkyblock.Items.AutoSeller;

import de.drache.hypixelSkyblock.Sell.AddCoins;
import de.drache.hypixelSkyblock.Sell.SellPrices;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class AutoSellFunction implements Listener {

    @EventHandler
    public void onAutoSell(PlayerInventorySlotChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = event.getNewItemStack();

        if (ASManagement.get(player) != null) {
            if (ASManagement.get(player).contains(newItem)) {
                ItemStack newItem0 = newItem.clone();
                newItem0.setAmount(1); // Ensure we only sell one item per click
                if (SellPrices.hasSellPrice(newItem0)) {
                    long sellPrice = SellPrices.getSellPrice(newItem);
                    int amount = newItem.getAmount(); // Number of items in the stack

                    // Calculate the total sell value for the stack of items
                    long totalSellValue = sellPrice * amount;
                    AddCoins.addCoins(player, totalSellValue);
                    // Notify the player of the auto-sell
                    player.sendMessage("Auto-sold " + amount + " " + newItem.getItemMeta().getDisplayName() + "§r for §6§l" + totalSellValue + " coins§r!");

                    // Remove the items from the inventory
                    player.getInventory().remove(newItem); // Remove the entire stack

                    // Add coins or other logic for auto-selling (e.g., player balance update)
                    // Example: player.getBalance().addCoins(totalSellValue);
                }
            }
        }
    }
}