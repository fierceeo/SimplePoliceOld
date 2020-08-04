package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabComplete implements Listener {

    //event handler
    @EventHandler
    public void onTab(TabCompleteEvent event) {
        if (event.getSender() instanceof Player && event.getBuffer().split(" ")[0].equalsIgnoreCase("/police")) {
            String[] bufferWithCmdPrefix = event.getBuffer().split(" ");
            String[] buffer = new String[bufferWithCmdPrefix.length-1];
            int i = 0;
            while (i < buffer.length) {
                buffer[i] = bufferWithCmdPrefix[i+1];
                i++;
            }


            ArrayList<String> completions = new ArrayList<>(event.getCompletions());
            Player player = (Player) event.getSender();

            Worker work = new Worker();
            boolean isPolice = work.alreadyPolice(player.getUniqueId().toString());

            if (player.hasPermission("police.admin")) {
                //admin reload, /admin setjail, etc.
                completions = this.cmdCompleatePlayer(completions, "police admin reload", buffer);
                completions = this.cmdCompleatePlayer(completions, "police admin add", buffer);
                completions = this.cmdCompleatePlayer(completions, "police admin remove", buffer);
                completions = this.cmdCompleatePlayer(completions, "police admin setjail", buffer);
                completions = this.cmdCompleatePlayer(completions, "police admin jail", buffer);
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

    public ArrayList<String> cmdCompleatePlayer(ArrayList<String> listToAddTo, String command, String[] buffer) {
        // /police admin s
        // /police admin setjail
        String[] cmd = ("/" + command).split(" ");
        if (!(buffer.length == 0)) {
            int maxLength = Math.min(cmd.length, buffer.length) - 1;
            if (!cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && cmd[maxLength].startsWith(buffer[maxLength])) {
                listToAddTo.add(cmd[maxLength]);
            }
            return listToAddTo;
        } else if (!listToAddTo.contains(cmd[0])) {
            listToAddTo.add(cmd[0]);
        }
        return listToAddTo;
    }
}
