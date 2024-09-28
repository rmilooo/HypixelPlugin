package de.drache.hypixelSkyblock.ArmorSystem;

import de.drache.hypixelSkyblock.Sell.BuyPrices;
import de.drache.hypixelSkyblock.Sell.SellPrices;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Armors implements Listener {
    static List<String> lore = Arrays.asList(
            "§l§7Reduces the damage you take from ",
            "§l§7mobs by §l§c10%"
    );
    public static ItemStack Necrons_Boots = ArmorCreator.createItem(Material.LEATHER_BOOTS, "Necron´s", Rarities.Legendary, Types.Boots, 0,200, 20, 120, 240, lore, Color.fromRGB(231, 110, 60));

    public static ItemStack Necrons_Leggings = ArmorCreator.createItem(Material.LEATHER_LEGGINGS, "Necron´s", Rarities.Legendary, Types.Leggings, 0,200, 20, 120, 240, lore, Color.fromRGB(231, 92, 60));

    public static ItemStack Necrons_Chestplate = ArmorCreator.createItem(Material.LEATHER_CHESTPLATE, "Necron´s", Rarities.Legendary, Types.Chestplate, 0,200, 20, 120, 240, lore, Color.fromRGB(231, 65, 60));


    public static ItemStack Storms_Boots = ArmorCreator.createItem(Material.LEATHER_BOOTS, "Storm´s", Rarities.Legendary, Types.Boots, 0,50, 950, 120, 200, lore, Color.fromRGB(28, 212, 228));

    public static ItemStack Storm_Leggings = ArmorCreator.createItem(Material.LEATHER_LEGGINGS, "Storm´s", Rarities.Legendary, Types.Leggings, 0,50, 950, 120, 200, lore, Color.fromRGB(23, 168, 196));

    public static ItemStack Storm_Chestplate = ArmorCreator.createItem(Material.LEATHER_CHESTPLATE, "Storm´s", Rarities.Legendary, Types.Chestplate,0, 50, 950, 120, 200, lore, Color.fromRGB(23, 147, 196));

    static List<String> lore2 = Arrays.asList(
            "§l§7Reduces the damage you take from ",
            "§l§7mobs by §l§c100%",
            "§c§lOnly Given to Admins"
    );
    public static ItemStack AdminInfernal_Boots = ArmorCreator.createItem(Material.LEATHER_BOOTS, "Admin´s", Rarities.Divine, Types.Boots, 999,999, 999, 999, 999, lore2, Color.fromRGB(230, 83, 0));

    public static ItemStack AdminInfernal_Leggings = ArmorCreator.createItem(Material.LEATHER_LEGGINGS, "Admin´s", Rarities.Divine, Types.Leggings, 999,999, 999, 999, 999, lore2, Color.fromRGB(230, 97, 5));

    public static ItemStack AdminInfernal_Chestplate = ArmorCreator.createItem(Material.LEATHER_CHESTPLATE, "Admin´s", Rarities.Divine, Types.Chestplate, 999,999, 999, 999, 999, lore2, Color.fromRGB(255, 111, 12));

    static List<String> lore3 = Arrays.asList(
            "§l§7Reduces the damage you take from ",
            "§l§7Iron Golem by §l§c50%"
    );
    public static ItemStack Golem_Boots = ArmorCreator.createItem(Material.IRON_BOOTS, "Golem", Rarities.Uncommon, Types.Boots, 0,25, 0, 20, 20, lore3);

    public static ItemStack Golem_Leggings = ArmorCreator.createItem(Material.IRON_LEGGINGS, "Golem", Rarities.Uncommon, Types.Leggings, 0,25, 0, 20, 20, lore3);

    public static ItemStack Golem_Chestplate = ArmorCreator.createItem(Material.IRON_CHESTPLATE, "Golem", Rarities.Uncommon, Types.Chestplate, 0,25, 0, 20, 20, lore3);

    static List<String> lore4 = Arrays.asList(
            "§l§7Reduces the damage you take from ",
            "§l§7mobs by §l§c10%"
    );
    public static ItemStack Ender_boots = ArmorCreator.createItem(Material.LEATHER_BOOTS, "Ender", Rarities.Epic, Types.Boots, 0,45, 250, 60, 60, lore, Color.fromRGB(0, 0, 0));

    public static ItemStack Ender_leggings = ArmorCreator.createItem(Material.LEATHER_LEGGINGS, "Ender", Rarities.Epic, Types.Leggings, 0,45, 250, 60, 60, lore, Color.fromRGB(0, 0, 0));

    public static ItemStack Ender_chest = ArmorCreator.createItem(Material.LEATHER_CHESTPLATE, "Ender", Rarities.Epic, Types.Chestplate, 0,45, 250, 60, 60, lore, Color.fromRGB(0, 0, 0));

    static{
        BuyPrices.addBuyPrice(Necrons_Boots, 1500000000L);
        BuyPrices.addBuyPrice(Necrons_Leggings, 2000000000L);
        BuyPrices.addBuyPrice(Necrons_Chestplate, 2500000000L);

        BuyPrices.addBuyPrice(Golem_Boots, 0L);
        BuyPrices.addBuyPrice(Golem_Leggings, 0L);
        BuyPrices.addBuyPrice(Golem_Chestplate, 0L);

        BuyPrices.addBuyPrice(Ender_boots, 15000000L);
        BuyPrices.addBuyPrice(Ender_leggings, 20000000L);
        BuyPrices.addBuyPrice(Ender_chest, 25000000L);
    }


}
