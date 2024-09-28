package de.drache.hypixelSkyblock.Listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerKillEvent implements Listener {
    @EventHandler
    public void PlayerKills(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null){
            Player player = event.getEntity().getKiller();

        }
    }
}
