package de.drache.hypixelSkyblock.Sell;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BuyPrices {
    public static BuyPrices instance;
    private static HashMap<ItemStack, Long> buyPrices = new HashMap<>();

    // Private constructor to prevent instantiation
    public BuyPrices() {
        instance = this;
    }

    // Singleton instance getter
    public static BuyPrices getInstance() {
        if (instance == null) {
            instance = new BuyPrices();
        }
        return instance;
    }

    // Initialize the sell prices map if needed
    public static void initialize() {
        // This method can be used if you want to reset or clear the sellPrices map
        buyPrices.clear();
    }

    // Add a sell price for an item
    public static void addBuyPrice(ItemStack itemStack, long price) {
        buyPrices.put(itemStack, price);
    }

    // Get the sell price of an item
    public static long getBuyPrice(ItemStack itemStack) {
        return buyPrices.getOrDefault(itemStack, 0L);
    }

    // Check if an item has a sell price
    public static boolean hasBuyPrice(ItemStack itemStack) {
        return buyPrices.containsKey(itemStack);
    }

    // Remove a sell price for an item
    public static void removeBuyPrice(ItemStack itemStack) {
        buyPrices.remove(itemStack);
    }
}
