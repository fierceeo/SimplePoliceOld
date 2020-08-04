package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements Listener {

    //event handler
    public void onTab(TabCompleteEvent event) {
        if (event.getSender() instanceof Player) {
            String[] buffer = event.getBuffer().split(" ");
            List<String> compleations = new ArrayList<>();
            Player player = (Player) event.getSender();

            Worker work = new Worker();
            boolean isPolice = work.alreadyPolice(player.getUniqueId().toString());


            if (player.hasPermission("police.admin") && buffer.length == 1) {
                if (buffer[0].equalsIgnoreCase("admin")) {
                    //admin reload, /admin setjail, etc.
                    compleations.add("reload");
                    compleations.add("add");
                    compleations.add("remove");
                    compleations.add("setjail");
                    compleations.add("jail");
                }
            }




            if ((player.hasPermission("police.help") || isPolice) ) {
                if (buffer.length == 0) {

                    if (player.hasPermission("police.tp") || isPolice) {
                        compleations.add("tp");
                    }
                    if (player.hasPermission("police.remove")) {
                        compleations.add("remove");
                    }
                    if (player.hasPermission("police.add")) {
                        compleations.add("add");
                    }
                    if (player.hasPermission("police.unjail") || isPolice) {
                        compleations.add("unjail");
                    }
                    if (player.hasPermission("police.chat") || isPolice) {
                        compleations.add("chat");
                    }
                    if (player.hasPermission("police.admin")) {
                        compleations.add("admin");
                    }
                    compleations.add("help");
                }
            }


            if (player.hasPermission("police.chat") || isPolice) {
                if (buffer.length == 1 && buffer[0].equalsIgnoreCase("chat")) {
                    compleations.add("on");
                    compleations.add("off");
                }
            }

            event.setCompletions(compleations);

        }
    }

    //end event handler

}
