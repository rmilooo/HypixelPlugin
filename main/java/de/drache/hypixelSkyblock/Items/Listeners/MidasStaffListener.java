package de.drache.hypixelSkyblock.Items.Listeners;

import de.drache.hypixelSkyblock.Classes.AbilityUse;
import de.drache.hypixelSkyblock.Classes.ItemsList;
import de.drache.hypixelSkyblock.DamageSystem.CalculateDamage;
import de.drache.hypixelSkyblock.Items.ItemCreator;
import de.drache.hypixelSkyblock.Items.Items;
import de.drache.hypixelSkyblock.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import java.util.Objects;
import java.util.UUID;

public class MidasStaffListener implements Listener {

    // Constructor
    public MidasStaffListener() {
    }

    // Handle right-click events
    // Handle right-click events
    @EventHandler
    public void rightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (event.getAction().isRightClick())
            // Check if the player has a "Midas" item and if it's not on cooldown
            if (item != null && item.isSimilar(Items.MidasStaff)) {
                if (player.hasCooldown(item.getType())) {
                    player.sendMessage("§4Midas Staff is on cooldown!");
                    return; // Exit if the item is still on cooldown
                }
                if (AbilityUse.useAbility("Molten Wave", 200, player)) {
                    // Set a 3-second cooldown for the item
                    player.setCooldown(item.getType(), 20 * 3); // 60 ticks = 3 seconds

                    // Get the start location in front of the player
                    Location startLocation = player.getEyeLocation().add(player.getLocation().getDirection().multiply(2));
                    Vector direction = getDirection(player);

                    // Schedule falling block tasks for 5 rows
                    for (int row = 0; row < 5; ++row) {
                        int finalRow = row;

                        // Schedule task with delay for each row
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // Spawn the middle 3 blocks in the row with correct positioning
                                for (int col = -1; col <= 1; ++col) {
                                    // Adjust for the column (left or right of the direction)
                                    Location blockLocation = startLocation.clone().add(direction.clone().multiply(finalRow)).add(direction.clone().crossProduct(new Vector(0, 1, 0)).multiply(col));

                                    spawnFallingBlock(blockLocation, player);
                                }
                            }
                        }.runTaskLater(main.instance, finalRow * 2L);
                    }
                }
            }
    }


    // Spawn a falling block at the given location
    private void spawnFallingBlock(Location location, Player player) {
        // Spawn the falling block (e.g., gold block)
        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location, Material.GOLD_BLOCK.createBlockData());
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(true);

        // Set metadata to store the player who spawned the block
        fallingBlock.setMetadata("midasOwner", new FixedMetadataValue(main.instance, player.getUniqueId().toString()));
    }

    // Handle block transformation from falling blocks
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock fallingBlock) {
            if (fallingBlock.getBlockData().getMaterial() == Material.GOLD_BLOCK) {

                // Retrieve the player's UUID from metadata
                String playerUUID = fallingBlock.getMetadata("midasOwner").stream()
                        .filter(meta -> meta.getOwningPlugin() == main.instance)
                        .findFirst()
                        .map(MetadataValue::asString)
                        .orElse(null);

                // Cancel the block change event (to prevent it from placing the block)
                event.setCancelled(true);

                // If the playerUUID is found, proceed with damaging nearby entities
                if (playerUUID != null) {
                    Player player = Bukkit.getPlayer(UUID.fromString(playerUUID));
                    if (player != null) {
                        // Loop through nearby entities and damage them
                        for (Entity entity : fallingBlock.getNearbyEntities(1.0, 1.0, 1.0)) {
                            if (entity instanceof LivingEntity livingEntity) {
                                if (livingEntity instanceof ArmorStand || livingEntity == player) {
                                    continue; // Skip but continue checking other entities
                                }


                                livingEntity.damage(CalculateDamage.calculateDamage(player, Items.MidasStaff, true, livingEntity.getLocation()), player);
                                event.getEntity().remove();
                            }
                        }
                    }
                }
            }
        }
    }
    // Calculate the direction based on player's yaw
    private Vector getDirection(Player player) {
        float yaw = player.getLocation().getYaw();
        double radians = Math.toRadians(yaw);
        double x = -Math.sin(radians);
        double z = Math.cos(radians);
        return new Vector(x, 0.0, z).normalize();
    }
}
