package de.drache.hypixelSkyblock;

import de.drache.hypixelSkyblock.ArmorSystem.ArmorStatListener;
import de.drache.hypixelSkyblock.ArmorSystem.Armors;
import de.drache.hypixelSkyblock.BasicSystemManger.PlayerBreakBlock;
import de.drache.hypixelSkyblock.Classes.FileManagement;
import de.drache.hypixelSkyblock.Classes.ItemCheck;
import de.drache.hypixelSkyblock.Classes.ManaRegen;
import de.drache.hypixelSkyblock.Classes.ZealotSpawnCheck;
import de.drache.hypixelSkyblock.Commands.ItemGui;
import de.drache.hypixelSkyblock.Commands.addZealotSpawn;
import de.drache.hypixelSkyblock.Commands.spawnKudra;
import de.drache.hypixelSkyblock.CustomCrafting.CraftCommand;
import de.drache.hypixelSkyblock.CustomCrafting.CraftingInventoryClickEvent;
import de.drache.hypixelSkyblock.CustomThing.FixSpawn;
import de.drache.hypixelSkyblock.DamageSystem.DamageHoloGram;
import de.drache.hypixelSkyblock.DamageSystem.PlayerGetsDamaged;
import de.drache.hypixelSkyblock.Items.AutoSeller.ASInteractListener;
import de.drache.hypixelSkyblock.Items.AutoSeller.AutoSellFunction;
import de.drache.hypixelSkyblock.Items.Items;
import de.drache.hypixelSkyblock.Items.Listeners.*;
import de.drache.hypixelSkyblock.Kudra.KudraManager;
import de.drache.hypixelSkyblock.Listener.EnderDragonCustom;
import de.drache.hypixelSkyblock.Listener.SummonEnderDragon;
import de.drache.hypixelSkyblock.Listener.ZealotDieEvent;
import de.drache.hypixelSkyblock.RNGSystem.CreateNewRNG;
import de.drache.hypixelSkyblock.ScoreBordManager.AutoComplete;
import de.drache.hypixelSkyblock.ScoreBordManager.ScoreboardManagment;
import de.drache.hypixelSkyblock.ScoreBordManager.Update;
import de.drache.hypixelSkyblock.ScoreBordManager.setPurse;
import de.drache.hypixelSkyblock.Sell.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class main extends JavaPlugin {
    public static main instance;

    @Override
    public void onEnable() {
        instance = this;
        Update.startRepeatingTask();
        ZealotSpawnCheck.startRepeatingTask();
        ZealotSpawnCheck.startRepeatingTask2();
        ItemCheck.startRepeatingTask();
        FileManagement.getInstance().initInventory();
        SellPrices.initialize();
        PlayerStatsFile.getInstance().initFile();
        Bukkit.getPluginManager().registerEvents(new Items(), this);
        Bukkit.getPluginManager().registerEvents(new CreateNewRNG(), this);
        Bukkit.getPluginManager().registerEvents(new DamageHoloGram(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorStatListener(), this);
        Bukkit.getPluginManager().registerEvents(new Armors(), this);
        Bukkit.getPluginManager().registerEvents(new MidasStaffListener(), this);
        Bukkit.getPluginManager().registerEvents(new ZealotDieEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ShadowFurryListener(), this);
        Bukkit.getPluginManager().registerEvents(new SummonEnderDragon(), this);
        Bukkit.getPluginManager().registerEvents(new JujuShotbowListener(), this);
        Bukkit.getPluginManager().registerEvents(new HyperionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TerminatorShotbowListener(), this);
        Bukkit.getPluginManager().registerEvents(new AdminHypeListener(), this);
        Bukkit.getPluginManager().registerEvents(new KudraManager(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerGetsDamaged(), this);
        Bukkit.getPluginManager().registerEvents(new EntityGetHit(), this);
        Bukkit.getPluginManager().registerEvents(new SellGui(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSaveLoadPurse(), this);
        Bukkit.getPluginManager().registerEvents(new FixSpawn(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBreakBlock(), this);
        Bukkit.getPluginManager().registerEvents(new ItemShopGui(), this);
        Bukkit.getPluginManager().registerEvents(new ASInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSellFunction(), this);
        Bukkit.getPluginManager().registerEvents(new EnderDragonCustom(), this);
        Bukkit.getPluginManager().registerEvents(new CraftingInventoryClickEvent(), this);
        Objects.requireNonNull(this.getCommand("skyblockitems")).setExecutor(new ItemGui());
        Objects.requireNonNull(this.getCommand("addzealotspawn")).setExecutor(new addZealotSpawn());
        Objects.requireNonNull(this.getCommand("spawnkudra")).setExecutor(new spawnKudra());
        Objects.requireNonNull(this.getCommand("sell")).setExecutor(new SellCommand());
        Objects.requireNonNull(this.getCommand("setpurse")).setExecutor(new setPurse());
        Objects.requireNonNull(this.getCommand("setpurse")).setTabCompleter(new AutoComplete());
        Objects.requireNonNull(this.getCommand("itemshop")).setExecutor(new BuyCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
        ManaRegen.startRepeatingTask();
        ScoreboardManagment.updateScoreboards();
        for (Player p : Bukkit.getOnlinePlayers()) {
            ArmorStatListener.getInstance().updateArmorStats(p);
            PlayerSaveLoadPurse.load(p);
        }
    }

    @Override
    public void onDisable() {
        FileManagement.getInstance().saveFile();
        PlayerStatsFile.getInstance().saveFile();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerSaveLoadPurse.save(p);
        }
    }

    public void log(String message) {
        Bukkit.getLogger().info(message);
    }
}

