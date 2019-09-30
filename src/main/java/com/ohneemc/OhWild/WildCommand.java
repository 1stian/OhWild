package com.ohneemc.OhWild;

import com.ohneemc.OhWild.util.CreateInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("wild") && sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            Inventory inv = CreateInventory.createWildInventory(player);
            if (player != null && inv != null) {
                player.openInventory(inv);
                return true;
            }
        }
        return false;
    }
}
