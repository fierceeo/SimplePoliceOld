package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import java.util.UUID;
//import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;


public class Police implements CommandExecutor {

// This method is called, when somebody uses our command
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
if (sender instanceof Player) {
Player player = (Player) sender;
worker work = new worker();


//reload config
if (player.hasPermission("police.reload") && args.length > 0) {
	if (args[0].equalsIgnoreCase("reload"))
	Main.getInstance().reloadConfig();
	player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " The config has been reloaded");
}



if ((player.hasPermission("police.unjail") && args.length > 0) || work.alreadyPolice(player.getUniqueId().toString()) ) {
	if (args[0].equalsIgnoreCase("unjail")) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:unjail "+player.getName());
	}
}

if (player.hasPermission("police.add") && args.length > 0) {
// need to check if player has perm ^
if (args[0].equalsIgnoreCase("add")) {
	if (args.length > 1) {
		work.addPolice(Bukkit.getPlayer(args[1]).getUniqueId().toString());
		player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " Added "+args[1]+" as a police officer!");
		} else {
		player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " You need to specify a player!");
	}
	
}

}

//Remove
if (player.hasPermission("police.remove") && args.length > 0) {

if (args[0].equalsIgnoreCase("remove")) {
	if (args.length > 1) {
		work.removePolice(Bukkit.getPlayer(args[1]).getUniqueId().toString());
		player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " Removed "+args[1]+" as a police officer!");
		} else {
		player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " You need to specify a player!");
	}
}

}

//help


	

if (player.hasPermission("police.help") || work.alreadyPolice(player.getUniqueId().toString())) {
if (args.length == 0) {
	player.sendMessage(ChatColor.DARK_AQUA + "[Police]");
	player.sendMessage("Commands:");
	//police tp help
	if (player.hasPermission("police.tp") || work.alreadyPolice(player.getUniqueId().toString())) {
		player.sendMessage("/police tp (player)");
	}
	//police remove help
	if (player.hasPermission("police.remove")) {
		player.sendMessage("/police remove (player)");
	}
	//police add help
	if (player.hasPermission("police.add")) {
		player.sendMessage("/police add (player)");
	}
	
	
	//police unjail
	if (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString())) {
		player.sendMessage("/police unjail (player)");
	}
	
	
	//help info
	player.sendMessage("/police help");
}

if (args.length > 0) {
	if (args[0].equalsIgnoreCase("help")) {
		player.sendMessage(ChatColor.DARK_AQUA+"[Police]");
		player.sendMessage("Commands:");
		//police tp help
		if (player.hasPermission("police.tp") || work.alreadyPolice(player.getUniqueId().toString())) {
			player.sendMessage("/police tp (player)");
		}
		//police remove help
		if (player.hasPermission("police.remove")) {
			player.sendMessage("/police remove (player)");
		}
		//police add help
		if (player.hasPermission("police.add")) {
			player.sendMessage("/police add (player)");
		}
		
		if (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString())) {
			player.sendMessage("/police unjail (player)");
		}
		
		//help info
		player.sendMessage("/police help");
	}
}
}


//police tp
if (player.hasPermission("police.tp") || work.alreadyPolice(player.getUniqueId().toString())) {
	if (args.length > 0) {
	if (args[0].equalsIgnoreCase("tp")) {
		if (args.length > 1) {
			int MaxValTp = Main.getInstance().getConfig().getInt("MaxPoliceTp");
			player.teleport(worker.policeTp(Bukkit.getPlayer(args[1]), MaxValTp));
			player.sendMessage(ChatColor.DARK_AQUA+"[Police]" + ChatColor.WHITE + " You have been teleported");
			Bukkit.getPlayer(args[1]).sendMessage(ChatColor.DARK_AQUA+"The police are comming!");
		} else {
			player.sendMessage(ChatColor.DARK_AQUA+"[Police]" + ChatColor.WHITE + " You need to specify a player!");
		}
		
	}
	}
}

//end help


}
// If the command is used correctly return true
return true;
}
}
