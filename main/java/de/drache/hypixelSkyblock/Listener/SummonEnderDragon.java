package de.drache.hypixelSkyblock.Listener;

import de.drache.hypixelSkyblock.Classes.ZealotSpawnList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SummonEnderDragon implements Listener {
    private int totalEyesPlaced = 0;
    private final List<Location> frames = new ArrayList<>();
    private static SummonEnderDragon instance;

    public SummonEnderDragon() {
        instance = this;
    }

    public static SummonEnderDragon getInstance() {
        if (instance == null) {
            instance = new SummonEnderDragon();
        }
        return instance;
    }

    // Clears all the eyes placed in portal frames and resets the count
    public void clearAllEyes() {
        World world = Bukkit.getWorld("world");  // Ensure you use the correct world
        if (world == null) return;

        totalEyesPlaced = 0;

        for (Location frame : frames) {
            Block block = world.getBlockAt(frame);
            if (block.getType() == Material.END_PORTAL_FRAME) {
                BlockData blockData = block.getBlockData();
                if (blockData instanceof EndPortalFrame) {
                    EndPortalFrame endPortalFrame = (EndPortalFrame) blockData;
                    endPortalFrame.setEye(false);
                    block.setBlockData(endPortalFrame);
                }
            }
        }
        frames.clear();
    }

    // Event handler to detect player interaction with portal frames
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick() || event.getClickedBlock() == null) {
            return;
        }

        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        // Check if the player clicked on an End Portal Frame
        if (block.getType() == Material.END_PORTAL_FRAME && player.getItemInHand().getType() == Material.ENDER_EYE) {
            BlockData blockData = block.getBlockData();

            if (blockData instanceof EndPortalFrame) {
                EndPortalFrame endPortalFrame = (EndPortalFrame) blockData;

                // If the eye is already placed, do nothing
                if (endPortalFrame.hasEye()) {
                    player.sendMessage("This frame already contains an eye.");
                    return;
                }

                // Register the frame and place the eye
                frames.add(block.getLocation());
                totalEyesPlaced++;

                // Remove one Ender Eye from the player's hand
                ItemStack item = player.getItemInHand();
                item.setAmount(item.getAmount() - 1);

                endPortalFrame.setEye(true);
                block.setBlockData(endPortalFrame);

                // Inform all players about the eye placement
                String message = player.getName() + " has placed an eye (" + totalEyesPlaced + "/8)";
                Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));

                // If all 8 eyes are placed, summon the dragon
                if (totalEyesPlaced >= 8) {
                    summonEnderDragon(block.getWorld(), block.getLocation());
                }
            }
        }
    }

    // Summon the Ender Dragon at a specific location
    private void summonEnderDragon(World world, Location location) {
        totalEyesPlaced = 0;  // Reset the count after summoning
        EnderDragon dragon = (EnderDragon) world.spawnEntity(location.add(0, 50, 0), EntityType.ENDER_DRAGON);
        EnderDragonCustom.Init(dragon, dragon.getLocation());  // Initialize the custom Ender Dragon
    }
}
