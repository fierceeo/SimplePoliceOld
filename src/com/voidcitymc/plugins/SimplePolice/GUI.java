package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class GUI implements Listener {
	@EventHandler
    public void gui (InventoryClickEvent event) {

	final Player player = (Player) event.getWhoClicked();
	if (!player.getOpenInventory().getTitle().equalsIgnoreCase("Jail Time")) return;
        
        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;


        int jailTime = 0;
        
        if (event.getRawSlot() == 2) {
        	jailTime = 4;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 3) {
        	jailTime = 8;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 4) {
        	jailTime = 10;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 5) {
        	jailTime = 12;
        	event.getWhoClicked().closeInventory();
        }
        if (event.getRawSlot() == 6) {
        	jailTime = 15;
        	event.getWhoClicked().closeInventory();
        }

        String jailedPlayer = Main.lastArrest.get(player.getName());
        player.sendMessage(ChatColor.DARK_AQUA+"[Police]"+ChatColor.WHITE+" You jailed "+jailedPlayer+" for "+jailTime+" minutes");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:jail "+jailedPlayer+" jail1 "+jailTime+"m");
        //need to find jailed player
                
        
}
	
    public void preventShiftgui (InventoryMoveItemEvent event) {
        if (!((Player)event.getSource().getHolder()).getOpenInventory().getTitle().equalsIgnoreCase("Jail Time")) return;
        event.setCancelled(true);
    }

    
    public void openInventory(final Player player) {
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		public void run() {
			player.openInventory(createGUI(player));
		}
        }, 1L);
	    
    }
    
    
    public Inventory createGUI(Player player) {
        
    	worker work = new worker();
            
        Inventory inv = Bukkit.createInventory(null, 9, "Jail Time");
        
        inv.setItem(2, work.createGuiItem(Material.RED_TERRACOTTA, "§f4M", "§bClick here to jail the player for 4 minutes", ""));
        inv.setItem(3, work.createGuiItem(Material.ORANGE_TERRACOTTA, "§f8M", "§bClick here to jail the player for 8 minutes", ""));
        inv.setItem(4, work.createGuiItem(Material.YELLOW_TERRACOTTA, "§f10M", "§bClick here to jail the player for 10 minutes", ""));
        inv.setItem(5, work.createGuiItem(Material.LIME_TERRACOTTA, "§f12M", "§bClick here to jail the player for 12 minutes", ""));
        inv.setItem(6, work.createGuiItem(Material.LIGHT_BLUE_TERRACOTTA, "§f15M", "§bClick here to jail the player for 15 minutes", ""));
        
        return inv;
    }
	
	
}    
