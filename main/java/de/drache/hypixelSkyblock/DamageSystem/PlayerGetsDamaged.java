package de.drache.hypixelSkyblock.DamageSystem;

import de.drache.hypixelSkyblock.ArmorSystem.ArmorStatListener;
import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerGetsDamaged implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (PlayerStats.getInstance().Defense.get(player) != null) {
                event.setDamage(event.getDamage() / PlayerStats.getInstance().Defense.get(player) * 4);
            }
        }
    }
}