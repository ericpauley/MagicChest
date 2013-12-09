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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
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

	private static com.rocketmail.live2pwn.Main getUncraftingTable() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Uncrafting Table");
		// Uncrafting Table may not be loaded
		if (plugin == null || !(plugin instanceof com.rocketmail.live2pwn.Main)) {
			return null;
		}
		return (com.rocketmail.live2pwn.Main) plugin;
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
		if(getUncraftingTable() != null)
			compat = compat + "Compatibility loaded for Uncrafting Table!\n";
		if(getFastCraft() != null)
			compat = compat + "Compatibility loaded for FastCraft!\n";
		if(getAShops() != null)
			compat = compat + "Compatibility loaded for AShops!";
		if(compat == "")
			return null;
		return compat;
	}

	public static boolean isCompatibleInventory(InventoryOpenEvent e) {
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
		if(getAShops() != null)
			if(e.getInventory().getTitle().contains("Shop Manager")
					|| e.getInventory().getTitle().contains("Select item.")
					|| e.getInventory().getTitle().contains("Offer Manager")
					|| e.getInventory().getTitle().contains("Collect items")
					|| e.getInventory().getTitle().contains("Load items")
					|| e.getInventory().getTitle().contains("Buy")
					|| e.getInventory().getTitle().contains("Sell"))
				return false;
		//Bukkit.getPlayer("uvbeenzaned").sendMessage(e.getInventory().getType().toString() + ", " + e.getInventory().getHolder().toString());
			if(e.getInventory().getType() == InventoryType.CHEST && e.getInventory().getHolder() instanceof Chest) {
				Block b = ((Chest)e.getInventory().getHolder()).getBlock();
				for(BlockFace bf : BlockFace.values()) {
					//Bukkit.getPlayer("uvbeenzaned").sendMessage(bf.toString() + ": " + b.getRelative(bf).getType().toString());
					if(b.getRelative(bf).getType() == Material.WALL_SIGN) {
						for(String s : ((Sign)b.getRelative(bf)).getLines()) {
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
