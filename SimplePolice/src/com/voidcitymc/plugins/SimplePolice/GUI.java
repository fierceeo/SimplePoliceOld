package com.voidcitymc.plugins.SimplePolice;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class GUI implements Listener {
	@EventHandler
    public void gui (InventoryClickEvent event) {

		final Player player = (Player) event.getWhoClicked();
		final Inventory inv = createGUI(player);
		 if (!Arrays.equals(event.getInventory().getContents(), inv.getContents())) return;
        
        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;


        int jailTime = 0;
        
        if (event.getRawSlot() == 2) {
        	jailTime = 1;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 3) {
        	jailTime = 2;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 4) {
        	jailTime = 5;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 5) {
        	jailTime = 10;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 6) {
        	jailTime = 15;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 7) {
        	jailTime = 30;
        	event.getWhoClicked().closeInventory();
        }

        String jailedPlayer = Main.lastArrest.get(player.getName());
        player.sendMessage(ChatColor.DARK_AQUA+"[Police]"+ChatColor.WHITE+"You jailed "+jailedPlayer+" for "+jailTime+" minutes");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:jail "+jailedPlayer+" jail1 "+jailTime+"m");
        //need to find jailed player
                
        
}
	
    public void preventShiftgui (InventoryMoveItemEvent event) {
        if (!Arrays.equals(event.getSource().getContents(), createGUI((Player)event.getSource().getHolder()).getContents())) return;
        event.setCancelled(true);
    }

    
    public void openInventory(final Player player) {
        player.openInventory(createGUI(player));
    }
    
    
    public Inventory createGUI(Player player) {
        
    	worker work = new worker();
            
        Inventory inv = Bukkit.createInventory(null, 9, "Jail Time");
        
        inv.setItem(2, work.createGuiItem(Material.RED_TERRACOTTA, "§f1M", "§bClick here to jail the player for 1 minute", ""));
        inv.setItem(3, work.createGuiItem(Material.ORANGE_TERRACOTTA, "§f2M", "§bClick here to jail the player for 1 minutes", ""));
        inv.setItem(4, work.createGuiItem(Material.YELLOW_TERRACOTTA, "§f5M", "§bClick here to jail the player for 1 minutes", ""));
        inv.setItem(5, work.createGuiItem(Material.LIME_TERRACOTTA, "§f10M", "§bClick here to jail the player for 1 minutes", ""));
        inv.setItem(6, work.createGuiItem(Material.LIGHT_BLUE_TERRACOTTA, "§f15M", "§bClick here to jail the player for 1 minutes", ""));
        inv.setItem(7, work.createGuiItem(Material.BLUE_TERRACOTTA, "§f30M", "§bClick here to jail the player for 1 minutes", ""));
        
        return inv;
    }
	
	
}    
