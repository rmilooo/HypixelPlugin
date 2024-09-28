package de.drache.hypixelSkyblock.Classes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsList {
    public static ItemsList instance;
    public ItemsList() {
        instance = this;
    }

    public static ItemsList getInstance() {
        if (instance == null) {
            instance = new ItemsList();
        }
        return instance;
    }
    public List<ItemStack> items = new ArrayList<>();
    public HashMap<ItemStack, Integer> ItemDamage = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemStrength = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemMana = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemHealth = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemDefense = new HashMap<>();
}
