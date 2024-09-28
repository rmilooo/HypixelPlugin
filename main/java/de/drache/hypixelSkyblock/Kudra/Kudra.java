package de.drache.hypixelSkyblock.Kudra;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;

import java.util.List;
import java.util.Random;

public class Kudra implements KudraInterface {
    private static Kudra instance;
    private double health;
    private Location location;
    private Slime body;
    private List<LivingEntity> targets;  // Multiple targets
    private List<Slime> summons;
    private KudraPhase phase;

    public Kudra() {
        instance = this;
    }

    public static Kudra getInstance() {
        if (instance == null) {
            instance = new Kudra();
        }
        return instance;
    }

    @Override
    public void setTargets(List<LivingEntity> entities) {
        this.targets = entities;
    }

    @Override
    public List<LivingEntity> getTargets() {
        return this.targets;
    }

    @Override
    public void teleport(Location location) {
        this.location = location;
        if (this.body != null) {
            this.body.teleport(location);
        }
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
        if (this.getMainBody() != null) {
            this.getMainBody().setMaxHealth(health);
            this.getMainBody().setHealth(health);
        }
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void update() {
        if (this.getMainBody() != null && this.getTargets() != null && !this.getTargets().isEmpty()) {
            Slime slime = this.getMainBody();
            Random rand = new Random();
            Location slimeLocation = slime.getLocation();

            // Find the closest target within 20 blocks
            LivingEntity closestTarget = null;
            double closestDistance = 20;

            for (LivingEntity target : this.getTargets()) {
                double distance = target.getLocation().distance(slimeLocation);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestTarget = target;
                }
            }

            if (closestTarget != null) {
                // Rotate towards the closest target
                Location targetLocation = closestTarget.getLocation();
                double deltaX = targetLocation.getX() - slimeLocation.getX();
                double deltaZ = targetLocation.getZ() - slimeLocation.getZ();
                float yaw = (float) Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90;
                slimeLocation.setYaw(yaw);
                slime.teleport(slimeLocation);

                // Strike lightning and damage all targets within 20 blocks
                for (LivingEntity target : this.getTargets()) {
                    double distance = target.getLocation().distance(slimeLocation);
                    if (distance <= 20) {
                        if (rand.nextInt(50) == 1) {  // 1 in 10 chance
                            target.getWorld().strikeLightning(target.getLocation());
                            target.damage(((target.getMaxHealth() * 0.2) + rand.nextDouble(100)) / 2, slime);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setMainBody(Slime slime) {
        this.body = slime;
    }

    public Slime getMainBody() {
        return this.body;
    }

    @Override
    public void setSummons(List<Slime> summons) {
        this.summons = summons;
    }

    @Override
    public List<Slime> getSummons() {
        return this.summons;
    }

    @Override
    public void setPhase(KudraPhase phase) {
        this.phase = phase;
    }

    @Override
    public KudraPhase getPhase() {
        return this.phase;
    }
}
