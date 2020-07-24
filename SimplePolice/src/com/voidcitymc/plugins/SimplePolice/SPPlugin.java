package com.voidcitymc.plugins.SimplePolice;

import com.voidcitymc.api.SimplePolice.SimplePoliceAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SPPlugin extends JavaPlugin implements SimplePoliceAPI {
    static HashMap<String, String> lastArrest = new HashMap<String, String>();
    private static SPPlugin instance;
    File DataFile;
    FileConfiguration Data;
    File ControbandFile;
    FileConfiguration Controband;
    File MessageFile;
    FileConfiguration Message;
    Worker work = new Worker();

    public static SPPlugin getInstance() {
        return instance;
    }

    public void createMessage() {
        MessageFile = new File(getDataFolder(), "messages.yml");
        if (!MessageFile.exists()) {
            MessageFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }
        Message = YamlConfiguration.loadConfiguration(MessageFile);
    }

    public void createData() {
        DataFile = new File(getDataFolder(), "data.yml");
        if (!DataFile.exists()) {
            DataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        Data = YamlConfiguration.loadConfiguration(DataFile);
    }

    public void createControbandFile() {
        ControbandFile = new File(getDataFolder(), "contraband.yml");
        if (!ControbandFile.exists()) {
            ControbandFile.getParentFile().mkdirs();
            saveResource("contraband.yml", false);
        }
        Controband = YamlConfiguration.loadConfiguration(ControbandFile);
    }

    public void SaveMessageFile() {
        try {
            Message.save(MessageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveDataFile() {
        try {
            Data.save(DataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveControbandFile() {
        try {
            Controband.save(ControbandFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //enabled
    @Override
    public void onEnable() {
        instance = this;
        //create config
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        //add missing items to config
        work.updateConfig();
        //create datafile and controband file
        createData();
        createControbandFile();
        createMessage();
        //update checker
        new UpdateChecker(this).checkForUpdate();
        //metrics
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, 6814);
        //
        getServer().getPluginManager().registerEvents(new GUI(), this);
        getServer().getPluginManager().registerEvents(new PoliceListener(), this);
        getServer().getPluginManager().registerEvents(new Frisk(), this);
        this.getCommand("police").setExecutor(new Police());
        this.getCommand("911").setExecutor(new NineOneOne());
        this.getCommand("police").setAliases(Collections.singletonList(this.getConfig().getString("CmdForPolice")));
        this.getCommand("911").setAliases(Collections.singletonList(this.getConfig().getString("CmdFor911")));
        System.out.println("ramdon_person's Police Plugin Has Been Enabled!");
    }


//api

    //disabled
    @Override
    public void onDisable() {
        System.out.println("Thanks for using ramdon_person's police plugin!");
        System.out.println("-- Saving Data --");
        this.saveConfig();
        this.SaveDataFile();
        System.out.println("-- All data saved! --");
    }

    @Override
    public ArrayList<String> onlinePoliceList() {
        return work.onlinePoliceList();
    }

    @Override
    public void addPolice(String uuid) {
        work.addPolice(uuid);
    }

    @Override
    public boolean isPolice(String uuid) {
        return work.alreadyPolice(uuid);
    }

    @Override
    public void removePolice(String uuid) {
        work.removePolice(uuid);

    }

    @Override
    public Location policeTp(Player player, int farthestTpDistance) {
        return work.policeTp(player, farthestTpDistance);
    }

    @Override
    public Location policeTp(Player player) {
        return work.policeTp(player, SPPlugin.getInstance().getConfig().getInt("MaxPoliceTp"));
    }

    @Override
    public ArrayList<String> listPolice() {
        return work.listPolice();
    }

    @Override
    public boolean inSafeArea(Player player) {
        return work.inSafeArea(player);
    }

    @Override
    public Material getBatonMaterial() {
        return work.getBatonMaterial();
    }

    @Override
    public Material getFriskStickMaterial() {
        return work.getFriskStickMaterial();
    }

    @Override
    public void addToFriskList(ItemStack item) {
        work.addToFriskList(item);

    }

    @Override
    public void removeFromFriskList(ItemStack item) {
        work.removeFromFriskList(item);

    }

    @Override
    public List<ItemStack> getFriskList() {
        return work.getFriskList();
    }

    @Override
    public boolean friskingEnabled() {
        return work.friskingEnabled();
    }


}
