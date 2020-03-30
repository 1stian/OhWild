package com.ohneemc.OhWild.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreateInventory {

    private static String guiName = Config.getString("settings.guiname");

    public static Inventory createWildInventory(Player player){

        //Getting inventory settings
        int invSize = Config.getInteger("settings.size");
        String name = ChatColor.translateAlternateColorCodes('&',guiName);
        Inventory inv = Bukkit.createInventory(null, invSize, name);
        String emptyItem = Config.getString("settings.fillitem");

        if (emptyItem == null){
            return null;
        }
        Material fillItem = Material.getMaterial(emptyItem);
        if (fillItem == null){
            return null;
        }
        ItemStack empty = new ItemStack(fillItem);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName(" ");
        empty.setItemMeta(emptyMeta);

        List<Integer> posFilled = new ArrayList<>();
        ConfigurationSection section = Config.getSection("worlds");

        if (section != null) {
            for (String items : section.getKeys(false)){
                String materialButton = Config.getString("worlds." + items + ".item");
                String itemName = Config.getString("worlds." + items + ".name");
                List<String> lore = Config.getList("worlds." + items +  ".lore");
                Material itemMaterial = Material.matchMaterial(materialButton);
                if (itemMaterial == null){
                    Bukkit.getLogger().warning("[OhWild] Couldn't find item.. " + items);
                    return null;
                }
                ItemStack toAdd = new ItemStack(itemMaterial);
                ItemMeta meta = toAdd.getItemMeta();
                meta.setDisplayName(itemName);
                meta.setLore(lore);
                toAdd.setItemMeta(meta);

                int pos = Config.getInteger("worlds." + items + ".position");
                Maps.worldCooldown.put(itemName, Config.getInteger("worlds." + items + ".cooldown"));

                Maps.itemWorld.put(toAdd.getItemMeta().getDisplayName(), items);
                posFilled.add(pos);
                inv.setItem(pos, toAdd);
            }
        }else{
            return null;
        }

        int i = 0;
        while (i < invSize){
            if (!posFilled.contains(i)){
                inv.setItem(i, empty);
            }
            i++;
        }
        return inv;
    }
}
