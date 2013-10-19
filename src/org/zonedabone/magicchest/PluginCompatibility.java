package org.zonedabone.magicchest;

import net.dandielo.api.traders.tNpcAPI;
import net.dandielo.citizens.traders_v3.bukkit.DtlTraders;
import com.gmail.filoghost.chestcommands.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

public class PluginCompatibility {
	
	private static DtlTraders getdtlTraders() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("dtlTraders");
		// DtlTraders may not be loaded
		if (plugin == null || !(plugin instanceof DtlTraders)) {
			return null;
		}
		return (DtlTraders) plugin;
	}
	
	private static Main getChestCommands() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ChestCommands");
		// ChestCommands may not be loaded
		if (plugin == null || !(plugin instanceof Main)) {
			return null;
		}
		return (Main) plugin;
	}
	
	private static com.rocketmail.live2pwn.Main getUncraftingTable() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Uncrafting Table");
		// ChestCommands may not be loaded
		if (plugin == null || !(plugin instanceof com.rocketmail.live2pwn.Main)) {
			return null;
		}
		return (com.rocketmail.live2pwn.Main) plugin;
	}
	
	private static me.bw.fastcraft.FastCraft getFastCraft() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("FastCraft");
		// ChestCommands may not be loaded
		if (plugin == null || !(plugin instanceof me.bw.fastcraft.FastCraft)) {
			return null;
		}
		return (me.bw.fastcraft.FastCraft) plugin;
	}
	
	public static String printPluginCompatibilty()
	{
		String compat = "";
		if(getdtlTraders() != null)
			compat = compat + "Compatibility loaded for dtlTraders!\n";
		if(getChestCommands() != null)
			compat = compat + "Compatibility loaded for ChestCommands!\n";
		if (getUncraftingTable() != null)
			compat = compat + "Compatibility loaded for Uncrafting Table!\n";
		if(getFastCraft() != null)
			compat = compat + "Compatibility loaded for FastCraft!";
		if(compat == "")
			return null;
		return compat;
	}
	
	public static boolean isCompatibleInventory(InventoryOpenEvent e)
	{
		if(getdtlTraders() != null)
			if(tNpcAPI.isTNpcInventory((Player)e.getPlayer()))
				return false;
		if(getChestCommands() != null)
			if(e.getInventory().getTitle().startsWith("§r"))
				return false;
		if(getUncraftingTable() != null)
			if(e.getInventory().getTitle() == "Uncrafting")
				return false;
		if(getFastCraft() != null)
			if(me.bw.fastcraft.api.FastCraftApi.isFastCraftInventory(e.getInventory()))
				return false;
		return true;
	}
}
