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

    SPPlugin plugin;

    public Worker(SPPlugin plugin) {
        this.plugin = plugin;
    }

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
            plugin.Data.set(uuid, true);
            plugin.SaveDataFile();
        }
    }

    public boolean alreadyPolice(String uuid) {
        return plugin.Data.getBoolean(uuid);
    }

    public void removePolice(String uuid) {
        if (this.alreadyPolice(uuid)) {
            plugin.Data.set(uuid, false);
            plugin.SaveDataFile();
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

        Map<String, Object> police = plugin.Data.getValues(false);

        ArrayList<String> policeList = new ArrayList<>();

        Object[] objectPoliceArray = police.keySet().toArray();

        int i = 0;

        while (i < objectPoliceArray.length) {
            if ((boolean) police.get(objectPoliceArray[i])) {
                policeList.add((String) objectPoliceArray[i]);
            }
            i++;
        }

        return policeList;
    }

    public void payPoliceOnArrest(Player player) {
        if (plugin.getConfig().getBoolean("PayPoliceOnArrest")) {
            //make sure vault is installed
            if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
                Economy economy = this.setupEconomy();
                if (economy != null) {
                    economy.depositPlayer(player, plugin.getConfig().getDouble("MoneyToGiveToPoliceOnArrest"));
                }
                player.sendMessage(Messages.getMessage("MoneyEarnOnArrest", "$" + plugin.getConfig().getDouble("MoneyToGiveToPoliceOnArrest")));
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
        if (plugin.getConfig().getBoolean("TakeMoneyOnArrest")) {
            //make sure vault is installed
            if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {

                double percent = plugin.getConfig().getDouble("PercentOfMoneyToTake");


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
        if (plugin.getConfig().getBoolean("SafeArea")) {
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
                    if (Objects.requireNonNull(plugin.getConfig().getList("SafeAreas")).contains(region.getId())) {
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
        String mat = plugin.Data.getString("BatonMaterialType");
        if (mat != null && Material.getMaterial(mat) != null) {
            return Material.getMaterial(mat);
        } else {
            System.out.print("Error with the baton material");
            return Material.BLAZE_ROD;
        }
    }

    public Material getFriskStickMaterial() {
        String mat = plugin.getConfig().getString("FriskStickMaterialType");
        if (mat != null && Material.getMaterial(mat) != null) {
            return Material.getMaterial(mat);
        } else {
            System.out.print("Error with the frisk stick material");
            return Material.BLAZE_ROD;
        }
    }

    public void addToFriskList(ItemStack item) {
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) plugin.Controband.getList("Items");
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

        plugin.Controband.set("Items", items);
        plugin.SaveControbandFile();
    }

    public void removeFromFriskList(ItemStack item) {
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) plugin.Controband.getList("Items");
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


        plugin.Controband.set("Items", items);
        plugin.SaveControbandFile();
    }

    public List<ItemStack> getFriskList() {
        @SuppressWarnings("unchecked")
        List<ItemStack> items = (List<ItemStack>) plugin.Controband.getList("Items");
        if (items == null) {
            items = new ArrayList<>();
        }

        return items;

    }

    public boolean isItemContraband(ItemStack item) {
        ItemStack[] contraband = this.getFriskList().toArray(new ItemStack[0]);
        if (item != null) {

            ItemStack itemToTest = item.clone();

            boolean qaInstalled = (Bukkit.getServer().getPluginManager().getPlugin("QualityArmory") != null);

            if (qaInstalled && QualityArmory.isGun(itemToTest) && plugin.getConfig().getBoolean("MarkAllGunsAsContraband")) {
                return true;
            }

            if (qaInstalled && QualityArmory.isGun(itemToTest)) {
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
                if (qaInstalled && QualityArmory.isGun(itemToTest) && Objects.equals(Objects.requireNonNull(itemToTest.getItemMeta()).getLore(), Objects.requireNonNull(contraband[i].getItemMeta()).getLore())) {
                    return true;
                }
                i++;
            }


        }

        return false;

    }

    public boolean friskingEnabled() {
        return plugin.getConfig().getBoolean("Frisking");

    }

    public ArrayList<String> cmdCompletePlayer(ArrayList<String> listToAddTo, String command, String[] buffer, boolean addAllOnlinePlayers) {
        String[] cmd = ("/" + command).split(" ");
        int maxLength = Math.min(cmd.length, buffer.length)-1;
        if (!(buffer.length == 0)) {
            if (!cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && cmd[maxLength].startsWith(buffer[maxLength])) {
                listToAddTo.add(cmd[maxLength]);
            } else if (cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && (cmd.length > maxLength+1)) {
                listToAddTo.add(cmd[maxLength+1]);
            }
        }
        if (buffer.length+1 > cmd.length && cmd[maxLength].equalsIgnoreCase(buffer[maxLength]) && addAllOnlinePlayers) {
            Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
            int i = 0;
            while (i < onlinePlayers.length) {
                listToAddTo.add(onlinePlayers[i].getName());
                i++;
            }
        }
        return listToAddTo;
    }

    public void setConfig(FileConfiguration config, String path, int value) {
        if (!config.isSet(path)) {
            config.set(path, value);
        }
    }

    public void setConfig(FileConfiguration config, String path, boolean value) {
        if (!config.isSet(path)) {
            config.set(path, value);
        }
    }

    public void setConfig(FileConfiguration config, String path, String value) {
        if (!config.isSet(path)) {
            config.set(path, value);
        }
    }

    public void updateConfig() {
        FileConfiguration config = plugin.getConfig();

        this.setConfig(config, "MaxPoliceTp", 50);
        this.setConfig(config, "PayPoliceOnArrest", true);
        this.setConfig(config, "MoneyToGiveToPoliceOnArrest", 500);
        this.setConfig(config, "TakeMoneyOnArrest", false);
        this.setConfig(config, "PercentOfMoneyToTake", 20);
        this.setConfig(config, "SafeArea", false);
        if (!config.isSet("SafeAreas")) {
            ArrayList<String> safeAreas = new ArrayList<>();
            safeAreas.add("examplesafearea1");
            safeAreas.add("examplesafearea2");
            config.set("SafeAreas", safeAreas);
        }
        this.setConfig(config, "BatonMaterialType", "BLAZE_ROD");
        this.setConfig(config, "PercentOfFindingContraband", 20);
        this.setConfig(config, "FriskStickMaterialType", "BLAZE_ROD");
        this.setConfig(config, "MarkAllGunsAsContraband", false);
        this.setConfig(config, "ShowCords911", false);
        this.setConfig(config, "FriskCooldown", 600);
        this.setConfig(config, "UsePlayerDisplayNameInPoliceChat", true);
        this.setConfig(config, "UseEssentialsJailSystem", false);
        if (!config.isSet("ArrestGUI")) {
            CustomJailGuiItem cItem = new CustomJailGuiItem(plugin);
            ArrayList<Object> arrestGUI = new ArrayList<>();
            arrestGUI.add(cItem.setFile("AIR", 0.0, null, "&bClick here to jail the player for $1 minutes"));
            arrestGUI.add(cItem.setFile("AIR", 0.0, null, "&bClick here to jail the player for $1 minutes"));
            arrestGUI.add(cItem.setFile("RED_TERRACOTTA", 4.0, null, "&bClick here to jail the player for $1 minutes"));
            arrestGUI.add(cItem.setFile("ORANGE_TERRACOTTA", 8.0, null, "&bClick here to jail the player for $1 minutes"));
            arrestGUI.add(cItem.setFile("YELLOW_TERRACOTTA", 10.0, null, "&bClick here to jail the player for $1 minutes"));
            arrestGUI.add(cItem.setFile("LIME_TERRACOTTA", 12.0, null, "&bClick here to jail the player for $1 minutes"));
            arrestGUI.add(cItem.setFile("LIGHT_BLUE_TERRACOTTA", 15.0, null, "&bClick here to jail the player for $1 minutes"));
            config.set("ArrestGUI", arrestGUI);
        }
        if (!config.isSet("JailLocation")) {
            HashMap<String, Object> jailLoc = new HashMap<>();
            jailLoc.put("World", "world");
            jailLoc.put("X", 50);
            jailLoc.put("Y", 50);
            jailLoc.put("Z", 50);
            ArrayList<Object> arrayL = new ArrayList<>();
            arrayL.add(jailLoc);
            config.set("JailLocation", arrayL);
        }

        plugin.saveConfig();

    }

    public String capitalize(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
