package de.drache.hypixelSkyblock.CustomCrafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

public class CraftingInventoryClickEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Inventory playerInventory = CreateCraftingGui.inventoryHashMap.get(player);
            if (playerInventory != null && event.getInventory().equals(playerInventory)) {
                List<Integer> glassPaneSlots = IntStream.of(
                        10, 11, 12,
                        19, 20, 21,
                        28, 29, 30
                ).boxed().toList();

                // Only cancel the event if the clicked slot is valid and not a glass pane
                if (!glassPaneSlots.contains(event.getSlot())) {
                    event.setCancelled(true);
                    ItemStack clickedItem = event.getCurrentItem();

                    // Optional: Handle the clicked item here if needed
                }
            }
        }
    }
}
