package de.drache.hypixelSkyblock.Kudra;

import de.drache.hypixelSkyblock.BasicSystemManger.PlayerBreakBlock;
import de.drache.hypixelSkyblock.Items.Items;
import de.drache.hypixelSkyblock.RNGSystem.CreateNewRNG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static de.drache.hypixelSkyblock.RNGSystem.CreateNewRNG.generateDrop;

public class KudraManager implements Listener {
    BossBar bar = Bukkit.createBossBar("§4Kudra Health", BarColor.RED, BarStyle.SOLID);

    // Spawns the Kudra (Slime) for the player
    public static void spawnKudra(Kudra kudra, Player player) {
        if (kudra.getMainBody() == null) {
            kudra.setTargets(List.of(player));
            kudra.setPhase(KudraPhase.Attacking);
            KudraStorage.getInstance().kudra = kudra;
            Slime kudraBody = (Slime) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.SLIME);

            // Start the task to update the slime behavior
            KudraUpdate.startTask();

            kudra.setMainBody(kudraBody);
            kudra.getMainBody().setSize(20);

            // Handle AI manually since Slimes do not have setWander or setAI methods
            kudra.getMainBody().setAI(false);

            // Set health of the Slime
            if (kudra.getHealth() != 0) {
                kudra.setHealth(kudra.getHealth());
            } else {
                kudra.setHealth(2048);
            }
        }
    }

    // Prevents splitting of Kudra
    @EventHandler
    public void onKudraDeath(SlimeSplitEvent e) {
        e.setCount(0); // Prevent slime from splitting
        KudraUpdate.cancelTask();
        bar.removeAll();
        Player player = e.getEntity().getKiller();
        List<CreateNewRNG.DropItem> dropItems = new ArrayList<>();
        dropItems.add(new CreateNewRNG.DropItem(Items.Hyperion, 10.0));  // 100% chance for Dragon Egg

        // Generate the drops and add them to the death event
        List<ItemStack> drops = generateDrop(dropItems);
        for (ItemStack drop : drops) {
            player.getInventory().addItem(drop);
            if (drop.isSimilar(Items.Hyperion)) {
                String mainTitle = "§l§4" + drop.getItemMeta().getDisplayName();
                String subtitle = ""; // You can add a subtitle or leave it blank
                int fadeIn = 10;  // 0.5 seconds to fade in
                int stay = 40;    // 2 seconds to stay on screen
                int fadeOut = 10; // 0.5 seconds to fade out
                player.sendTitle(mainTitle, subtitle, fadeIn, stay, fadeOut);
            }
        }
    }

    // Manages Kudra damage events
    @EventHandler
    public void KudraGetsDamaged(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Slime) {
            Slime slime = (Slime) event.getEntity();
            assert KudraStorage.getInstance().kudra != null;
            if (KudraStorage.getInstance().kudra.getPhase() == KudraPhase.Invincible) {
                event.setCancelled(true);
                return;
            }
            if (event.getDamager() instanceof Player player) {
                if (player.getItemInHand().getType() == Material.BOW) {
                    event.setCancelled(true);
                }
                    // Create or update the BossBar
                if (KudraStorage.getInstance().kudra.getTargets().contains(player)){
                }else {
                    KudraStorage.getInstance().kudra.getTargets().add(player);
                }
                if (bar.getPlayers().contains(player)){

                }else {
                    bar.addPlayer(player);
                }
                double maxHealth = slime.getMaxHealth();
                double currentHealth = slime.getHealth();
                double damage = event.getDamage();

                // Cap the damage at 10% of max health or divide by 3, whichever is lower
                double maxDamage = maxHealth * 0.5;
                if (damage > maxDamage) {
                    event.setDamage(maxDamage / 10);
                } else {
                    event.setDamage(damage / 10);
                }

                // Update the BossBar's progress after calculating new health
                double newHealth = currentHealth - event.getDamage();
                bar.setProgress(newHealth / maxHealth);

                // Set the Slime's custom name with updated health
                slime.setCustomNameVisible(true);
                slime.setCustomName("§l§cKudra: §l§4" + Math.round(newHealth) + " / " + Math.round(maxHealth));
            }
        }
    }
}
