package de.drache.hypixelSkyblock.Items.Listeners;

import de.drache.hypixelSkyblock.Classes.ItemsList;
import de.drache.hypixelSkyblock.DamageSystem.CalculateDamage;
import de.drache.hypixelSkyblock.Items.Items;
import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import java.util.Comparator;
import java.util.List;

public class JujuShotbowListener implements Listener {

    @EventHandler
    public void traceArrowToEntity(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Check if the player is holding the Juju Shortbow
        if (event.getItem() != null && event.getItem().isSimilar(Items.Juju_Shortbow)) {
            event.setCancelled(true); // Cancel default bow behavior

            // Launch 3 arrows
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();

            // Middle arrow
            Arrow middleArrow = launchHomingArrow(player, direction, eyeLocation);

            // Left arrow (slightly offset)
            Vector leftOffset = direction.clone().rotateAroundY(Math.toRadians(-15));
            Arrow leftArrow = launchHomingArrow(player, leftOffset, eyeLocation);

            // Right arrow (slightly offset)
            Vector rightOffset = direction.clone().rotateAroundY(Math.toRadians(15));
            Arrow rightArrow = launchHomingArrow(player, rightOffset, eyeLocation);
            middleArrow.setVelocity(middleArrow.getVelocity().multiply(3));
            leftArrow.setVelocity(leftArrow.getVelocity().multiply(3));
            rightArrow.setVelocity(rightArrow.getVelocity().multiply(3));
            // Task to trace nearest entity
            Bukkit.getScheduler().runTaskTimer(main.instance, () -> {
                traceNearestEntity(player, middleArrow);
                traceNearestEntity(player, leftArrow);
                traceNearestEntity(player, rightArrow);
            }, 0L, 1L); // Runs every tick
        }
    }

    private Arrow launchHomingArrow(Player player, Vector direction, Location location) {
        Arrow arrow = player.getWorld().spawnArrow(location, direction, 1.5f, 0f);
        arrow.setShooter(player);
        arrow.setMetadata("homing", new FixedMetadataValue(main.instance, true)); // Mark arrow as homing
        return arrow;
    }

    private void traceNearestEntity(Player player, Arrow arrow) {
        if (!arrow.isValid() || arrow.isInBlock()) {
            return; // Stop if the arrow is no longer valid
        }

        List<Entity> nearbyEntities = arrow.getNearbyEntities(5, 5, 5);
        Entity target = nearbyEntities.stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(entity -> !(entity instanceof Player))
                .filter(entity -> !(entity instanceof ArmorStand))// Avoid targeting the shooter
                .min(Comparator.comparingDouble(entity -> entity.getLocation().distance(arrow.getLocation())))
                .orElse(null);

        // Prioritize EnderDragon if available
        for (Entity entity : nearbyEntities) {
            if (entity instanceof EnderDragon) {
                target = entity;
                break;
            }
        }

        if (target instanceof Enderman enderman) {
            enderman.setMetadata("targetedByJujuArrow", new FixedMetadataValue(main.instance, true));
        }

        if (target != null) {
            Location arrowLoc = arrow.getLocation();
            Location targetLoc = target.getLocation().add(0, target.getHeight() / 2, 0); // Aim for the center of the entity
            double distance = arrowLoc.distance(targetLoc);
            if (target instanceof Enderman) {
                if (distance <= 2) {
                    if (target instanceof LivingEntity livingTarget) {
                        livingTarget.setNoDamageTicks(0); // Reset damage cooldown
                        livingTarget.damage(CalculateDamage.calculateDamage(player, Items.Juju_Shortbow, false, targetLoc), player);
                        livingTarget.setNoDamageTicks(0);
                    }
                    arrow.remove(); // Remove the arrow once it hits
                } else {
                    // Otherwise, adjust the arrow's trajectory towards the target
                    Vector toTarget = targetLoc.toVector().subtract(arrowLoc.toVector()).normalize();
                    arrow.setVelocity(toTarget.multiply(1.5)); // Adjust velocity towards the target
                }
            } else if (distance <= 1) {
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.setNoDamageTicks(0); // Reset damage cooldown
                    livingTarget.damage(CalculateDamage.calculateDamage(player, Items.Juju_Shortbow, false, targetLoc), player); // Deal damage
                    livingTarget.setNoDamageTicks(0);
                }
                arrow.remove(); // Remove the arrow once it hits
            } else if (target instanceof EnderDragon living) {
                if (distance <= 6) {
                    // Otherwise, adjust the arrow's trajectory towards the target
                    living.setNoDamageTicks(0);
                    living.damage(CalculateDamage.calculateDamage(player, Items.Juju_Shortbow, false, targetLoc), player); // Deal damage
                    living.setNoDamageTicks(0);
                    arrow.remove();
                }
            }else {
                Vector toTarget = targetLoc.toVector().subtract(arrowLoc.toVector()).normalize();
                arrow.setVelocity(toTarget.multiply(1.5)); // Adjust velocity towards the target
            }
        }
    }


    @EventHandler
    public void onArrowHitGround(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            // Check if it's one of the homing arrows by checking its metadata
            if (arrow.hasMetadata("homing")) {
                // Remove the arrow when it hits the ground
                    arrow.remove();
            }
        }
    }

    @EventHandler
    public void onEndermanTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Enderman enderman) {
            event.setCancelled(true);
            // Check if the teleport was caused by one of the homing arrows
            if (enderman.hasMetadata("targetedByJujuArrow")) {
                event.setCancelled(true); // Cancel the teleport
                Bukkit.getScheduler().runTaskLater(main.instance, () -> {
                    if (enderman != null) {
                        enderman.removeMetadata("targetedByJujuArrow", main.instance);
                    }
                }, 20L); // Re-enable teleportation after 1 second
            }
        }
    }

}
