package de.drache.hypixelSkyblock.ArmorSystem;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;


import java.util.ArrayList;
import java.util.List;

public class ArmorCreator {

    public static ArmorCreator instance;

    public ArmorCreator() {
        instance = this;
    }

    public static ArmorCreator getInstance() {
        if (instance == null) {
            instance = new ArmorCreator();
        }
        return instance;
    }

    public static ItemStack createItem(final Material material, final String name, final Rarities rarity, final Types type,final Integer magicfind, final Integer strength, final Integer mana, final Integer defense, final Integer health, final List<String> lore, final Color... colors) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta(); // Initialize meta outside the condition.

        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            if (colors.length > 0) {
                leatherArmorMeta.setColor(colors[0]); // Use the first color if provided.
            }
            meta = leatherArmorMeta; // Set the modified meta.
        }

        // Creating and adding lore.
        List<String> finalLore = new ArrayList<>();
        finalLore.add("§7Strength: §c+" + strength);
        if (magicfind != 0){
            finalLore.add("§7Magic Find: §a+" + magicfind);
        }
        finalLore.add("§7Health: §a+" + health);
        finalLore.add("§7Defense: §a+" + defense);
        if (mana != 0){
            finalLore.add("§7Intelligence: §a+" + mana);
        }
        finalLore.add(" §8[❁] §8[⚔]");
        finalLore.add("");

        if (lore != null) {
            finalLore.addAll(lore); // Add additional lore if available.
        }

        finalLore.add("");
        finalLore.add("§" + rarity.getColorCode() + "§l" + rarity.getName().toUpperCase() + " " + type.getName().toUpperCase());

        // Set display name and lore.
        meta.setDisplayName("§" + rarity.getColorCode() + name + " " + type.getName());
        meta.setLore(finalLore);
        meta.setUnbreakable(true);

        item.setItemMeta(meta); // Set final meta.

        // Register item stats.
        ArmorList.getInstance().items.add(item);
        ArmorList.getInstance().ItemStrength.put(item, strength);
        ArmorList.getInstance().ItemHealth.put(item, health);
        ArmorList.getInstance().ItemDefense.put(item, defense);
        ArmorList.getInstance().ItemMana.put(item, mana);
        ArmorList.getInstance().ItemMagicFind.put(item, magicfind);

        return item;
    }
}
