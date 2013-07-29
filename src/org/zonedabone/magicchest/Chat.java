package org.zonedabone.magicchest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

	//sends plugin message with with either a player name string or the actual instance of the player
		public static void sendPM(String pl, String msg)
		{
			Bukkit.getServer().getPlayer(pl).sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + "MagicChest" + ChatColor.GOLD + "] " + ChatColor.RESET + msg);
		}

		public static void sendPM(Player pl, String msg)
		{
			pl.sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + "MagicChest" + ChatColor.GOLD + "] " + ChatColor.RESET + msg);
		}
	
}
