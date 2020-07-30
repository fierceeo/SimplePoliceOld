package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimplePoliceAPI implements com.voidcitymc.api.SimplePolice.SimplePoliceAPI {

    Worker work = new Worker();

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

    @Override
    public boolean isItemContraband(ItemStack item) {
        return work.isItemContraband(item);
    }

    @Override
    public String getMessage(String path, String... variables) {
        return Messages.getMessage(path, variables);
    }

    @Override
    public Map<String, String> getMessagesMap() {
        return (new Messages().getMessagesMap());
    }


}
