package de.drache.hypixelSkyblock.Listener;

import com.destroystokyo.paper.Title;
import de.drache.hypixelSkyblock.Classes.ZealotSpawnList;
import de.drache.hypixelSkyblock.Sell.AddCoins;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class ZealotDieEvent implements Listener {
    @EventHandler
    public void onZealotDie(EntityDeathEvent event) {
        if (event.getEntity() instanceof Enderman) {
            if (event.getEntity().getKiller() != null) {
                Player player = event.getEntity().getKiller();
                event.setDroppedExp(0);
                AddCoins.addCoins(player, 1000);
                ItemStack stack = new ItemStack(Material.ENDER_EYE);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName("§l§4Summoning Eye");
                stack.addUnsafeEnchantment(Enchantment.AQUA_AFFINITY, 1);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                stack.setItemMeta(meta);
                event.getDrops().clear();
                Random random = new Random();
                int randomInt = random.nextInt(100);
                /*if (randomInt < 15) {
                    player.getInventory().addItem(stack);
                    // Title text with colors and bold style
                    String mainTitle = "§l§4Summoning Eye";
                    String subtitle = ""; // You can add a subtitle or leave it blank
                    int fadeIn = 10;  // 0.5 seconds to fade in
                    int stay = 40;    // 2 seconds to stay on screen
                    int fadeOut = 10; // 0.5 seconds to fade out
                    player.sendTitle(mainTitle, subtitle, fadeIn, stay, fadeOut);

                }*/
                ZealotSpawnList.getInstance().aliveZealots.remove(event.getEntity());
            }
        }
    }
}
