package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collections;

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
                            SPPlugin.getInstance().createMessage();

                            if (!SPPlugin.getInstance().getCommand("police").getAliases().contains(SPPlugin.getInstance().getConfig().getString("CmdForPolice"))) {
                                SPPlugin.getInstance().getCommand("police").setAliases(Collections.singletonList(SPPlugin.getInstance().getConfig().getString("CmdForPolice")));
                            }
                            if (!SPPlugin.getInstance().getCommand("police").getAliases().contains(SPPlugin.getInstance().getConfig().getString("CmdFor911"))) {
                                SPPlugin.getInstance().getCommand("911").setAliases(Collections.singletonList(SPPlugin.getInstance().getConfig().getString("CmdFor911")));
                            }

                            player.sendMessage(Messages.getMessage("AdminConfigReload"));
                        } else if (args[1].equalsIgnoreCase("add")) {
                            if (!work.isItemContraband(player.getInventory().getItemInMainHand())) {
                                if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                    work.addToFriskList(player.getInventory().getItemInMainHand());
                                    player.sendMessage(Messages.getMessage("AdminAddItem"));
                                } else {
                                    player.sendMessage(Messages.getMessage("AdminAddItemFail"));
                                }
                            } else {
                                player.sendMessage(Messages.getMessage("AdminAddItemFailContraband"));
                            }


                        } else if (args[1].equalsIgnoreCase("remove")) {
                            if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                work.removeFromFriskList(player.getInventory().getItemInMainHand());
                                player.sendMessage(Messages.getMessage("AdminRemoveItem"));
                            } else {
                                player.sendMessage(Messages.getMessage("AdminRemoveItemFail"));
                            }
                        }

                    } else {
                        player.sendMessage(Messages.getMessage("PoliceAdminHelpTitle"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp1"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp2"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp3"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp4"));
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
                            player.sendMessage(Messages.getMessage("PoliceOfficerAdd", args[1]));
                        } else {
                            player.sendMessage(Messages.getMessage("PoliceOfficerAddFail", args[1]));
                        }

                    } else {
                        player.sendMessage(Messages.getMessage("PoliceOfficerAddNoPlayer"));
                    }

                }

            }

//Remove
            if (player.hasPermission("police.remove") && args.length > 0) {
                if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length > 1) {
                        if (work.alreadyPolice(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
                            work.removePolice(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                            player.sendMessage(Messages.getMessage("PoliceOfficerRemove", args[1]));
                        } else {
                            player.sendMessage(Messages.getMessage("PoliceOfficerRemoveFail", args[1]));
                        }
                    } else {
                        player.sendMessage(Messages.getMessage("PoliceOfficerRemoveNoPlayer"));
                    }
                }
            }

//help


            if (player.hasPermission("police.help") || work.alreadyPolice(player.getUniqueId().toString())) {
                if (args.length == 0) {
                    player.sendMessage(Messages.getMessage("PoliceHelpTitle"));
                    player.sendMessage(Messages.getMessage("PoliceHelpCommands"));
                    //police tp help
                    if (player.hasPermission("police.tp") || work.alreadyPolice(player.getUniqueId().toString())) {
                        player.sendMessage(Messages.getMessage("PoliceHelpPoliceTp"));
                    }
                    //police remove help
                    if (player.hasPermission("police.remove")) {
                        player.sendMessage(Messages.getMessage("PoliceHelpPoliceRemove"));
                    }
                    //police add help
                    if (player.hasPermission("police.add")) {
                        player.sendMessage(Messages.getMessage("PoliceHelpPoliceAdd"));
                    }


                    //police unjail
                    if (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString())) {
                        player.sendMessage(Messages.getMessage("PoliceHelpPoliceUnjail"));
                    }
                    if (player.hasPermission("police.admin")) {
                        player.sendMessage(Messages.getMessage("PoliceHelpPoliceAdmin"));
                    }


                    //help info
                    player.sendMessage(Messages.getMessage("PoliceHelpPoliceHelp"));
                }

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("help")) {
                        player.sendMessage(Messages.getMessage("PoliceHelpTitle"));
                        player.sendMessage(Messages.getMessage("PoliceHelpCommands"));
                        //police tp help
                        if (player.hasPermission("police.tp") || work.alreadyPolice(player.getUniqueId().toString())) {
                            player.sendMessage(Messages.getMessage("PoliceHelpPoliceTp"));
                        }
                        //police remove help
                        if (player.hasPermission("police.remove")) {
                            player.sendMessage(Messages.getMessage("PoliceHelpPoliceRemove"));
                        }
                        //police add help
                        if (player.hasPermission("police.add")) {
                            player.sendMessage(Messages.getMessage("PoliceHelpPoliceAdd"));
                        }

                        if (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString())) {
                            player.sendMessage(Messages.getMessage("PoliceHelpPoliceUnjail"));
                        }
                        if (player.hasPermission("police.admin")) {
                            player.sendMessage(Messages.getMessage("PoliceHelpPoliceAdmin"));
                        }

                        //help info
                        player.sendMessage(Messages.getMessage("PoliceHelpPoliceHelp"));
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
                            player.sendMessage(Messages.getMessage("PoliceTp"));
                            Bukkit.getPlayer(args[1]).sendMessage(Messages.getMessage("PoliceTpComingMessage"));
                        } else {
                            player.sendMessage(Messages.getMessage("PoliceSpecifyPlayer"));
                        }

                    }
                }
            }
            return true;
        } else {
            sender.sendMessage(Messages.getMessage("OnlyPlayersCanUseCMD"));
            return true;
        }
    }

}
