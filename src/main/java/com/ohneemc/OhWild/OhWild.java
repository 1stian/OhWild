package com.ohneemc.OhWild;

import com.ohneemc.OhWild.task.InvListener;
import com.ohneemc.OhWild.task.SignListener;
import com.ohneemc.OhWild.util.Config;
import com.ohneemc.OhWild.util.GetNewLocation;
import org.bstats.bukkit.Metrics;
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
        //Metrics
        int pluginId = 5721;
        Metrics metrics = new Metrics(this, pluginId);
        //Config part
        saveDefaultConfig();
        reloadConfig();
        //Register command
        getCommand("wild").setExecutor(new WildCommand());
        //Register listeners
        getServer().getPluginManager().registerEvents(new InvListener(), this);
        //Start various tasks.
        fillUnsafe();
        //Add sign materials
        signMaterials();
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
        GetNewLocation.materials.add(Material.AIR);
    }

    private static void signMaterials(){
        //Normal signs
        SignListener.signMaterials.add(Material.OAK_SIGN);
        SignListener.signMaterials.add(Material.SPRUCE_SIGN);
        SignListener.signMaterials.add(Material.ACACIA_SIGN);
        SignListener.signMaterials.add(Material.BIRCH_SIGN);
        SignListener.signMaterials.add(Material.DARK_OAK_SIGN);
        SignListener.signMaterials.add(Material.JUNGLE_SIGN);

        //Wall signs
        SignListener.signMaterials.add(Material.SPRUCE_WALL_SIGN);
        SignListener.signMaterials.add(Material.ACACIA_WALL_SIGN);
        SignListener.signMaterials.add(Material.BIRCH_WALL_SIGN);
        SignListener.signMaterials.add(Material.DARK_OAK_WALL_SIGN);
        SignListener.signMaterials.add(Material.OAK_WALL_SIGN);
        SignListener.signMaterials.add(Material.JUNGLE_WALL_SIGN);
    }

    public void onDisable(){

    }
}
