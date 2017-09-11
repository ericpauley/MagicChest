package org.zonedabone.magicchest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

    //format for mgcs to use
    private static String format = ChatColor.GOLD + "[" + ChatColor.GREEN + "MagicChest" + ChatColor.GOLD + "] " + ChatColor.RESET;

    //sends plugin message with with either a player name string or the actual instance of the player
    public static void sendPM(String pl, String msg) {
        Bukkit.getServer().getPlayer(pl).sendMessage(format + msg);
    }

    public static void sendPM(Player pl, String msg) {
        pl.sendMessage(format + msg);
    }

    public static String formatString(String s) {
        return format + s;
    }
}
