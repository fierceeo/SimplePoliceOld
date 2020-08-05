package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;

public class TabComplete implements Listener {

    //event handler
    @EventHandler
    public void onTab(TabCompleteEvent event) {
        if (event.getSender() instanceof Player && event.getBuffer().split(" ")[0].equalsIgnoreCase("/police")) {
            String[] buffer = event.getBuffer().split(" ");

            ArrayList<String> completions = new ArrayList<>();
            Player player = (Player) event.getSender();

            Worker work = new Worker();
            boolean isPolice = work.alreadyPolice(player.getUniqueId().toString());

            if (player.hasPermission("police.admin")) {
                //admin reload, /admin setjail, etc.
                completions = this.cmdCompletePlayer(completions, "police admin reload", buffer, false);
                completions = this.cmdCompletePlayer(completions, "police admin add", buffer, false);
                completions = this.cmdCompletePlayer(completions, "police admin remove", buffer, false);
                completions = this.cmdCompletePlayer(completions, "police admin setjail", buffer, false);
                completions = this.cmdCompletePlayer(completions, "police admin jail", buffer, true);
            }





            if ((player.hasPermission("police.help") || isPolice) ) {
                if (buffer.length == 0) {

                    if (player.hasPermission("police.tp") || isPolice) {
                        completions.add("tp");
                    }
                    if (player.hasPermission("police.remove")) {
                        completions.add("remove");
                    }
                    if (player.hasPermission("police.add")) {
                        completions.add("add");
                    }
                    if (player.hasPermission("police.unjail") || isPolice) {
                        completions.add("unjail");
                    }
                    if (player.hasPermission("police.chat") || isPolice) {
                        completions.add("chat");
                    }
                    if (player.hasPermission("police.admin")) {
                        completions.add("admin");
                    }
                    completions.add("help");
                }
            }


            if (player.hasPermission("police.chat") || isPolice) {
                if (buffer.length == 1 && buffer[0].equalsIgnoreCase("chat")) {
                    completions.add("on");
                    completions.add("off");
                }
            }

            event.setCompletions(completions);

        }
    }

    //end event handler

    public ArrayList<String> cmdCompletePlayer(ArrayList<String> listToAddTo, String command, String[] buffer, boolean addAllOnlinePlayers) {
        // /police admin s
        // /police admin setjail
        String[] cmd = ("/" + command).split(" ");
        int maxLength = Math.min(cmd.length, buffer.length)-1;
        if (!(buffer.length == 0) && !(buffer.length-1 > cmd.length)) {
            if (!cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && cmd[maxLength].startsWith(buffer[maxLength])) {
                listToAddTo.add(cmd[maxLength]);
            } else if (cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && (cmd.length > maxLength+1)) {
                listToAddTo.add(cmd[maxLength+1]);
            }
        }
        if (buffer.length-1 > cmd.length && cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && addAllOnlinePlayers) {
            Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
            int i = 0;
            while (i < onlinePlayers.length) {
                listToAddTo.add(onlinePlayers[i].getName());
                i++;
            }
        }
        return listToAddTo;
    }
}
