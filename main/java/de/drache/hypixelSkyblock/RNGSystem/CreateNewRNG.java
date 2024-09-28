package de.drache.hypixelSkyblock.RNGSystem;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import de.drache.hypixelSkyblock.Items.Items;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateNewRNG implements Listener {

    private static Random random = new Random();

    // Define a class to hold the item and its drop chance
    public static class DropItem {
        private ItemStack item;
        private double chance;

        public DropItem(ItemStack item, double chance) {
            this.item = item;
            this.chance = chance;
        }

        public ItemStack getItem() {
            return item;
        }

        public double getChance() {
            return chance;
        }
    }

    // Generate drop based on a list of DropItems
    public static List<ItemStack> generateDrop(List<DropItem> items) {
        List<ItemStack> droppedItems = new ArrayList<>();

        for (DropItem item : items) {
            double roll = random.nextDouble() * 100;
            if (roll < item.getChance()) {
                droppedItems.add(item.getItem());
            }
        }

        return droppedItems;
    }

    // Listener to track when an entity dies
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (event.getEntity() instanceof Enderman) {
                Player player = (Player) event.getEntity().getKiller();
                event.getEntity().remove();
                // Define your custom drop items
                List<DropItem> dropItems = new ArrayList<>();
                dropItems.add(new DropItem(Items.Summoning_Eye, 24.0 * PlayerStats.getInstance().MagicFind.get(player)));  // 24% chance for Summoning Eye
                dropItems.add(new DropItem(Items.Juju_Shortbow, 2.0 * PlayerStats.getInstance().MagicFind.get(player)));  // 5% chance for Rare Sword
                dropItems.add(new DropItem(Items.MidasStaff, 0.1 * PlayerStats.getInstance().MagicFind.get(player)));  // 50% chance for Common Gem

                // Generate the drops and add them to the death event
                List<ItemStack> drops = generateDrop(dropItems);
                for (ItemStack drop : drops) {
                    player.sendMessage("§7You just got a §l"+drop.getItemMeta().getDisplayName()+"  §b+"+PlayerStats.getInstance().MagicFind.get(player)*100+"§b Magicfind");
                    player.getInventory().addItem(drop);
                    String mainTitle = "§l§4" + drop.getItemMeta().getDisplayName();
                    String subtitle = ""; // You can add a subtitle or leave it blank
                    int fadeIn = 10;  // 0.5 seconds to fade in
                    int stay = 40;    // 2 seconds to stay on screen
                    int fadeOut = 10; // 0.5 seconds to fade out
                    player.sendTitle(mainTitle, subtitle, fadeIn, stay, fadeOut);
                }
            } else if (event.getEntity() instanceof EnderDragon) {
                Player player = (Player) event.getEntity().getKiller();
                // Define your custom drop items
                List<DropItem> dropItems = new ArrayList<>();
                dropItems.add(new DropItem(Items.Terminator_Shortbow, 1.0 * PlayerStats.getInstance().MagicFind.get(player)));  // 100% chance for Dragon Egg

                // Generate the drops and add them to the death event
                List<ItemStack> drops = generateDrop(dropItems);
                for (ItemStack drop : drops) {
                    player.getInventory().addItem(drop);
                    if (drop.isSimilar(Items.Terminator_Shortbow)) {
                        String mainTitle = "§l§4" + drop.getItemMeta().getDisplayName();
                        String subtitle = ""; // You can add a subtitle or leave it blank
                        int fadeIn = 10;  // 0.5 seconds to fade in
                        int stay = 40;    // 2 seconds to stay on screen
                        int fadeOut = 10; // 0.5 seconds to fade out
                        player.sendTitle(mainTitle, subtitle, fadeIn, stay, fadeOut);
                    }
                }

                // Method to register events
            }
        }
    }
}