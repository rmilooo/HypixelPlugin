package de.drache.hypixelSkyblock.Sell;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SellPrices {
    public static SellPrices instance;
    private static HashMap<ItemStack, Long> sellPrices = new HashMap<>();

    // Private constructor to prevent instantiation
    private SellPrices() {
        instance = this;
    }

    // Singleton instance getter
    public static SellPrices getInstance() {
        if (instance == null) {
            instance = new SellPrices();
        }
        return instance;
    }

    // Initialize the sell prices map if needed
    public static void initialize() {
        // This method can be used if you want to reset or clear the sellPrices map
        sellPrices.clear();
    }

    // Add a sell price for an item
    public static void addSellPrice(ItemStack itemStack, long price) {
        sellPrices.put(itemStack, price);
    }

    // Get the sell price of an item
    public static long getSellPrice(ItemStack itemStack) {
        return sellPrices.getOrDefault(itemStack, 0L);
    }

    // Check if an item has a sell price
    public static boolean hasSellPrice(ItemStack itemStack) {
        return sellPrices.containsKey(itemStack);
    }

    // Remove a sell price for an item
    public static void removeSellPrice(ItemStack itemStack) {
        sellPrices.remove(itemStack);
    }
}
