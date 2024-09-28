package de.drache.hypixelSkyblock.Kudra;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;

import java.util.List;

public interface KudraInterface {
    void setTargets(List<LivingEntity> entities);  // Changed to handle multiple targets

    List<LivingEntity> getTargets();  // Changed to return a list of targets

    void teleport(Location location);

    void setHealth(double health);
    double getHealth();

    void update();

    void setMainBody(Slime slime);
    Slime getMainBody();

    void setSummons(List<Slime> summons);
    List<Slime> getSummons();

    void setPhase(KudraPhase phase);
    KudraPhase getPhase();
}
