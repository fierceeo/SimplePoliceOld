package com.voidcitymc.www;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
private static Main instance;

//enabled
@Override
public void onEnable() {
	getServer().getPluginManager().registerEvents(new MyListener(), this);
	instance = this;
	this.getCommand("police").setExecutor(new Police());
	System.out.println("ramdon_person's Police Plugin Has Been Enabled!");
}
//get the instance thingy (or "this")

public static Main getInstance() {
	return instance;
}

//disabled
@Override
public void onDisable() {
	System.out.println("Thanks for using ramdon_person's police plugin!");
	System.out.println("-- Saving Data --");
	this.saveConfig();
	System.out.println("-- All data saved! --");
}
}

// need to review this
// https://www.spigotmc.org/threads/player-haspermission-not-working.146769/?__cf_chl_jschl_tk__=7095348855a540c73071771e519f7172fd209dcf-1575993858-0-AbnNQ7iSbPLxP1z0fpYy0K_zmvQtyErHCsq704IWp2NTEpvKmOktTWfQeQx-JGEDDTq7S9OXJaI0mUBY0g-NHsSnOiQ0Kn3ldwDmK21bnHhzczIkzOaTpkQT3WQJVwi2az4jQgpSA4t9mzbzo88WLwfLAzaj9EMOxhwG5UGIoTS2eRxfYFTykFsVqyWDrpnVto-70kIYk79uaREGwaICH0NtgytTPfMn6j_GlgPHjDhY5ll-Tm6-Z2BzlGJDQitNFMpqqTfDHQtfwVvo7PwcQeYonEIJOc71eH7Man8I53ZOljk_q2IbkpMuq8LaDNMeOFxpePpa57AS186S4Zf5pvE
