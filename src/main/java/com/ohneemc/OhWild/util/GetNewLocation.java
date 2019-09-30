package com.ohneemc.OhWild.util;

import org.bukkit.*;

import java.util.ArrayList;
import java.util.List;

public class GetNewLocation {

    public static List<Material> materials = new ArrayList<>();
    private static boolean notSafe = false;

    public static Location getLocation(World world) {
        int radius = Config.getInteger("worlds." + world.getName() + ".radius");
        int negativeRadius = radius - (radius * 2);

        //Setting radius.
        int x = GetX(radius, negativeRadius);
        int z = GetZ(radius, negativeRadius);

        World.Environment getWorldEnv = world.getEnvironment();
        if (getWorldEnv == World.Environment.NORMAL) {
            return getHighestBlock(world, 255, x, z);
        }
        if (getWorldEnv == World.Environment.NETHER) {
            return getHighestBlock(world, 100, x, z);
        }
        return null;
    }

    private static Integer GetX(int max, int min) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private static Integer GetZ(int max, int min) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private static Location getHighestBlock(World world, int height, int x, int z) {
        notSafe = false;
        int i = height;
        Location check = new Location(world, x, i, z);
        while (i > 0) {
            if (!materials.contains(new Location(world, x, i, z).getBlock().getType())) {
                return new Location(world, x, i, z).add(0, 2, 0);
            } else {
                if (materials.contains(check.getBlock().getType()) && check.getBlock().getType() != Material.AIR) {
                    notSafe = true;
                }
                i--;
            }
        }
        return null;
    }
}
