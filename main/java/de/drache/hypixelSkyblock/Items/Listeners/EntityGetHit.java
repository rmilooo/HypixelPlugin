package de.drache.hypixelSkyblock.Items.Listeners;

import de.drache.hypixelSkyblock.Classes.AbilityUse;
import de.drache.hypixelSkyblock.DamageSystem.CalculateDamage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityGetHit implements Listener {
    @EventHandler
    public void onEntityGetHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (!AbilityUse.Ability.get(player)) {
                if (event.getEntity() instanceof LivingEntity living) {
                    Double damage = CalculateDamage.calculateDamage(player, player.getItemInHand(), false, living.getLocation());
                    event.setDamage(damage);
                }
            }
        }
    }
}
