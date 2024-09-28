package de.drache.hypixelSkyblock.Items.AutoSeller;

import de.drache.hypixelSkyblock.Items.Items;
import de.drache.hypixelSkyblock.Sell.SellPrices;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ASInteractListener implements Listener {
    @EventHandler
    public void onASInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the item is null or not the AutoSeller
        if (item == null || !item.isSimilar(Items.Autoseller)) {
            return;
        }

        // Check if the player's inventory open map entry is null or false
        Boolean isInventoryOpen = ASInv.playerinvopenmap.get(player);

        if (isInventoryOpen == null || !isInventoryOpen) {
            // Create and open the inventory if it's not already open
            ASInv.getInstance().createInv(player);
            ASInv.getInstance().openInv(player);
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void ClickInv(InventoryClickEvent e) {
        List<Integer> stacklist = new ArrayList<>();
        stacklist.add(12); // 3 + 9
        stacklist.add(13); // 4 + 9
        stacklist.add(14); // 5 + 9
        stacklist.add(21); // 3 + 9 + 9
        stacklist.add(22); // 4 + 9 + 9
        stacklist.add(23); // 5 + 9 + 9

        if (e.getWhoClicked() instanceof Player player) {
            if (ASInv.playerinvmap.get(player) != null) {
                if (Boolean.TRUE.equals(ASInv.playerinvopenmap.get(player))) {
                    e.setCancelled(true);
                    ItemStack stack = e.getCurrentItem();
                    ItemStack stack1 = stack.clone();
                    stack1.setAmount(1);
                    if (stack != null && SellPrices.hasSellPrice(stack1)) {
                        List<ItemStack> items = ASManagement.get(player) != null ? ASManagement.get(player) : new ArrayList<>();

                        // Check if the item is in the AutoSeller slots (to remove it)
                        if (stacklist.contains(e.getSlot())) {
                            if (e.getInventory() == ASInv.playerinvmap.get(player)) {
                                ASManagement.remove(player, stack);
                                player.sendMessage(stack.getItemMeta().getDisplayName() + "§r removed from your AutoSeller!");
                            }
                        }
                        // Else try to add it to AutoSeller if not already present
                        else {
                            boolean alreadyAdded = false;
                            for (ItemStack item : items) {
                                if (item.isSimilar(stack)) {
                                    alreadyAdded = true;
                                    break;
                                }
                            }

                            if (alreadyAdded) {
                                player.sendMessage("You already have this item in your AutoSeller!");
                            } else {
                                items.add(stack1);
                                ASManagement.set(player, items);
                                player.sendMessage(stack.getItemMeta().getDisplayName() + "§r added to your AutoSeller!");
                            }
                        }

                        // Update the inventory after adding or removing
                        ASInv.getInstance().updateInv(player);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player player) {
            // Check if the player has an AutoSeller inventory open
            if (ASInv.playerinvmap.get(player) != null && Boolean.TRUE.equals(ASInv.playerinvopenmap.get(player))) {
                ASInv.playerinvopenmap.put(player, false);
            }
        }
    }
}
