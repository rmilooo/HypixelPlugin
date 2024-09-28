package de.drache.hypixelSkyblock.Items.Listeners;

import de.drache.hypixelSkyblock.DamageSystem.CalculateDamage;
import de.drache.hypixelSkyblock.Items.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class AdminHypeListener implements Listener {

    @EventHandler
    public void WitherImpact(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().isSimilar(Items.AdminHype)) {
            if (e.getAction().isRightClick()) {
                e.setCancelled(true);
                Player player = e.getPlayer();
                Location location = player.getLocation();

                // Play explosion sound
                player.getWorld().playSound(location, "entity.generic.explode", 1.0F, 1.0F);

                // Create explosion particles
                for (int i = 0; i < 200; i++) {
                    // Generate random offsets in the range [-7, 7]
                    int x = getRandomInt(15) - 7;
                    int y = getRandomInt(15) - 7;
                    int z = getRandomInt(15) - 7;

                    // Make sure the offset is within the 7-block radius
                    if (Math.sqrt(x * x + y * y + z * z) <= 7) {
                        Location newloc = location.clone().add(x, y, z);
                        location.getWorld().spawnParticle(Particle.EXPLOSION, newloc, 0);
                    }
                }

                // Apply potion effects to the player
                PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 20 * 10, 1);
                PotionEffect effect2 = new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 10, 1);
                PotionEffect effect3 = new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 3);
                player.addPotionEffect(effect);
                player.addPotionEffect(effect2);
                player.addPotionEffect(effect3);

                // Teleport player 7 blocks ahead in the direction they are looking
                teleportPlayerForward(player, 12);

                // Deal damage to nearby entities
                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                    if (entity instanceof LivingEntity target) {
                        if (target != player) {
                            if (target instanceof ArmorStand)return;
                            target.damage(CalculateDamage.calculateDamage(player, Items.AdminHype, true, target.getLocation()), player);
                        }
                    }
                }
            }
        }
    }


    private void teleportPlayerForward(Player player, double distance) {
        Location loc = player.getLocation();
        Vector direction = loc.getDirection().normalize().multiply(distance);
        Location targetLocation = loc.clone().add(direction);
        targetLocation.setPitch(player.getLocation().getPitch());
        targetLocation.setYaw(player.getLocation().getYaw());

        // Check for obstacles along the path
        Location currentLocation = loc.clone();
        for (double i = 0; i < distance; i += 0.5) { // Check every half-block along the path
            currentLocation.add(direction.clone().normalize().multiply(0.5));
            Block block = currentLocation.getBlock();
            if (block.getType() != Material.AIR) {
                // If there's a solid block, adjust targetLocation
                targetLocation = currentLocation.clone().subtract(direction.clone().normalize().multiply(0.5));
                break;
            }
        }

        // Ensure targetLocation is slightly above the block to avoid issues
        targetLocation.add(0, 1, 0);

        // Teleport the player to the target location
        player.teleport(targetLocation);
    }

    public static Integer getRandomInt(Integer max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }
}
