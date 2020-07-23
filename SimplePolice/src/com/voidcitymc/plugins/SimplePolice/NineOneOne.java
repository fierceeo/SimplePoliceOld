package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;


public class NineOneOne implements CommandExecutor {

    // This method is called, when somebody uses our command

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Worker work = new Worker();

            ArrayList<Player> playerList = new ArrayList<Player>();

            ArrayList<String> policeList = work.listPolice();
            int i = 0;


//online police list
            while (i < policeList.size()) {
                if (Bukkit.getPlayer(UUID.fromString(policeList.get(i))) != null) {
                    playerList.add(Bukkit.getPlayer(UUID.fromString(policeList.get(i))));
                }
                i++;
            }

//send police message

            i = 1;
            String message = "";
            if (args.length > 0) {
                message = "- " + args[0];
            }

            while (i < args.length) {
                message = message + " " + args[i];
                i++;
            }


            i = 0;

            while (i < playerList.size()) {
                    playerList.get(i).sendMessage(Messages.getMessage("NineOneOneMsgPolice", player.getName(), message));
                if (SPPlugin.getInstance().getConfig().getBoolean("ShowCords911")) {
                    playerList.get(i).sendMessage(Messages.getMessage("NineOneOneCordsMessage", player.getName(), String.valueOf(player.getLocation().getBlockX()), String.valueOf(player.getLocation().getBlockY()), String.valueOf(player.getLocation().getBlockZ())));
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
