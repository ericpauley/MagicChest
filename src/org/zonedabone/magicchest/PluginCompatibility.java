package org.zonedabone.magicchest;

import net.dandielo.api.traders.tNpcAPI;
import net.dandielo.citizens.traders_v3.bukkit.DtlTraders;

import com.gmail.filoghost.chestcommands.ChestCommands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
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

	private static ChestCommands getChestCommands() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ChestCommands");
		// ChestCommands may not be loaded
		if (plugin == null || !(plugin instanceof ChestCommands)) {
			return null;
		}
		return (ChestCommands) plugin;
	}

	private static me.bw.fastcraft.FastCraft getFastCraft() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("FastCraft");
		// FastCraft may not be loaded
		if (plugin == null || !(plugin instanceof me.bw.fastcraft.FastCraft)) {
			return null;
		}
		return (me.bw.fastcraft.FastCraft) plugin;
	}
	
	private static pl.austindev.ashops.AShops getAShops() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("AShops");
		// AShops may not be loaded
		if (plugin == null || !(plugin instanceof pl.austindev.ashops.AShops)) {
			return null;
		}
		return (pl.austindev.ashops.AShops) plugin;
	}

	public static String printPluginCompatibilty() {
		String compat = "";
		if(getdtlTraders() != null)
			compat = compat + "Compatibility loaded for dtlTraders!\n";
		if(getChestCommands() != null)
			compat = compat + "Compatibility loaded for ChestCommands!\n";
		if(getFastCraft() != null)
			compat = compat + "Compatibility loaded for FastCraft!\n";
		if(getAShops() != null)
			compat = compat + "Compatibility loaded for AShops!";
		if(compat == "")
			return null;
		return compat;
	}

	public static boolean isCompatibleInventory(Inventory i, Player p) {
		if(getdtlTraders() != null)
			if(tNpcAPI.isTNpcInventory(p))
				return false;
		if(getChestCommands() != null)
			if(i.getTitle().startsWith("§r"))
				return false;
		if(getFastCraft() != null)
			if(me.bw.fastcraft.api.FastCraftApi.isFastCraftInventory(i))
				return false;
		if(getAShops() != null)
			if(i.getTitle().contains("Shop Manager")
					|| i.getTitle().contains("Select item.")
					|| i.getTitle().contains("Offer Manager")
					|| i.getTitle().contains("Collect items")
					|| i.getTitle().contains("Load items")
					|| i.getTitle().contains("Buy")
					|| i.getTitle().contains("Sell"))
				return false;
			if(i.getType() == InventoryType.CHEST && i.getHolder() instanceof Chest) {
				Block b = ((Chest)i.getHolder()).getBlock();
				for(BlockFace bf : BlockFace.values()) {
					if(b.getRelative(bf).getType() == Material.WALL_SIGN) {
						for(String s : ((Sign)b.getRelative(bf).getState()).getLines()) {
							if(s.contains("(AShops)")) {
								return false;
							}
						}
					}
				}
			}
		return true;
	}
}
