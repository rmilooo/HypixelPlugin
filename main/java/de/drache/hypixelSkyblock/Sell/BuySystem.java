package de.drache.hypixelSkyblock.Sell;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuySystem {
    public static void buy(Player player, Long Price, ItemStack itemstack){
        if (PlayerStats.getInstance().Coins.get(player) != null){
            Long coins = PlayerStats.getInstance().Coins.get(player);
            if (coins >= Price){
                PlayerStats.getInstance().Coins.put(player, coins - Price);
                player.getInventory().addItem(itemstack);
                player.sendMessage("You bought " + itemstack.getAmount() + "x " + itemstack.getItemMeta().getDisplayName());
            }
        }
    }
}
