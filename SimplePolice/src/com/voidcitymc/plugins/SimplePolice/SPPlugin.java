package com.voidcitymc.plugins.SimplePolice;

import com.voidcitymc.api.SimplePolice.SimplePolice;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

public class SPPlugin extends JavaPlugin implements SimplePolice {
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
    public SimplePoliceAPI getApi() {
        return new SimplePoliceAPI();
    }
}
