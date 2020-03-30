package com.ohneemc.OhWild.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final Map<UUID, String> worldCooldown = new HashMap<>();

    public static final int DEFAULT_COOLDOWN = Config.getInteger("settings.cooldown");
    public static final boolean DEFAULT_PRWORLD = Config.getBoolean("settings.prworld");

    public static void setCooldown(UUID player, long time) {
        {
            if (time < 1) {
                cooldowns.remove(player);

                if (DEFAULT_PRWORLD)
                    worldCooldown.remove(player);
            } else {
                cooldowns.put(player, time);

                if (DEFAULT_PRWORLD)
                    worldCooldown.put(player, Bukkit.getPlayer(player).getWorld().getName());
            }
        }
    }

    public static long getCooldown(UUID player) {
        return cooldowns.getOrDefault(player, 0L);
    }

    public static String getWorld(UUID player) {
        return worldCooldown.get(player);
    }
}
