package de.drache.hypixelSkyblock.ArmorSystem;

import de.drache.hypixelSkyblock.Classes.ItemsList;
import de.drache.hypixelSkyblock.Classes.PlayerStats;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorStatListener implements Listener {
    public static ArmorStatListener instance;

    public ArmorStatListener() {
        instance = this;
    }

    public static ArmorStatListener getInstance() {
        if (instance == null) {
            instance = new ArmorStatListener();
        }
        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updateArmorStats(player); // Apply stats when the player joins
    }

    @EventHandler
    public void onArmorChange(PlayerInventorySlotChangeEvent event) {
        Player player = event.getPlayer();
        updateArmorStats(player); // Update stats when the player's armor changes
    }

    @EventHandler
    public void onArmorChange2(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //updateArmorStats(player); // Update stats when player interacts
    }

    public void updateArmorStats(Player player) {
        double baseHealth = 20.0;

        // Calculate total bonuses from all equipped armor pieces
        double totalHealthBonus = 0;
        double totalStrengthBonus = 0;
        double totalDefenseBonus = 0;
        double totalManaBonus = 0;
        double totalMagicFindBonus = 0;

        // List of relevant armor slots
        ItemStack[] armorPieces = {
                player.getInventory().getBoots(),
                player.getInventory().getLeggings(),
                player.getInventory().getChestplate(),
                player.getInventory().getHelmet(),
                player.getInventory().getItemInHand()
        };

        for (ItemStack armorPiece : armorPieces) {
            if (armorPiece != null) {
                totalHealthBonus += checkAndApplyHealthBonus(armorPiece);
                totalStrengthBonus += checkAndApplyStrengthBonus(armorPiece);
                totalDefenseBonus += checkAndApplyDefenseBonus(armorPiece);
                totalManaBonus += checkAndApplyManaBonus(armorPiece);
                totalMagicFindBonus += checkAndApplyMagicFindBonus(armorPiece);
            }
        }

        // Apply bonuses
        PlayerStats.getInstance().Defense.put(player, 1 + (totalDefenseBonus * 0.1) * (totalHealthBonus / 100) / 5 / 2);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth + totalHealthBonus);

        PlayerStats.getInstance().MagicFind.put(player, (totalMagicFindBonus + 100)/100);
        PlayerStats.getInstance().PlayerStrength.put(player, totalStrengthBonus);
        PlayerStats.getInstance().MaxPlayerMana.put(player, totalManaBonus + 200);
    }

    // Check and apply bonuses methods
    private double checkAndApplyHealthBonus(ItemStack armorPiece) {
        if (ArmorList.getInstance().ItemHealth.containsKey(armorPiece)) {
            return (double) ArmorList.getInstance().ItemHealth.get(armorPiece) / 10;
        } else if (ItemsList.getInstance().ItemHealth.containsKey(armorPiece)) {
            return (double) ItemsList.getInstance().ItemHealth.get(armorPiece);
        }
        return 0;
    }

    private double checkAndApplyStrengthBonus(ItemStack armorPiece) {
        if (ArmorList.getInstance().ItemStrength.containsKey(armorPiece)) {
            return (double) ArmorList.getInstance().ItemStrength.get(armorPiece);
        } else if (ItemsList.getInstance().ItemStrength.containsKey(armorPiece)) {
            return (double) ItemsList.getInstance().ItemStrength.get(armorPiece);
        }
        return 0;
    }

    private double checkAndApplyDefenseBonus(ItemStack armorPiece) {
        if (ArmorList.getInstance().ItemDefense.containsKey(armorPiece)) {
            return (double) ArmorList.getInstance().ItemDefense.get(armorPiece);
        } else if (ItemsList.getInstance().ItemDefense.containsKey(armorPiece)) {
            return (double) ItemsList.getInstance().ItemDefense.get(armorPiece);
        }
        return 0;
    }

    private double checkAndApplyManaBonus(ItemStack armorPiece) {
        if (ArmorList.getInstance().ItemMana.containsKey(armorPiece)) {
            return (double) ArmorList.getInstance().ItemMana.get(armorPiece);
        } else if (ItemsList.getInstance().ItemMana.containsKey(armorPiece)) {
            return (double) ItemsList.getInstance().ItemMana.get(armorPiece);
        }
        return 0;
    }
    private double checkAndApplyMagicFindBonus(ItemStack armorPiece) {
        if (ArmorList.getInstance().ItemMagicFind.containsKey(armorPiece)) {
            return (double) ArmorList.getInstance().ItemMagicFind.get(armorPiece);
        }
        return 0;
    }
}
