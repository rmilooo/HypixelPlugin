package de.drache.hypixelSkyblock.CustomThing;

import de.drache.hypixelSkyblock.ArmorSystem.Armors;
import de.drache.hypixelSkyblock.Items.Items;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class FixSpawn implements Listener {
    @EventHandler
    public void onFixSpawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location =  world.getSpawnLocation();
        location.setYaw(90);
        event.setRespawnLocation(location.add(0,1,0));
    }
    @EventHandler
    public void onPlayerSpawn(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location =  world.getSpawnLocation();
        location.setYaw(90);
        player.teleport(location.add(0,1,0));
    }
    @EventHandler
    public void giveCustomItem(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()){
            player.getInventory().addItem(Items.GolemSword);
            player.getInventory().addItem(Armors.Golem_Boots);
            player.getInventory().addItem(Armors.Golem_Leggings);
            player.getInventory().addItem(Armors.Golem_Chestplate);
        }
    }
    @EventHandler
    public void onPlayerInteract(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player){
            player.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
}
