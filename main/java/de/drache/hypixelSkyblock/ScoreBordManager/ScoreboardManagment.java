package de.drache.hypixelSkyblock.ScoreBordManager;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardManagment {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private final JavaPlugin plugin;

    public ScoreboardManagment(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Updates the scoreboard for a single player
    public static void updateScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        // Create objective with title
        Objective objective = board.registerNewObjective("playerInfo", "dummy", "§e§lDragonSMP §b§lSOLO");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int scoreCounter = 9; // Start with score 1

        // Add the various sections of the scoreboard
        scoreCounter = addDate(objective, scoreCounter);
        scoreCounter = addEmptyLine(objective, scoreCounter);
        scoreCounter = addSeasonalReference(objective, scoreCounter);
        scoreCounter = addCurrentTime(objective, scoreCounter);
        scoreCounter = addLocation(objective, scoreCounter);
        scoreCounter = addEmptyLine(objective, scoreCounter);
        scoreCounter = addPlayerPurse(objective, player, scoreCounter);
        scoreCounter = addEmptyLine(objective, scoreCounter);
        addServerIP(objective, scoreCounter);

        // Assign the final scoreboard to the player
        player.setScoreboard(board);
    }

    // Add the current date
    private static int addDate(Objective objective, int score) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(now);
        return addScore(objective, "§7" + date, score);
    }

    // Add the current time
    private static int addCurrentTime(Objective objective, int score) {
        Date now = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");
        String time = timeFormat.format(now).toLowerCase(); // Format to lowercase
        return addScore(objective, "§e☀ §7" + time, score);
    }

    // Add the player's purse (coins)
    private static int addPlayerPurse(Objective objective, Player player, int score) {
        String purse = "§7Purse: §6" + formatCoins(getCoins(player));
        return addScore(objective, purse, score);
    }

    // Add the seasonal reference (hardcoded example)
    private static int addSeasonalReference(Objective objective, int score) {
        String season = "§fEarly Summer 23rd";
        return addScore(objective, season, score);
    }

    // Add the server IP
    private static void addServerIP(Objective objective, int score) {
        addScore(objective, "§7dragonsmp.online", score);
    }

    // Add the player’s location (example: End)
    private static int addLocation(Objective objective, int score) {
        String location = "⏣  §dEnd";
        return addScore(objective, location, score);
    }

    // Utility to add an empty line for spacing
    private static int addEmptyLine(Objective objective, int score) {
        return addScore(objective, " ".repeat(score), score);
    }

    // Utility function to add a score entry
    private static int addScore(Objective objective, String text, int score) {
        Score scoreEntry = objective.getScore(text);
        scoreEntry.setScore(score);
        return score - 1; // Increment the score for the next entry
    }

    // Dummy method to represent getting player stats (Coins)
    private static long getCoins(Player player) {
        // Replace this with your actual logic to get the player's coins
        if (PlayerStats.getInstance().Coins.containsKey(player)){
            return PlayerStats.getInstance().Coins.get(player);
        }
        return 0;
    }

    // Format the coin amount into a more readable format
    private static String formatCoins(long amount) {
        if (amount < 1000) {
            return String.valueOf(amount);
        } else if (amount < 1_000_000) {
            return decimalFormat.format(amount / 1_000.0) + "k"; // Format thousands
        } else if (amount < 1_000_000_000) {
            return decimalFormat.format(amount / 1_000_000.0) + "m"; // Format millions
        } else {
            return decimalFormat.format(amount / 1_000_000_000.0) + "b"; // Format billions
        }
    }

    // Update the scoreboard for all online players
    public static void updateScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }
}
