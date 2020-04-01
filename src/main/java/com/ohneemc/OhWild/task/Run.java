package com.ohneemc.OhWild.task;

import org.bukkit.Location;
import org.bukkit.entity.Player;

class Run {

    static void runWild(Player player, Location location) {
        player.teleport(location.add(0,0.2,0));
    }
}
