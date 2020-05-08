package com.voidcitymc.plugins.SimplePolice;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.voidcitymc.plugins.SimplePolice.Main;

public class Main extends JavaPlugin {
private static Main instance;

File DataFile;
FileConfiguration Data;

public void createData() {
    DataFile = new File(getDataFolder(), "data.yml");
    if (!DataFile.exists()) {
        DataFile.getParentFile().mkdirs();
        saveResource("data.yml", false);
     }
    Data = YamlConfiguration.loadConfiguration(DataFile);
}

public static Main getInstance() {
	return instance;
}

public void SaveDataFile() {
	try {
		Data.save(DataFile);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//enabled
@Override
public void onEnable() {
	//update checker
	new UpdateChecker(this).checkForUpdate();
	//metrics
	@SuppressWarnings("unused")
	Metrics metrics = new Metrics(this, 6814);
	//create config
	this.getConfig().options().copyDefaults(true);
	saveConfig();
	//create datafile;
	createData();
	//add mising items to config
	worker.AddMissingItemsToConfig();
	//
	getServer().getPluginManager().registerEvents(new PoliceListener(), this);
	instance = this;
	this.getCommand("police").setExecutor(new Police());
	this.getCommand("911").setExecutor(new NineOneOne());
	System.out.println("ramdon_person's Police Plugin Has Been Enabled!");
}


//disabled
@Override
public void onDisable() {
	System.out.println("Thanks for using ramdon_person's police plugin!");
	System.out.println("-- Saving Data --");
	this.saveConfig();
	this.SaveDataFile();
	System.out.println("-- All data saved! --");
}
}
