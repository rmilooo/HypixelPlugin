package de.drache.hypixelSkyblock.ScoreBordManager;

import de.drache.hypixelSkyblock.Classes.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class setPurse implements CommandExecutor {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Check if the command sender is an OP
        if (commandSender.isOp()  || commandSender.hasPermission("ci.setpurse")) {

            // Check if the correct number of arguments is provided
            if (args.length != 2) {
                commandSender.sendMessage("§cUsage: /setpurse <player> <amount>");
                return true;
            }

            // Get the player to set the purse for
            Player targetPlayer = Bukkit.getPlayer(args[0]);

            // Check if the target player is online
            if (targetPlayer == null) {
                commandSender.sendMessage("§cPlayer not found or is not online.");
                return true;
            }

            // Try to parse the amount from the arguments
            try {
                long amount = Long.parseLong(args[1]);

                // Check for negative values
                if (amount < 0) {
                    commandSender.sendMessage("§cAmount cannot be negative.");
                    return true;
                }

                // Set the target player's purse
                PlayerStats.getInstance().Coins.put(targetPlayer, amount);
                targetPlayer.sendMessage("§aYour purse has been set to " + formatCoins(amount) + "!");
                commandSender.sendMessage("§aYou have set " + targetPlayer.getName() + "'s purse to " + formatCoins(amount) + "!");
                return true;
            } catch (NumberFormatException e) {
                commandSender.sendMessage("§cInvalid amount. Please enter a valid number.");
                return true;
            }
        }else {
            commandSender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }
    }

    // Format the coin amount into a more readable format
    private String formatCoins(long amount) {
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
}
