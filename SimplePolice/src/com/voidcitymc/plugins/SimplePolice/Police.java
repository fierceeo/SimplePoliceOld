package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
                        } else if (args[1].equalsIgnoreCase("setjail")) {
                            HashMap<String, Object> jailLoc = new HashMap<>();
                            Location loc = player.getLocation();
                            jailLoc.put("World", Objects.requireNonNull(loc.getWorld()).getName());
                            jailLoc.put("X", loc.getBlockX());
                            jailLoc.put("Y", loc.getBlockY());
                            jailLoc.put("Z", loc.getBlockZ());
                            ArrayList<Object> arrayL = new ArrayList<>();
                            arrayL.add(jailLoc);
                            SPPlugin.getInstance().getConfig().set("JailLocation", arrayL);
                            SPPlugin.getInstance().saveConfig();
                            player.sendMessage(Messages.getMessage("AdminJailLocSet"));
                        } else if (args[1].equalsIgnoreCase("jail")) {
                            if (args.length > 2) {
                                if (Bukkit.getPlayerExact(args[2]) != null) {
                                    if (args.length > 3) {
                                        Jail.jailPlayer(Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).getUniqueId(), Double.parseDouble(args[3]) * 60);
                                        player.sendMessage(Messages.getMessage("AdminJail", args[2], Jail.timeLeftText(Integer.parseInt(args[3]))));
                                    } else {
                                        Jail.jailPlayer(Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).getUniqueId(), 60.0);
                                        player.sendMessage(Messages.getMessage("AdminJail", args[2], "1 minute"));
                                    }
                                } else {
                                    player.sendMessage(Messages.getMessage("AdminJailPlayerOffline"));
                                }

                            } else {
                                player.sendMessage(Messages.getMessage("AdminJailSpecifyPlayer"));
                            }

                        }

                    } else {
                        player.sendMessage(Messages.getMessage("PoliceAdminHelpTitle"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp1"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp2"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp3"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp4"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp5"));
                        player.sendMessage(Messages.getMessage("PoliceAdminHelp6"));
                    }
                }
            }


//unjail
            if (args.length > 0 && (player.hasPermission("police.unjail") || work.alreadyPolice(player.getUniqueId().toString()))) {
                if (args[0].equalsIgnoreCase("unjail")) {
                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        Jail jailer = new Jail();
                        Jail.unjailPlayer(Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).getUniqueId(), true);
                        player.sendMessage(Messages.getMessage("UnjailPlayer", args[1]));
                    } else {
                        player.sendMessage(Messages.getMessage("ErrorUnjailingPlayerOffline", args[1]));
                    }
                }
            }

            if (player.hasPermission("police.add") && args.length > 0) {
//need to check if player has perm ^
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length > 1) {

                        if (!work.alreadyPolice(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString())) {
                            work.addPolice(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString());
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
                        if (work.alreadyPolice(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString())) {
                            work.removePolice(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString());
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
                            if (Bukkit.getPlayer(args[1]) != null) {
                                int MaxValTp = SPPlugin.getInstance().getConfig().getInt("MaxPoliceTp");
                                player.teleport(work.policeTp(Objects.requireNonNull(Bukkit.getPlayer(args[1])), MaxValTp));
                                player.sendMessage(Messages.getMessage("PoliceTp"));
                                Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(Messages.getMessage("PoliceTpComingMessage"));
                            } else {
                                player.sendMessage(Messages.getMessage("PoliceTpPlayerOffline"));
                            }
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
