package com.ohneemc.OhWild;

import com.ohneemc.OhWild.util.CooldownManager;
import com.ohneemc.OhWild.util.CreateInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.TimeUnit;

public class WildCommand implements CommandExecutor {

    private final CooldownManager cooldownManager = new CooldownManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("wild") && sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            Inventory inv = CreateInventory.createWildInventory(player);
            if (player != null && inv != null) {
                long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(player.getUniqueId());
                if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) > CooldownManager.DEFAULT_COOLDOWN || CooldownManager.DEFAULT_COOLDOWN == -1){
                    cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
                    player.openInventory(inv);
                }else{
                    long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownManager.DEFAULT_COOLDOWN);
                    player.sendMessage(ChatColor.GOLD + String.valueOf(seconds) + ChatColor.GREEN + " seconds before you can use wild again.");
                }
                return true;
            }
        }
        return false;
    }
}
