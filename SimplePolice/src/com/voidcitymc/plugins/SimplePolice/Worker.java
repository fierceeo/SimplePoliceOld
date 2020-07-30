package com.voidcitymc.plugins.SimplePolice;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.zombie_striker.qg.api.QualityArmory;
import me.zombie_striker.qg.guns.Gun;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.*;


public class Worker {

    public ArrayList<String> onlinePoliceList() {
        ArrayList<String> policeList = this.listPolice();
        ArrayList<String> onlinePolice = new ArrayList<>();
        int i = 0;


        //online police list
        while (i < policeList.size()) {
            if (Bukkit.getPlayer(UUID.fromString(policeList.get(i))) != null) {
                onlinePolice.add(policeList.get(i));
                //cycle through online police

            }
            i++;
        }
        return onlinePolice;
    }

    //check if a player has item in hand
    public boolean testForItem(Player p, Material item, String DisplayName) {
        return item != null && p.getInventory().getItemInMainHand().getType() == item;
    }


    public void addPolice(String uuid) {
        if (!alreadyPolice(uuid)) {
            SPPlugin.getInstance().Data.set(uuid, true);
            SPPlugin.getInstance().SaveDataFile();
        }
    }

    public boolean alreadyPolice(String uuid) {
        return SPPlugin.getInstance().Data.getBoolean(uuid);
    }

    public void removePolice(String uuid) {
        if (this.alreadyPolice(uuid)) {
            SPPlugin.getInstance().Data.set(uuid, false);
            SPPlugin.getInstance().SaveDataFile();
        }
    }

    public boolean isLocationSafe(Location loc1) {
        //check if the location is safe to teleport to (air)
        if (loc1.getBlock().getType().equals(Material.AIR)) {
            //we know that the inital location is safe, so we need to check one block up
            //location one block up
            Location loc2 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() + 1, loc1.getZ());
            //2nd block is safe too
            return loc2.getBlock().getType().equals(Material.AIR);
            //loc2 didn't check out
        }
        //because that method didn't pass, we know that the location isn't safe
        return false;
    }

    public Location policeTp(Player player, int MaxValTp) {
        Location LocP = player.getLocation();
        //players x,y,z
        int pX = LocP.getBlockX();
        int pY = LocP.getBlockY();
        int pZ = LocP.getBlockZ();

        //generate a random number from 0 to the farthest /police tp value in config (MaxValTp)
        double drandom1 = Math.random() * MaxValTp;
        double drandom2 = Math.random() * MaxValTp;
        int random1 = (int) Math.round(drandom1);
        int random2 = (int) Math.round(drandom2);

        //should number be negative or positave

        //number from 0 to 1
        int posOrNeg = (int) Math.round(Math.random());
        if (posOrNeg == 1) {
            random1 = random1 - (2 * random1);
        }

        posOrNeg = (int) Math.round(Math.random());
        if (posOrNeg == 1) {
            random2 = random2 - (2 * random2);
        }


        int nX = pX + random1;
        int nY = pY;
        int nZ = pZ + random2;

        Location returnLocYOneDown = new Location(player.getWorld(), nX, nY - 1, nZ);

        while (returnLocYOneDown.getBlock().getType().equals(Material.AIR)) {
            nY = nY - 1;
            returnLocYOneDown = new Location(player.getWorld(), nX, nY - 1, nZ);
        }

        Location returnLoc = returnLocYOneDown;


        //keeps increasing y cord until location is safe
        while (!isLocationSafe(returnLoc)) {
            nY = nY + 1;
            returnLoc = new Location(player.getWorld(), nX, nY, nZ);
        }

        return returnLoc;


    }

    public ArrayList<String> listPolice() {

        Map<String, Object> police = SPPlugin.getInstance().Data.getValues(false);

        ArrayList<String> policeList = new ArrayList<>();

        Object[] objectPoliceArray = police.keySet().toArray();

        String[] policeArrayNotFinal = (String[]) objectPoliceArray;

        int i = 0;

        while (i < policeArrayNotFinal.length) {
            if ((boolean) police.get(policeArrayNotFinal[i])) {
                policeList.add(policeArrayNotFinal[i]);
            }
            i++;
        }

        return policeList;
    }

    public void payPoliceOnArrest(Player player) {
        if (SPPlugin.getInstance().getConfig().getBoolean("PayPoliceOnArrest")) {
            //make sure vault is installed
            if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
                Economy economy = this.setupEconomy();
                if (economy != null) {
                    economy.depositPlayer(player, SPPlugin.getInstance().getConfig().getDouble("MoneyToGiveToPoliceOnArrest"));
                }
                player.sendMessage(Messages.getMessage("MoneyEarnOnArrest", "$" + SPPlugin.getInstance().getConfig().getDouble("MoneyToGiveToPoliceOnArrest")));
            }
        }
    }


    private Economy setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            return rsp.getProvider();
        }
        return null;
    }

    public void takeMoneyOnArrest(Player player) {
        if (SPPlugin.getInstance().getConfig().getBoolean("TakeMoneyOnArrest")) {
            //make sure vault is installed
            if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {

                double percent = SPPlugin.getInstance().getConfig().getDouble("PercentOfMoneyToTake");


                Economy economy = this.setupEconomy();

                if (economy != null) {
                    double moneyLost = economy.getBalance(player) * (percent / 100);
                    player.sendMessage(Messages.getMessage("MoneyLostOnArrest", "$" + moneyLost));
                    economy.withdrawPlayer(player, moneyLost);
                }


            }
        }
    }


    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // Set the name of the item
            meta.setDisplayName(name);

            // Set the lore of the item
            meta.setLore(Arrays.asList(lore));

            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean inSafeArea(Player police) {
        if (SPPlugin.getInstance().getConfig().getBoolean("SafeArea")) {
            //make sure worldguard is installed
            if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {


                //get region "block"
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                //create block
                RegionQuery query = container.createQuery();
                //gets the list of all the region(s) the player is standing in
                ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(police.getLocation()));
                //create Worker

                for (ProtectedRegion region : set) {
                    if (Objects.requireNonNull(SPPlugin.getInstance().getConfig().getList("SafeAreas")).contains(region.getId())) {
                        return true;
                    }
                }

                return false;


            }
            System.out.println("You have the safe area setting in simple police toggled, but you do not have world guard installed!");
            return false;
        }
        return false;
    }

    public Material getBatonMaterial() {
        String mat = SPPlugin.getInstance().Data.getString("BatonMaterialType");
        if (mat != null && Material.getMaterial(mat) != null) {
            return Material.getMaterial(mat);
        } else {
            System.out.print("Error with the baton material");
            return Material.BLAZE_ROD;
        }
    }

    public Material getFriskStickMaterial() {
        String mat = SPPlugin.getInstance().getConfig().getString("FriskStickMaterialType");
        if (mat != null && Material.getMaterial(mat) != null) {
            return Material.getMaterial(mat);
        } else {
            System.out.print("Error with the frisk stick material");
            return Material.BLAZE_ROD;
        }
    }

    public void addToFriskList(ItemStack item) {
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) SPPlugin.getInstance().Controband.getList("Items");
        if (items == null) {
            items = new ArrayList<>();
        }

        ItemStack itemToTest = item.clone();


        //if gun then remove all bullets to 0
        if (Bukkit.getServer().getPluginManager().getPlugin("QualityArmory") != null && QualityArmory.isGun(itemToTest)) {
            ItemMeta meta = itemToTest.getItemMeta();
            if (meta != null) {
                meta.setLore(Gun.getGunLore(QualityArmory.getGun(itemToTest), itemToTest, 0));
                itemToTest.setItemMeta(meta);
            }
        }

        itemToTest.setAmount(1);


        if (!items.contains(itemToTest)) {
            items.add(itemToTest);
        }

        SPPlugin.getInstance().Controband.set("Items", items);
        SPPlugin.getInstance().SaveControbandFile();
    }

    public void removeFromFriskList(ItemStack item) {
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) SPPlugin.getInstance().Controband.getList("Items");
        if (items == null) {
            items = new ArrayList<>();
        }

        ItemStack itemToTest = item.clone();


        //if gun then remove all bullets to 0
        if (Bukkit.getServer().getPluginManager().getPlugin("QualityArmory") != null && QualityArmory.isGun(itemToTest)) {
            ItemMeta meta = itemToTest.getItemMeta();
            if (meta != null) {
                meta.setLore(Gun.getGunLore(QualityArmory.getGun(itemToTest), itemToTest, 0));
                itemToTest.setItemMeta(meta);
            }
        }

        itemToTest.setAmount(1);


        items.remove(itemToTest);


        SPPlugin.getInstance().Controband.set("Items", items);
        SPPlugin.getInstance().SaveControbandFile();
    }

    public List<ItemStack> getFriskList() {
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) SPPlugin.getInstance().Controband.getList("Items");
        if (items == null) {
            items = new ArrayList<>();
        }

        return items;

    }

    public boolean isItemContraband(ItemStack item) {
        ItemStack[] contraband = this.getFriskList().toArray(new ItemStack[0]);
        if (item != null) {
            
            ItemStack itemToTest = item.clone();

            if ((Bukkit.getServer().getPluginManager().getPlugin("QualityArmory") != null) && QualityArmory.isGun(itemToTest)) {
                ItemMeta meta = itemToTest.getItemMeta();
                if (meta != null) {
                    meta.setLore(Gun.getGunLore(QualityArmory.getGun(itemToTest), itemToTest, 0));
                    itemToTest.setItemMeta(meta);
                }
            }

            int i = 0;

            while (i < contraband.length) {
                if (contraband[i].isSimilar(item)) {
                    return true;
                }
                i++;
            }
            return (Bukkit.getServer().getPluginManager().getPlugin("QualityArmory") != null) && QualityArmory.isGun(itemToTest) && SPPlugin.getInstance().getConfig().getBoolean("MarkAllGunsAsContraband");
 
        }

        return false;

    }

    public boolean friskingEnabled() {
        return SPPlugin.getInstance().getConfig().getBoolean("Frisking");

    }

    public void updateConfig() {
        FileConfiguration config = SPPlugin.getInstance().getConfig();
        if (!config.isSet("MaxPoliceTp")) {
            config.set("MaxPoliceTp", 50);
        }
        if (!config.isSet("PayPoliceOnArrest")) {
            config.set("PayPoliceOnArrest", true);
        }
        if (!config.isSet("MoneyToGiveToPoliceOnArrest")) {
            config.set("MoneyToGiveToPoliceOnArrest", 500);
        }
        if (!config.isSet("TakeMoneyOnArrest")) {
            config.set("TakeMoneyOnArrest", false);
        }
        if (!config.isSet("PercentOfMoneyToTake")) {
            config.set("PercentOfMoneyToTake", 20);
        }
        if (!config.isSet("SafeArea")) {
            config.set("SafeArea", false);
        }
        if (!config.isSet("SafeAreas")) {
            ArrayList<String> safeAreas = new ArrayList<>();
            safeAreas.add("examplesafearea1");
            safeAreas.add("examplesafearea2");
            config.set("SafeAreas", safeAreas);
        }
        if (!config.isSet("BatonMaterialType")) {
            config.set("BatonMaterialType", "BLAZE_ROD");
        }
        if (!config.isSet("PercentOfFindingContraband")) {
            config.set("PercentOfFindingContraband", 20);
        }
        if (!config.isSet("FriskStickMaterialType")) {
            config.set("FriskStickMaterialType", "BLAZE_ROD");
        }
        if (!config.isSet("MarkAllGunsAsContraband")) {
            config.set("MarkAllGunsAsContraband", false);
        }
        if (!config.isSet("ShowCords911")) {
            config.set("ShowCords911", false);
        }
        if (!config.isSet("FriskCooldown")) {
            config.set("FriskCooldown", 600);
        }
        if (!config.isSet("CmdFor911")) {
            config.set("CmdFor911n", "911");
        }
        if (!config.isSet("CmdForPolice")) {
            config.set("CmdForPolice", "police");
        }


        /*
        if (!config.isSet("MaxPoliceTp")) {
            config.set("MaxPoliceTp", 50);
        }
        */


    }


}
