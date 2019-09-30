package com.ohneemc.OhWild;

import com.ohneemc.OhWild.task.InvListener;
import com.ohneemc.OhWild.util.Config;
import com.ohneemc.OhWild.util.GetNewLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class OhWild extends JavaPlugin {

    public static OhWild instance;
    public static boolean debug = Config.getBoolean("debug");

    @SuppressWarnings("ConstantConditions")
    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        //Register command
        getCommand("wild").setExecutor(new WildCommand());
        //Register listeners
        getServer().getPluginManager().registerEvents(new InvListener(), this);
        //Start various tasks.
        fillUnsafe();
    }

    private static void fillUnsafe(){
        //Put unsafeBlocks in material list.
        List<String> unsafeBlocsk = Config.getList("settings.unsafeblocks");
        for (String item : unsafeBlocsk) {
            Material material = Material.matchMaterial(item);
            if (material == null){
                Bukkit.getLogger().warning(ChatColor.GREEN +"[OhWild]" + ChatColor.RED + " Couldn't match material listed in unsafe blocks! "
                        + ChatColor.GOLD + item);
                return;
            }
            GetNewLocation.materials.add(material);
        }
    }

    public void onDisable(){

    }
}
