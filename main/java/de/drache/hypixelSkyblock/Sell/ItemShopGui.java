package de.drache.hypixelSkyblock.Sell;

import de.drache.hypixelSkyblock.ArmorSystem.Armors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemShopGui implements Listener {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static Inventory inv;
    public static HashMap<ItemStack, ItemStack> itemmap = new HashMap<>();

    public static void createShopGui() {
        inv = Bukkit.createInventory(null, 9 * 5, "§5§lShopGui");

        addShopItem(Armors.Necrons_Boots, BuyPrices.getBuyPrice(Armors.Necrons_Boots), 1+9);
        addShopItem(Armors.Necrons_Leggings, BuyPrices.getBuyPrice(Armors.Necrons_Leggings), 2+9);
        addShopItem(Armors.Necrons_Chestplate, BuyPrices.getBuyPrice(Armors.Necrons_Chestplate), 3+9);

        addShopItem(Armors.Ender_boots, BuyPrices.getBuyPrice(Armors.Ender_boots), 4+9);
        addShopItem(Armors.Ender_leggings, BuyPrices.getBuyPrice(Armors.Ender_leggings), 5+9);
        addShopItem(Armors.Ender_chest, BuyPrices.getBuyPrice(Armors.Ender_chest), 6+9);

        // Fill remaining slots with gray stained glass pane
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) {
                ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
                ItemMeta meta = pane.getItemMeta();
                meta.setDisplayName(" ");
                pane.setItemMeta(meta);
                inv.setItem(i, pane);
            }
        }
    }

    public static void openInv(Player player) {
        if (player != null && inv != null) {
            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inv)) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                Player player = (Player) event.getWhoClicked();
                List<String> lore = clickedItem.getItemMeta().getLore();
                if (lore != null && !lore.isEmpty()) {
                    if (itemmap.containsKey(clickedItem)) {
                        ItemStack originalItem = itemmap.get(clickedItem);
                        // Assuming BuyPrices and BuySystem are implemented correctly
                        if (BuyPrices.hasBuyPrice(originalItem)) {
                            BuySystem.buy(player, BuyPrices.getBuyPrice(originalItem), originalItem);
                        }
                    }
                }
            }
        }
    }
    public static void addShopItem(ItemStack item, Long Price, Integer slot) {
        ItemStack shopItem = item.clone();
        List<String> lore = shopItem.getItemMeta().getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add("§7Price: §6§l" + formatCoins(Price));
        ItemMeta meta = shopItem.getItemMeta();
        meta.setLore(lore);
        shopItem.setItemMeta(meta);
        inv.setItem(slot, shopItem);
        itemmap.put(shopItem, item);
    }
    private static String formatCoins(long amount) {
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