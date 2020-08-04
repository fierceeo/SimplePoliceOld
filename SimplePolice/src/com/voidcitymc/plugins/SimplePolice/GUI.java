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
import java.util.UUID;

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

        double jailTime = customGuiItem.getJailTime(i + 1);


        String jailedPlayerUUID = SPPlugin.lastArrest.get(player.getName()).split(",")[0];
        String jailedPlayer = SPPlugin.lastArrest.get(player.getName()).split(",")[1];
        player.sendMessage(Messages.getMessage("JailTimePoliceMSG", jailedPlayer, String.valueOf(jailTime)));
        if (Bukkit.getPlayer(jailedPlayerUUID) != null) {
            Objects.requireNonNull(Bukkit.getPlayer(jailedPlayerUUID)).sendMessage(Messages.getMessage("JailTimeMSG", String.valueOf(jailTime)));
        }
        Jail.jailPlayer(UUID.fromString(jailedPlayerUUID), jailTime * 60);

    }

    @EventHandler
    public void preventShiftgui(InventoryMoveItemEvent event) {
        if (!(event.getSource().getHolder() instanceof Player)) return;
        if (!((Player) event.getSource().getHolder()).getOpenInventory().getTitle().equalsIgnoreCase("Jail Time")) return;
        event.setCancelled(true);
    }


    public void openInventory(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SPPlugin.getInstance(), () -> player.openInventory(createGUI(player)), 1L);

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
        double jailTime;
        while (cnt < i) {
            if (!matList.get(cnt).equals(Material.AIR) || matList.get(cnt) != null) {
                String perm = customGuiItem.getPerm(i);

                if (perm != null) {
                    if (player.hasPermission(perm)) {
                        hasPerm = true;
                    }
                } else {
                    hasPerm = true;
                }
                if (hasPerm) {
                    jailTime = customGuiItem.getJailTime(cnt + 1);
                    //to-do translate this
                    inv.setItem(cnt, work.createGuiItem(matList.get(cnt), "§f" + jailTime + "M", "§bClick here to jail the player for " + jailTime + " minutes", ""));
                }
            }
            cnt++;
        }


        return inv;
    }


}    
