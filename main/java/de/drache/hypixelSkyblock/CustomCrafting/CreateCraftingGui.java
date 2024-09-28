package de.drache.hypixelSkyblock.CustomCrafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreateCraftingGui {
    private static final int INVENTORY_ROWS = 5;
    private static final int INVENTORY_SIZE = INVENTORY_ROWS * 9;
    private static final String GUI_TITLE = "Crafting Table";
    private static final Material GLASS_PANE_TYPE = Material.GRAY_STAINED_GLASS_PANE;

    public static HashMap<Player, Inventory> inventoryHashMap = new HashMap<>();

    public static void initializeInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, GUI_TITLE);
        ItemStack glassPane = createGlassPane(GLASS_PANE_TYPE);

        // List of positions where glass panes will be placed
        List<Integer> glassPaneSlots = IntStream.of(
                10, 11, 12,
                19, 20, 21,
                28, 29, 30
        ).boxed().toList();

        // Fill the inventory with glass panes at specified positions
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!glassPaneSlots.contains(i)) {
                inventory.setItem(i, glassPane);
            }
        }
        inventory.setItem(24, createGlassPane(Material.RED_STAINED_GLASS_PANE));
        for (int i = 36; i < inventory.getSize(); i++) {
            inventory.setItem(i, createGlassPane(Material.RED_STAINED_GLASS_PANE));
        }
        inventoryHashMap.put(player, inventory);
    }

    private static ItemStack createGlassPane(Material material) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            stack.setItemMeta(meta);
        }
        return stack;
    }
}
