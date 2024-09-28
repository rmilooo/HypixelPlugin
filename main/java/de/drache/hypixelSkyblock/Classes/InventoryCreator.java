package de.drache.hypixelSkyblock.Classes;

import de.drache.hypixelSkyblock.ArmorSystem.ArmorList;
import de.drache.hypixelSkyblock.Items.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryCreator implements Listener {
    private final Inventory inv;
    public static InventoryCreator instance;

    public static InventoryCreator getInstance() {
        if (instance == null) {
            instance = new InventoryCreator();
        }
        return instance;
    }

    public InventoryCreator() {
        instance = this;
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9*5, "§4Items");

        // Put the items into the inventory
        initializeItems(1);
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(int i) {
        if (i == 1) {
            inv.clear();
            for (ItemStack stack : ItemsList.getInstance().items) {
                inv.addItem(stack);
            }
        } else if (i == 2) {
            inv.clear();
            for (ItemStack stack : ArmorList.getInstance().items) {
                inv.addItem(stack);
            }
        }
    }
    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }


    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }
}


