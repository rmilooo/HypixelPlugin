package de.drache.hypixelSkyblock.DamageSystem;

import de.drache.hypixelSkyblock.Classes.ItemsList;
import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CalculateDamage {
    public static Double calculateDamage(Player player, ItemStack stack, Boolean isMagicDamage, Location location) {
        if (ItemsList.getInstance().ItemDamage.containsKey(stack)) {
            Random random = new Random();
            double min = 5.0;
            double max = 30.0;
            double randomValue = min + (max - min) * random.nextDouble();
            double damage = ItemsList.getInstance().ItemDamage.get(stack);
            double strength = PlayerStats.getInstance().PlayerStrength.get(player);

            if (isMagicDamage) {
                damage = Math.max(0, damage * ((900 + PlayerStats.getInstance().MaxPlayerMana.get(player)) / 900) - 150);
            }
            if (strength == 0){
                DamageHoloGram.showDamageHoloGram(damage + randomValue, location);
                return randomValue + damage;
            }else {
                DamageHoloGram.showDamageHoloGram(randomValue + damage * (strength / 100), location);
                return randomValue + damage * (strength / 100);

            }
        }
        return 0.0;
    }
}
