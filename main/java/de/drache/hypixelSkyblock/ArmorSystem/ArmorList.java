package de.drache.hypixelSkyblock.ArmorSystem;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorList {
    public static ArmorList instance;
    public ArmorList() {
        instance = this;
    }

    public static ArmorList getInstance() {
        if (instance == null) {
            instance = new ArmorList();
        }
        return instance;
    }
    public List<ItemStack> items = new ArrayList<>();
    public HashMap<ItemStack, Integer> ItemStrength = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemMana = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemHealth = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemDefense = new HashMap<>();
    public HashMap<ItemStack, Integer> ItemMagicFind = new HashMap<>();

}
