package de.drache.hypixelSkyblock.Items;

import de.drache.hypixelSkyblock.ArmorSystem.ArmorList;
import de.drache.hypixelSkyblock.ArmorSystem.Types;
import de.drache.hypixelSkyblock.Classes.ItemsList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator{

    public static ItemCreator instance;
    public ItemCreator() {
        instance = this;
    }

    public static ItemCreator getInstance() {
        if (instance == null) {
            instance = new ItemCreator();
        }
        return instance;
    }
    public static ItemStack createItem(final Material material, final String name, final Rarities rarity, final ItemTypes type, final Integer damage, final Integer strength, final Integer mana, final Integer defense, final Integer health, final List<String> lore,Boolean showtypeinname) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta(); // Initialize meta outside the condition.

        // Creating and adding lore.
        List<String> finalLore = new ArrayList<>();
        if (damage != 0) {
            finalLore.add("§7Damage: §c+" + damage);
        }
        if (strength != 0) {
            finalLore.add("§7Strength: §c+" + strength);
        }
        if (health != 0) {
            finalLore.add("§7Health: §a+" + health);
        }
        if (defense != 0){
            finalLore.add("§7Defense: §a+" + defense);
        }
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
        if (showtypeinname) {
            meta.setDisplayName("§" + rarity.getColorCode() + name + " " + type.getName());
        }else {
            meta.setDisplayName("§" + rarity.getColorCode() + name);
        }
        meta.setLore(finalLore);
        meta.setUnbreakable(true);

        item.setItemMeta(meta); // Set final meta.

        // Register item stats.
        ItemsList.getInstance().items.add(item);
        ItemsList.getInstance().ItemDamage.put(item, damage);
        ItemsList.getInstance().ItemStrength.put(item, strength);
        ItemsList.getInstance().ItemHealth.put(item, health);
        ItemsList.getInstance().ItemDefense.put(item, defense);
        ItemsList.getInstance().ItemMana.put(item, mana);

        return item;
    }

}
