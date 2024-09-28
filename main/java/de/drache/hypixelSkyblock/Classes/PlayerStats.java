package de.drache.hypixelSkyblock.Classes;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerStats {
    public static PlayerStats instance;
    public PlayerStats() {
        instance = this;
    }

    public static PlayerStats getInstance() {
        if (instance == null) {
            instance = new PlayerStats();
        }
        return instance;
    }

    public HashMap<Player, Double> PlayerMana = new HashMap<>();
    public HashMap<Player, Double> MaxPlayerMana = new HashMap<>();
    public HashMap<Player, Double> PlayerStrength = new HashMap<>();
    public HashMap<Player, Double> Defense = new HashMap<>();
    public HashMap<Player, Double> MagicFind = new HashMap<>();

    public HashMap<Player, Integer> Level = new HashMap<>();
    public HashMap<Player, Integer> EXP = new HashMap<>();
    public HashMap<Player, Long> Coins = new HashMap<>();
}
