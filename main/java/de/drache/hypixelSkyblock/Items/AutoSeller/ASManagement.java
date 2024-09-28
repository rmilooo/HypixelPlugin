package de.drache.hypixelSkyblock.Items.AutoSeller;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ASManagement {
    private static ASManagement instance; // Ensures singleton behavior

    // Constructor ensures only one instance of ASManagement
    private ASManagement() {
        // Private to prevent instantiation
    }

    // Get or create the singleton instance
    public static ASManagement getInstance() {
        if (instance == null) {
            instance = new ASManagement();
        }
        return instance;
    }

    // HashMap to store AutoSeller inventories per player
    private static final HashMap<Player, List<ItemStack>> playerAutoSellerInv = new HashMap<>();

    // Get a player's AutoSeller inventory
    public static List<ItemStack> get(Player player) {
        return playerAutoSellerInv.get(player);
    }

    // Set a player's AutoSeller inventory
    public static void set(Player player, List<ItemStack> items) {
        playerAutoSellerInv.put(player, items);
    }

    // Check if the player has an AutoSeller inventory
    public static boolean has(Player player) {
        return playerAutoSellerInv.containsKey(player);
    }

    // Remove a specific ItemStack from the player's AutoSeller inventory
    public static void remove(Player player, ItemStack stack) {
        List<ItemStack> items = playerAutoSellerInv.get(player);
        if (items != null) {
            // Use an iterator to safely remove items while looping
            items.removeIf(item -> item != null && item.isSimilar(stack));
        }
    }


    // Clear all AutoSeller inventories
    public static void clear() {
        playerAutoSellerInv.clear();
    }

    // Check if a player's AutoSeller inventory is empty
    public static boolean isAutoSellerInvEmpty(Player player) {
        List<ItemStack> items = get(player);
        return items == null || items.isEmpty();
    }
}
