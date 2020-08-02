package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class GUI implements Listener {
    @EventHandler
    public void gui(InventoryClickEvent event) {

        final Player player = (Player) event.getWhoClicked();
        if (!player.getOpenInventory().getTitle().equalsIgnoreCase("Jail Time")) return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        int i = event.getRawSlot();

        CustomJailGuiItem customGuiItem = new CustomJailGuiItem();


        if (customGuiItem.isItemNull(i)) {
            return;
        }

        double jailTime = customGuiItem.getJailTime(i);


        String jailedPlayer = SPPlugin.lastArrest.get(player.getName());
        player.sendMessage(Messages.getMessage("JailTimePoliceMSG", jailedPlayer, String.valueOf(jailTime)));
        Bukkit.getPlayerExact(jailedPlayer).sendMessage(Messages.getMessage("JailTimeMSG", String.valueOf(jailTime)));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:jail " + jailedPlayer + " jail1 " + jailTime + "m");
        //need to find jailed player


    }

    @EventHandler
    public void preventShiftgui(InventoryMoveItemEvent event) {
        if (!((Player) Objects.requireNonNull(event.getSource().getHolder())).getOpenInventory().getTitle().equalsIgnoreCase("Jail Time"))
            return;
        event.setCancelled(true);
    }


    public void openInventory(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SPPlugin.getInstance(), new Runnable() {
            public void run() {
                player.openInventory(createGUI(player));
            }
        }, 1L);

    }


    public Inventory createGUI(Player player) {

        Worker work = new Worker();

        Inventory inv = Bukkit.createInventory(null, 9, "Jail Time");

        ArrayList<Material> matList = new ArrayList<>();
        int i = 1;

        CustomJailGuiItem customGuiItem = new CustomJailGuiItem();
        //itemListFromConfig.get(i) gets a list for the item
        // (String)((HashMap<String, Object>) itemListFromConfig.get(i)).get(path) where path is material, jailtime, etc. returns info of item
        while (!customGuiItem.isItemNull(i)) {
            matList.add(customGuiItem.getMaterial(i));
            i++;
        }
        i--;


        boolean hasPerm = false;
        int cnt = 0;
        while (cnt < i) {
            if (!matList.get(cnt + 1).equals(Material.AIR) || matList.get(cnt + 1) != null) {
                String perm = customGuiItem.getPerm(i);

                if (perm != null) {
                    if (player.hasPermission(perm)) {
                        hasPerm = true;
                    }
                } else {
                    hasPerm = true;
                }
                if (hasPerm) {
                    double jailTime = customGuiItem.getJailTime(i);
                    inv.setItem(cnt + 1, work.createGuiItem(matList.get(cnt), "§f" + jailTime + "M", "§bClick here to jail the player for " + jailTime + " minutes", ""));
                }
            }
            cnt++;
        }


        return inv;
    }


}    
