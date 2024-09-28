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

public class TerminatorShotbowListener implements Listener {

    @EventHandler
    public void traceArrowToEntity(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Check if the player is holding the Terminator Shortbow
        if (event.getItem() != null && event.getItem().isSimilar(Items.Terminator_Shortbow)) {
            event.setCancelled(true); // Cancel default bow behavior

            // Launch 5 arrows with different offsets
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();

            Arrow middleArrow = launchHomingArrow(player, direction, eyeLocation);
            Arrow leftArrow = launchHomingArrow(player, direction.clone().rotateAroundY(Math.toRadians(-8)), eyeLocation);
            Arrow rightArrow = launchHomingArrow(player, direction.clone().rotateAroundY(Math.toRadians(8)), eyeLocation);
            Arrow leftArrow2 = launchHomingArrow(player, direction.clone().rotateAroundY(Math.toRadians(-15)), eyeLocation);
            Arrow rightArrow2 = launchHomingArrow(player, direction.clone().rotateAroundY(Math.toRadians(15)), eyeLocation);

            // Speed up the arrows
            middleArrow.setVelocity(middleArrow.getVelocity().multiply(3));
            leftArrow.setVelocity(leftArrow.getVelocity().multiply(3));
            rightArrow.setVelocity(rightArrow.getVelocity().multiply(3));
            leftArrow2.setVelocity(leftArrow2.getVelocity().multiply(3));
            rightArrow2.setVelocity(rightArrow2.getVelocity().multiply(3));

            // Task to trace nearest entities for all arrows
            Bukkit.getScheduler().runTaskTimer(main.instance, () -> {
                traceNearestEntity(player, middleArrow);
                traceNearestEntity(player, leftArrow);
                traceNearestEntity(player, rightArrow);
                traceNearestEntity(player, leftArrow2);
                traceNearestEntity(player, rightArrow2);
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

        List<Entity> nearbyEntities = arrow.getNearbyEntities(7, 7, 7);
        Entity target = nearbyEntities.stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(entity -> !(entity instanceof Player || entity instanceof ArmorStand)) // Avoid players and ArmorStands
                .min(Comparator.comparingDouble(entity -> entity.getLocation().distance(arrow.getLocation())))
                .orElse(null);

        // Prioritize EnderDragon
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

            if (distance <= 5 && target instanceof LivingEntity livingTarget) {
                livingTarget.setNoDamageTicks(0); // Reset damage cooldown
                livingTarget.damage(CalculateDamage.calculateDamage(player, Items.Terminator_Shortbow, false, targetLoc), player);
                livingTarget.setNoDamageTicks(0);
                arrow.remove(); // Remove the arrow once it hits
            } else if (target instanceof EnderDragon dragon && distance <= 10){
                dragon.setNoDamageTicks(0); // Reset damage cooldown
                dragon.damage(CalculateDamage.calculateDamage(player, Items.Terminator_Shortbow, false, targetLoc), player);
                dragon.setNoDamageTicks(0);
                arrow.remove();
            }else {
                Vector toTarget = targetLoc.toVector().subtract(arrowLoc.toVector()).normalize();
                arrow.setVelocity(toTarget.multiply(1.5)); // Adjust velocity towards the target
            }
        }
    }

    @EventHandler
    public void onArrowHitGround(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow && arrow.hasMetadata("homing")) {
            arrow.remove(); // Remove the arrow when it hits the ground
        }
    }

    @EventHandler
    public void onEndermanTeleport(EntityTeleportEvent event) {
        event.setCancelled(true);
        if (event.getEntity() instanceof Enderman enderman && enderman.hasMetadata("targetedByJujuArrow")) {
            event.setCancelled(true); // Cancel the teleport
            Bukkit.getScheduler().runTaskLater(main.instance, () -> {
                if (enderman.isValid()) {
                    enderman.removeMetadata("targetedByJujuArrow", main.instance);
                }
            }, 20L); // Re-enable teleportation after 1 second
        }
    }
}
