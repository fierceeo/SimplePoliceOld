package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements Listener {

    //event handler
    @EventHandler
    public void onTab(TabCompleteEvent event) {
        if (event.getSender() instanceof Player && event.getBuffer().split(" ")[0].equalsIgnoreCase("police")) {
            String[] bufferWithCmdPrefix = event.getBuffer().split(" ");
            String[] buffer = new String[bufferWithCmdPrefix.length-1];
            int i = 0;
            while (i < buffer.length) {
                buffer[i] = bufferWithCmdPrefix[i+1];
                i++;
            }


            List<String> completions = new ArrayList<>();
            Player player = (Player) event.getSender();

            Worker work = new Worker();
            boolean isPolice = work.alreadyPolice(player.getUniqueId().toString());


            if (player.hasPermission("police.admin") && buffer.length == 1) {
                if (buffer[0].equalsIgnoreCase("admin")) {
                    //admin reload, /admin setjail, etc.
                    completions.add("reload");
                    completions.add("add");
                    completions.add("remove");
                    completions.add("setjail");
                    completions.add("jail");
                }
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

}
