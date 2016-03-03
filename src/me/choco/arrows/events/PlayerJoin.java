package me.choco.arrows.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if (player.getUniqueId().toString().equals("73c62196-2af7-463d-8be1-a7a2270f4696")/*2008Choco*/){
			player.sendMessage(ChatColor.GOLD + "--------------------------------------------");
			player.sendMessage(ChatColor.GRAY + "     You are the developer of " + ChatColor.ITALIC + "AlchemicalArrows");
			player.sendMessage("");
			player.sendMessage(ChatColor.GRAY + "                What's up, " + ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + player.getName());
			player.sendMessage(ChatColor.DARK_AQUA + "                     Version: " + ChatColor.GRAY + Bukkit.getPluginManager().getPlugin("AlchemicalArrows").getDescription().getVersion());
			player.sendMessage(ChatColor.GOLD + "--------------------------------------------");
		}
	}
}