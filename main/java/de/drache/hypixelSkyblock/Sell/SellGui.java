package de.drache.hypixelSkyblock.Sell;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.HashMap;

public class SellGui implements Listener {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static HashMap<Player, Inventory> playerInventoryHashMap = new HashMap<>();

    public static void createInventory(Player player) {
        if (player != null) {
            Inventory inv = Bukkit.createInventory(null, 9 * 5, "§cSell Items");
            playerInventoryHashMap.put(player, inv);
        }
    }

    public static Inventory getInventory(Player player) {
        return player != null ? playerInventoryHashMap.get(player) : null;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = playerInventoryHashMap.get(player);

        // Check if the closed inventory matches the tracked inventory
        if (inventory != null && event.getInventory().equals(inventory)) {
            for (ItemStack itemStack : inventory.getContents()) {
                // Check if itemStack is valid and has a positive amount
                if (itemStack != null && itemStack.getAmount() > 0) {
                    ItemStack stack2 = itemStack.clone();
                    stack2.setAmount(1);

                    if (SellPrices.hasSellPrice(stack2)) {
                        long sellPrice = SellPrices.getSellPrice(stack2);
                        int amount = itemStack.getAmount();
                        long totalSell = sellPrice * amount;

                        player.sendMessage("§7You sold " + amount + "x " + itemStack.getType().name() + " for §6§l" + formatCoins(totalSell) + " coins§7!");

                        PlayerStats stats = PlayerStats.getInstance();
                        // Safely update coins to avoid potential overflow
                        stats.Coins.put(player, stats.Coins.getOrDefault(player, 0L) + totalSell);
                    } else {
                        player.sendMessage("§cThis item cannot be sold!");
                        player.getInventory().addItem(itemStack);
                    }
                }
            }

            inventory.clear(); // Clear the inventory after processing
            playerInventoryHashMap.remove(player); // Remove the player from the hashmap
        }
    }
    private String formatCoins(long amount) {
        if (amount < 1000) {
            return String.valueOf(amount);
        } else if (amount < 1_000_000) {
            return decimalFormat.format(amount / 1_000.0) + "k"; // Format thousands
        } else if (amount < 1_000_000_000) {
            return decimalFormat.format(amount / 1_000_000.0) + "m"; // Format millions
        } else {
            return decimalFormat.format(amount / 1_000_000_000.0) + "b"; // Format billions
        }
    }
}
