package com.ohneemc.OhWild.util;

import com.ohneemc.OhWild.OhWild;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Config {

    /**
     * Report an error in reading the configuration
     * @param e Exception generated from reading configuration
     */
    private static void error(Exception e){
        if (OhWild.debug) e.printStackTrace();
    }

    /**
     * Fetches a boolean from the configuration
     * @param location Configuration location of the boolean
     */
    public static boolean getBoolean(String location){
        try {return OhWild.instance.getConfig().getBoolean(location);}
        catch (Exception e) {
            error(e);
            return false;
        }
    }

    /**
     * Fetches an integer from the configuration
     * @param location Configuration location of the integer
     */
    public static int getInteger(String location){
        try {return OhWild.instance.getConfig().getInt(location);}
        catch (Exception e) {
            error(e);
            return 0;
        }
    }

    /**
     * Fetches a string from the configuration
     * @param location Configuration location of the string
     */
    public static String getString(String location) {
        try {return OhWild.instance.getConfig().getString(location);}
        catch (Exception e) {
            error(e);
            return "";
        }
    }

    /**
     * Fetches a double from the configuration
     * @param location Configuration location of the double
     */
    public static double getDouble(String location) {
        try {return OhWild.instance.getConfig().getDouble(location);}
        catch (Exception e) {
            error(e);
            return 0.0;
        }
    }

    /**
     * Fetches a double from the configuration
     * @param location Configuration location of the double
     */
    public static List<String> getList(String location){
        try {return OhWild.instance.getConfig().getStringList(location);}
        catch (Exception e) {
            error(e);
            return new ArrayList<>();
        }
    }

    public static ConfigurationSection getSection(String section){
        try {return OhWild.instance.getConfig().getConfigurationSection(section);}
        catch (Exception e) {
            error(e);
            return null;
        }
    }

    public static boolean reloadConfig(){
        try {
            OhWild.instance.reloadConfig();
            return true;
        }catch (Exception e) {
            error(e);
            return false;
        }
    }
}
