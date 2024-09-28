package de.drache.hypixelSkyblock.Items;

import de.drache.hypixelSkyblock.Sell.BuyPrices;
import de.drache.hypixelSkyblock.Sell.SellPrices;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Items implements Listener {
    //Midas Staff
    static List<String> lore = Arrays.asList(
            "§f§6Ability: Molten Wave",
            "§7Cast a wave of molten gold in the direction you are facing!", "§7Deals up to §c6,000 - 32,000 damage."
    );
    public static final ItemStack MidasStaff = ItemCreator.createItem(Material.GOLDEN_SHOVEL, "Midas", Rarities.Legendary, ItemTypes.Staff, 250, 40, 800, 0, 0, lore, true);

    //Juju
    static List<String> lore2 = Arrays.asList(
            "§f§5Ability: Tracer",
            "§7The Arrows trace the nearest Mob. priority: §cEnderDragon"
    );
    public static final ItemStack Juju_Shortbow = ItemCreator.createItem(Material.BOW, "Juju", Rarities.Epic, ItemTypes.Shortbow, 20, 20, 0, 0, 0, lore2, true);

    //Term
    static List<String> lore3 = Arrays.asList(
            "§f§dAbility: Tracer",
            "§7The Arrows trace the nearest Mob. priority: §cEnderDragon"
    );
    public static final ItemStack Terminator_Shortbow = ItemCreator.createItem(Material.BOW, "Terminator", Rarities.Mythical, ItemTypes.Shortbow, 45, 100, 0, 0, 0, lore3, false);

    //Hype
    static List<String> lore4 = Arrays.asList(
            "§f§dAbility: Wither Impact",
            "§7A big §dExplosion"
    );
    public static final ItemStack Hyperion = ItemCreator.createItem(Material.IRON_SWORD, "Hyperion", Rarities.Mythical, ItemTypes.Sword, 200, 40, 1000, 0, 0, lore4, false);

    //Midas Staff
    static List<String> lore5 = Arrays.asList(
            "§f§6Ability: Shadow Furry",
            "§7Teleports to Entities and deals a lot of damage"
    );
    public static final ItemStack ShadowFurry = ItemCreator.createItem(Material.STONE_SWORD, "⚚ Shadow Fury", Rarities.Legendary, ItemTypes.Sword, 210, 40, 200, 0, 0, lore5, false);

    static List<String> lore6 = Arrays.asList(
            "§7A Summoning Eye used to summon the §cDragon§7."
    );
    public static final ItemStack Summoning_Eye = ItemCreator.createItem(Material.ENDER_EYE, "Summoning Eye", Rarities.Epic, ItemTypes.None, 0, 0, 0, 0, 0, lore6, false);

    static List<String> lore7 = Arrays.asList(
            "§f§bAbility: Wither Impact",
            "§7A big §bExplosion",
            "§cOnly given to Admins"
    );
    public static final ItemStack AdminHype = ItemCreator.createItem(Material.IRON_SWORD, "Admin Hype", Rarities.Divine, ItemTypes.Sword, 9999, 9999, 9999, 9999, 9999, lore7, false);


    static List<String> lore8 = Arrays.asList(
            "§f§aAbility: Punch",
            "§7Deals some Damage to nearby Entities"
    );
    public static final ItemStack GolemSword = ItemCreator.createItem(Material.IRON_SWORD, "Golem Sword", Rarities.Uncommon, ItemTypes.Sword, 120, 20, 0, 0, 0, lore8, false);

    static List<String> lore9 = Arrays.asList(
            "§f§dAbility: Auto Sell",
            "§7Automatically sells items you put in there"
    );
    public static final ItemStack Autoseller = ItemCreator.createItem(Material.BLUE_DYE, "Auto Seller", Rarities.Mythical, ItemTypes.None, 0, 0, 0, 0, 0, lore9, false);

    static List<String> lore10 = Arrays.asList(
            "§7Used to Craft different Dragon Armors"
    );
    public static final ItemStack SuperiorFragments = ItemCreator.createItem(Material.YELLOW_DYE, "Superior Fragment", Rarities.Legendary, ItemTypes.None, 0, 0, 0, 0, 0, lore10, false);

    // Move the addSellPrices call to a static block or constructor
    static {
        SellPrices.addSellPrice(MidasStaff, 20000000L);
        SellPrices.addSellPrice(Juju_Shortbow, 2500000);
        SellPrices.addSellPrice(Terminator_Shortbow, 200000000L);
        SellPrices.addSellPrice(Hyperion, 150000000L);
        SellPrices.addSellPrice(ShadowFurry, 10000000L);
        SellPrices.addSellPrice(Summoning_Eye, 500000);

        BuyPrices.addBuyPrice(GolemSword, 0L);
    }

}
