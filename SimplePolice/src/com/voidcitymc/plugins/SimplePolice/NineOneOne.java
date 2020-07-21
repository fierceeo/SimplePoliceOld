package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.UUID;


public class NineOneOne implements Listener, CommandExecutor {

    // This method is called, when somebody uses our command

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            this.cmd(((Player) commandSender), strings);
        } else {
            commandSender.sendMessage("Only players can use this command");
        }
        return true;
    }

    public void command(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().split(" ")[0].equalsIgnoreCase(SPPlugin.getInstance().getConfig().getString("CmdFor911"))) {
            String[] cmdMessage = event.getMessage().split(" ");
            int i = 0;
            String[] args = new String[cmdMessage.length - 1];
            while (i < cmdMessage.length) {
                args[i] = cmdMessage[i + 1];
                i++;
            }
            Player player = event.getPlayer();
            this.cmd(player, args);

        }
    }

    public void cmd(Player player, String[] args) {
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
            playerList.get(i).sendMessage(ChatColor.DARK_AQUA + "[911] " + ChatColor.WHITE + player.getName() + " needs help! " + message);
            if (SPPlugin.getInstance().getConfig().getBoolean("ShowCords911")) {
                playerList.get(i).sendMessage(ChatColor.DARK_AQUA + player.getName() + "is located at " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ());
            }

            i++;
        }


        if (playerList.size() == 0) {
            player.sendMessage(ChatColor.DARK_AQUA + "There are no police online, you are on your own!");
        } else {
            player.sendMessage(ChatColor.DARK_AQUA + "The police have gotten your message and will come asap!");
        }


    }

}
