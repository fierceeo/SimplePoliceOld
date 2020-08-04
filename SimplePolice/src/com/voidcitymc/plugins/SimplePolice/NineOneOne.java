package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class NineOneOne implements CommandExecutor {

    // This method is called, when somebody uses our command

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Worker work = new Worker();

            ArrayList<String> playerList = work.onlinePoliceList();

            int i = 1;
            StringBuilder message = new StringBuilder();
            if (args.length > 0) {
                message = new StringBuilder("- " + args[0]);
            }

            while (i < args.length) {
                message.append(" ").append(args[i]);
                i++;
            }


            i = 0;

            while (i < playerList.size()) {
                Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(playerList.get(i)))).sendMessage(Messages.getMessage("NineOneOneMsgPolice", player.getName(), message.toString()));
                if (SPPlugin.getInstance().getConfig().getBoolean("ShowCords911")) {
                    Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(playerList.get(i)))).sendMessage(Messages.getMessage("NineOneOneCordsMessage", player.getName(), String.valueOf(player.getLocation().getBlockX()), String.valueOf(player.getLocation().getBlockY()), String.valueOf(player.getLocation().getBlockZ())));
                }

                i++;
            }


            if (playerList.size() == 0) {
                player.sendMessage(Messages.getMessage("NineOneOneNoPolice"));
            } else {
                player.sendMessage(Messages.getMessage("NineOneOnePoliceOnline"));
            }

            return true;
        } else {
            sender.sendMessage(Messages.getMessage("NineOneOneOnlyPlayersCanRunCMD"));
            return true;
        }
    }

}
