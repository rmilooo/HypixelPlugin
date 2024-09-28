package de.drache.hypixelSkyblock.Items.Listeners;

import de.drache.hypixelSkyblock.Classes.ItemsList;
import de.drache.hypixelSkyblock.DamageSystem.CalculateDamage;
import de.drache.hypixelSkyblock.Items.Items;
import de.drache.hypixelSkyblock.main;
import io.papermc.paper.entity.LookAnchor;
import io.papermc.paper.math.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Collection;

public class ShadowFurryListener implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().isRightClick()) {
            if (player.getItemInHand().isSimilar(Items.ShadowFurry)) {
                Location location = player.getLocation().add(player.getLocation().getDirection().multiply(2));
                Collection<Entity> entities = player.getWorld().getNearbyEntities(location, 20, 20, 20);

                // Counter for delay increments
                int[] delay = {0};

                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity && entity != player) {
                        LivingEntity target = (LivingEntity) entity;
                        if (target instanceof ArmorStand)return;
                        // Calculate the location behind the target entity
                        Location targetLocation = target.getLocation();
                        Location behindTarget = targetLocation.clone().add(targetLocation.getDirection().multiply(-2));
                        behindTarget.setY(targetLocation.getY()); // Stay on the same vertical level

                        // Increment the delay for each target
                        delay[0] += 5; // 20 ticks = 1 second between teleports

                        Bukkit.getScheduler().runTaskLater(main.instance, () -> {
                            if (player.getWorld().getBlockAt(behindTarget).getType() == Material.AIR){
                                player.teleport(behindTarget);
                            }
                            player.lookAt(entity.getLocation(), LookAnchor.EYES);
                            player.attack(entity);
                            target.damage(CalculateDamage.calculateDamage(player, Items.ShadowFurry, false, targetLocation), player);
                            target.setMetadata("ShadowFurry", new FixedMetadataValue(main.instance, true));
                        }, delay[0]);
                    }
                }
            }
        }
    }
}
