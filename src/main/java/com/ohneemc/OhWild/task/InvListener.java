package com.ohneemc.OhWild.task;

import com.ohneemc.OhWild.OhWild;
import com.ohneemc.OhWild.util.Config;
import com.ohneemc.OhWild.util.CooldownManager;
import com.ohneemc.OhWild.util.GetNewLocation;
import com.ohneemc.OhWild.util.Maps;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.concurrent.TimeUnit;

public class InvListener implements Listener {
    private static Location location = null;
    private static String teleportMessage = Config.getString("messages.teleport");

    @EventHandler
    public static void invListener(InventoryClickEvent event){
        String guiName = ChatColor.translateAlternateColorCodes('&', Config.getString("settings.guiname"));
        String title = event.getView().getTitle();

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null){
            return;
        }

        if (title.equalsIgnoreCase(guiName)){
            event.setCancelled(true);
        }

        if (event.getCurrentItem() == null){
            return;
        }

        if (event.getCurrentItem().getItemMeta() == null){
            return;
        }

        String clickedItem = event.getCurrentItem().getItemMeta().getDisplayName();
        if (Maps.itemWorld.containsKey(clickedItem)){
            World world = Bukkit.getWorld(Maps.itemWorld.get(clickedItem));
            if (world == null){
                Bukkit.getLogger().info("World null");
                return;
            }

            if (teleportMessage.length() > 1){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', teleportMessage));
            }
            player.closeInventory();

            if (CooldownManager.DEFAULT_PRWORLD){
                long timeLeft = System.currentTimeMillis() - CooldownManager.getCooldown(player.getUniqueId());
                if (CooldownManager.getWorld(player.getUniqueId()) != null){
                    if (!CooldownManager.getWorld(player.getUniqueId()).equalsIgnoreCase(clickedItem)){
                        startTask(player, world);
                    }else{
                        if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) > Maps.worldCooldown.get(clickedItem) || Maps.worldCooldown.get(clickedItem) == -1){
                            startTask(player, world);
                        }else{
                            String cooldownMessage = Config.getString("worlds." + world.getName() + ".message");
                            if (cooldownMessage.length() > 1) {
                                long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - Maps.worldCooldown.get(clickedItem));
                                String strip = String.valueOf(seconds).replace("-", "");
                                String message = cooldownMessage.replace("{time}", strip);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    }
                }else{
                    startTask(player, world);
                }
            }else{
                startTask(player, world);
            }
        }
    }

    static void startTask(Player player, World world){
        CooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
        Bukkit.getScheduler().runTaskAsynchronously(OhWild.instance, () -> {
            location = GetNewLocation.getLocation(world);
            if (location == null || location.add(0, 2, 0).getBlock().getType() != Material.AIR) {
                startTask(player, world);
                return;
            }
            Bukkit.getScheduler().runTask(OhWild.instance, () -> Run.runWild(player, location));
        });
    }
}
