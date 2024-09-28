package de.drache.hypixelSkyblock.Listener;

import de.drache.hypixelSkyblock.Listener.SummonEnderDragon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

public class EnderDragonCustom implements Listener {

    private static BossBar bar = null;
    private static boolean enraged = false;  // A flag to activate special abilities
    private static final double MAX_HEALTH = 2048 * 20;  // Set max health for dragons
    private static final HashMap<UUID, Double> dragonHealthMap = new HashMap<>();  // Store custom health values

    // Constructor
    public EnderDragonCustom() {
        // Create the BossBar for the dragon health display
        bar = Bukkit.createBossBar("§4Dragon Health", BarColor.RED, BarStyle.SOLID);
    }

    public static void Init(EnderDragon dragon, Location location) {
        // Set the dragon's health in the custom HashMap
        dragonHealthMap.put(dragon.getUniqueId(), MAX_HEALTH);

        // Set the initial phase to HOVER to prevent it from flying away
        dragon.setPhase(EnderDragon.Phase.HOVER);

        // Make the dragon constantly focus on the player's location
        new BukkitRunnable() {
            @Override
            public void run() {
                if (dragon.isDead() || bar == null) {
                    if (bar != null) {
                        bar.removeAll();
                    }
                    this.cancel();
                    return;
                }

                // Make the dragon stay close to the player or target location
                Location dragonLocation = dragon.getLocation();
                double distance = dragonLocation.distance(location);

                // If the dragon is too far from the player, teleport it closer or adjust its phase
                if (distance > 50) {
                    dragon.teleport(location); // Optionally teleport to the player's location
                    dragon.setPhase(EnderDragon.Phase.HOVER);
                } else {
                    // If within range, make the dragon circle around the player
                    dragon.setPodium(location);
                    dragon.setPhase(EnderDragon.Phase.CIRCLING);
                }

                if (enraged) {
                    performSpecialAttack(dragon);
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("HypixelSkyblock"), 20, 100); // Repeat every 5 seconds
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof EnderDragon && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            EnderDragon dragon = (EnderDragon) event.getEntity();
            UUID dragonId = dragon.getUniqueId();

            // Get the dragon's current custom health
            double currentHealth = dragonHealthMap.getOrDefault(dragonId, MAX_HEALTH);
            if (currentHealth <= 0 || dragon.isDead()) {
                event.setCancelled(true);  // Prevent further damage after death
                if (dragon != null){
                    dragon.remove();
                }
                return;
            }

            // Handle custom damage scaling...
            if (event.getEntity() instanceof EnderDragon && event.getDamager() instanceof Player) {

                // Scale the damage
                double calculatedDamage = event.getDamage() / 25; // Scale original damage

                // Update the dragon's health in the HashMap
                double newHealth = Math.max(currentHealth - calculatedDamage, 0);
                player.sendMessage(newHealth+"");
                dragonHealthMap.put(dragonId, newHealth);

                // Update the BossBar
                bar.setProgress(newHealth / MAX_HEALTH);

                // Enrage the dragon when health falls below 50%
                if (newHealth <= MAX_HEALTH * 0.5 && !enraged) {
                    enraged = true;
                    dragon.setPhase(EnderDragon.Phase.CHARGE_PLAYER);
                    Bukkit.broadcastMessage("§cThe Dragon has become enraged!");
                }

                // Check if the dragon should die
                if (newHealth <= 0) {
                    handleDragonDeath(dragon, player);
                }

                event.setDamage(0); // Cancel default damage handling since we handled it manually
                bar.addPlayer(player); // Add the player back to the BossBar
            } else if (!(event.getDamager() instanceof Player)) {
                event.setCancelled(true); // Prevent other entities from damaging the dragon
            }
        }
    }

    private void handleDragonDeath(EnderDragon dragon, Player player) {
        UUID dragonId = dragon.getUniqueId();

        // Check if the dragon is already dead to avoid redundant calls
        if (!dragon.isDead()) {
            // Remove dragon from custom health system
            dragonHealthMap.remove(dragonId);
            dragon.setHealth(0);  // This triggers natural death mechanics
            dragon.remove();
            // Ensure proper cleanup
            if (bar != null) {
                bar.removeAll();
                bar = null;
            }

            SummonEnderDragon.getInstance().clearAllEyes();  // Custom method to clear dragon visuals
            enraged = false;
            player.sendMessage("§4The Dragon has been slain!");
        }
    }



    private static void performSpecialAttack(EnderDragon dragon) {
        World world = dragon.getWorld();
        Location dragonLocation = dragon.getLocation();

        // Select a random special ability
        int attack = (int) (Math.random() * 4);

        switch (attack) {
            case 0:
                // Fireball Barrage
                for (Player target : dragon.getNearbyEntities(50, 50, 50).stream()
                        .filter(e -> e instanceof Player)
                        .map(e -> (Player) e)
                        .toArray(Player[]::new)) {
                    dragon.launchProjectile(org.bukkit.entity.Fireball.class, target.getLocation().toVector().subtract(dragonLocation.toVector()).normalize().multiply(2));
                }
                break;
            case 1:
                // Summon Minions
                for (int i = 0; i < 3; i++) {
                    world.spawnEntity(dragonLocation, EntityType.ENDERMAN);
                }
                break;
            case 2:
                // AoE Attack
                for (Player target : dragon.getNearbyEntities(10, 10, 10).stream()
                        .filter(e -> e instanceof Player)
                        .map(e -> (Player) e)
                        .toArray(Player[]::new)) {
                    target.damage(15.0);  // Deal 15 damage to all players in a radius
                }
                break;
            case 3:
                // Lightning Strike
                Player nearest = dragon.getNearbyEntities(100, 100, 100).stream()
                        .filter(e -> e instanceof Player)
                        .map(e -> (Player) e)
                        .min(Comparator.comparingDouble(p -> dragonLocation.distance(p.getLocation())))
                        .orElse(null);
                if (nearest != null) {
                    world.strikeLightning(nearest.getLocation());
                }
                break;
        }
    }
}
