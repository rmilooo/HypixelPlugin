package de.drache.hypixelSkyblock.ScoreBordManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class AutoComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // Check if the command is /setpurse
        if (command.getName().equalsIgnoreCase("setpurse")) {

            // If only the command is typed, suggest player names
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
            // If the first argument is provided (a player), suggest amounts
            else if (args.length == 2) {
                completions.add("1000");
                completions.add("10000000");
                completions.add("1000000000");
                completions.add("1000000000000");
                // Add more preset amounts as needed
            }
        }

        return completions;
    }
}
