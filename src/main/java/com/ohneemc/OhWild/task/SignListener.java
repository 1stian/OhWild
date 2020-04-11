package com.ohneemc.OhWild.task;

import com.ohneemc.OhWild.OhWild;
import com.ohneemc.OhWild.util.Config;
import com.ohneemc.OhWild.util.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.ohneemc.OhWild.task.InvListener.startTask;

public class SignListener implements Listener {

    private final String cooldownMessage = Config.getString("messages.cooldown");

    public static List<Material> signMaterials = new ArrayList<>();

    @EventHandler
    public void onSign(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (signMaterials == null || event.getClickedBlock() == null){
            return;
        }

        long timeLeft = System.currentTimeMillis() - CooldownManager.getCooldown(player.getUniqueId());

        if (signMaterials.contains(event.getClickedBlock().getType())){
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                Sign sign = (Sign) event.getClickedBlock().getState();

                String line1 = ChatColor.stripColor(sign.getLine(0));
                String line2 = ChatColor.stripColor(sign.getLine(1));

                if (line1.equalsIgnoreCase("[Wild]")){
                    if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) > CooldownManager.DEFAULT_COOLDOWN || CooldownManager.DEFAULT_COOLDOWN == -1){
                        World world = OhWild.instance.getServer().getWorld(line2.toLowerCase());
                        if (world == null){
                            player.sendMessage(ChatColor.RED + "World name is invalid. Can't teleport.");
                        }else{
                            player.sendMessage(ChatColor.GREEN + "Swosh");
                            startTask(player, world);
                        }
                    }else{
                        if (cooldownMessage.length() > 1) {
                            long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownManager.DEFAULT_COOLDOWN);
                            String strip = String.valueOf(seconds).replace("-", "");
                            String message = cooldownMessage.replace("{time}", strip);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignPlace(SignChangeEvent event){
        Player player = event.getPlayer();

        String line1 = event.getLine(0);
        String line2 = event.getLine(1);

        if (line1 == null || line2 == null){
            return;
        }

        if (line1.equalsIgnoreCase("[Wild]")){
            String wName = line2.toLowerCase();
            World world = OhWild.instance.getServer().getWorld(wName);
            if (world == null){
                player.sendMessage(ChatColor.RED + "World name is invalid.");
            }else{
                event.setLine(0, ChatColor.GOLD + "[" + ChatColor.GREEN + "Wild" + ChatColor.GOLD + "]");
                event.setLine(1, ChatColor.AQUA + wName);
                player.sendMessage(ChatColor.GREEN + "Teleport sign successfully created!");
            }
        }
    }
}
