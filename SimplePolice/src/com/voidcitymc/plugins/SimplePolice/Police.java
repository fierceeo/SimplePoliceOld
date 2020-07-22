package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Police implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            Worker work = new Worker();


//add controband item
            if (player.hasPermission("police.admin") && args.length > 0) {
                if (args[0].equalsIgnoreCase("admin")) {
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("reload")) {
                            SPPlugin.getInstance().reloadConfig();
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " The config has been reloaded");
                        } else if (args[1].equalsIgnoreCase("add")) {
                            if (player.getInventory().getItemInMainHand() != null && !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                work.addToFriskList(player.getInventory().getItemInMainHand());
                                player.sendMessage(ChatColor.DARK_AQUA + "Added item");
                            } else {
                                player.sendMessage(ChatColor.DARK_AQUA + "Please make sure you have an item in your hand");
                            }

                        } else if (args[1].equalsIgnoreCase("remove")) {
                            if (player.getInventory().getItemInMainHand() != null && !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                work.removeFromFriskList(player.getInventory().getItemInMainHand());
                                player.sendMessage(ChatColor.DARK_AQUA + "Removed item");
                            } else {
                                player.sendMessage(ChatColor.DARK_AQUA + "Please make sure you have an item in your hand");
                            }
                        }

                    } else {
                        player.sendMessage(ChatColor.DARK_AQUA + "[Police]");
                        player.sendMessage("Admin Commands:");
                        player.sendMessage("/police admin reload - reloads config");
                        player.sendMessage("/police admin add - adds item in hand to frisk list");
                        player.sendMessage("/police admin remove - removed item in hand from frisk list");
                    }
                }
            }


//unjail
            if (args.length > 0 && (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString()))) {
                if (args[0].equalsIgnoreCase("unjail")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:unjail " + args[1]);
                }
            }

            if (player.hasPermission("police.add") && args.length > 0) {
//need to check if player has perm ^
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length > 1) {

                        if (!work.alreadyPolice(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
                            work.addPolice(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " Added " + args[1] + " as a police officer!");
                        } else {
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " Could not add " + args[1] + " because they are already a police officer!");
                        }

                    } else {
                        player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " You need to specify a player!");
                    }

                }

            }

//Remove
            if (player.hasPermission("police.remove") && args.length > 0) {
                if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length > 1) {
                        if (work.alreadyPolice(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
                            work.removePolice(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " Removed " + args[1] + " as a police officer!");
                        } else {
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " Could not remove " + args[1] + " because they are not a police officer!");
                        }
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
                    if (player.hasPermission("police.admin")) {
                        player.sendMessage("/police admin");
                    }


                    //help info
                    player.sendMessage("/police help");
                }

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("help")) {
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

                        if (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString())) {
                            player.sendMessage("/police unjail (player)");
                        }
                        if (player.hasPermission("police.admin")) {
                            player.sendMessage("/police admin");
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
                            int MaxValTp = SPPlugin.getInstance().getConfig().getInt("MaxPoliceTp");
                            player.teleport(work.policeTp(Bukkit.getPlayer(args[1]), MaxValTp));
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " You have been teleported");
                            Bukkit.getPlayer(args[1]).sendMessage(ChatColor.DARK_AQUA + "The police are coming!");
                        } else {
                            player.sendMessage(ChatColor.DARK_AQUA + "[Police]" + ChatColor.WHITE + " You need to specify a player!");
                        }

                    }
                }
            }
            return true;
        } else {
            sender.sendMessage("Only players can use this command");
            return true;
        }
    }

}
