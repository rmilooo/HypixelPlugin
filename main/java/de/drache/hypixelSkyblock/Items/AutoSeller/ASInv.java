package de.drache.hypixelSkyblock.Items.AutoSeller;

import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ASInv {
    public static HashMap<Player, Inventory> playerinvmap = new HashMap<>();
    public static HashMap<Player, Boolean> playerinvopenmap = new HashMap<>();
    public static ASInv instance;

    public ASInv() {
        instance = this;
    }

    public static ASInv getInstance() {
        if (instance == null) {
            instance = new ASInv();
        }
        return instance;
    }

    public void createInv(Player player) {
        List<ItemStack> items;
        Inventory inv = Bukkit.createInventory(null, 9 * 4, "§4§lAuto Seller");
        List<Integer> stacklist = new ArrayList<>();

        // Define the slots that will not be filled with gray panes
        stacklist.add(3 + 9);
        stacklist.add(4 + 9);
        stacklist.add(5 + 9);
        stacklist.add(3 + 9 + 9);
        stacklist.add(4 + 9 + 9);
        stacklist.add(5 + 9 + 9);

        // Fetch player's stored items from ASManagement
        if (ASManagement.has(player)) {
            items = ASManagement.get(player);
        } else {
            items = new ArrayList<>();
        }

        // Ensure the player's item list is not too large to prevent overflow
        if (items.size() < 7) {
            // Fill the inventory with gray panes except for the special slots
            for (int i = 0; i < inv.getSize(); i++) {
                if (!stacklist.contains(i)) {
                    ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
                    ItemMeta meta = stack.getItemMeta();
                    meta.setDisplayName(" ");
                    stack.setItemMeta(meta);
                    inv.setItem(i, stack);
                }
            }

            // Add the player's items to the inventory
            for (ItemStack stack : items) {
                inv.addItem(stack);
            }

            // Fill empty slots with green stained glass panes indicating free slots
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                    ItemStack stack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
                    ItemMeta meta = stack.getItemMeta();
                    meta.setDisplayName("§a§lFree Slot");
                    stack.setItemMeta(meta);
                    inv.setItem(i, stack);
                }
            }

            // Store the inventory in the player inventory map
            playerinvmap.put(player, inv);
        }
    }

    public void openInv(Player player) {
        if (playerinvmap.get(player) != null) {
            playerinvopenmap.put(player, true);
            player.openInventory(playerinvmap.get(player));
        }
    }

    public void updateInv(Player player) {
        player.closeInventory();
        createInv(player);
        openInv(player);
    }
}
