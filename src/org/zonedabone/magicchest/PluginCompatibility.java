package org.zonedabone.magicchest;

import net.dandielo.api.traders.tNpcAPI;
import net.dandielo.citizens.traders_v3.bukkit.DtlTraders;

import com.gmail.filoghost.chestcommands.ChestCommands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class PluginCompatibility {

	private static Plugin plugin;

	public static void initialize(Plugin p) {
		plugin = p;
	}

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

	private static co.kepler.fastcraftplus.FastCraft getFastCraft() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("FastCraftPlus");
		// FastCraft may not be loaded
		if (plugin == null || !(plugin instanceof co.kepler.fastcraftplus.FastCraft)) {
			return null;
		}
		return (co.kepler.fastcraftplus.FastCraft) plugin;
	}

//	private static pl.austindev.ashops.AShops getAShops() {
//		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("AShops");
//		// AShops may not be loaded
//		if (plugin == null || !(plugin instanceof pl.austindev.ashops.AShops)) {
//			return null;
//		}
//		return (pl.austindev.ashops.AShops) plugin;
//	}

	private static com.sucy.skill.SkillAPI getSkillAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("SkillAPI");
		// SkillAPI may not be loaded
		if (plugin == null || !(plugin instanceof com.sucy.skill.SkillAPI)) {
			return null;
		}
		return (com.sucy.skill.SkillAPI) plugin;
	}

//	private static org.goblom.gui.plugin.SimpleGuiCreator getSimpleGuiCreator() {
//		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("SimpleGUI Creator");
//		// SimpleGuiCreator may not be loaded
//		if (plugin == null || !(plugin instanceof org.goblom.gui.plugin.SimpleGuiCreator)) {
//			return null;
//		}
//		return (org.goblom.gui.plugin.SimpleGuiCreator) plugin;
//	}

	public static String printPluginCompatibilty() {
		String compat = "";
		if (getdtlTraders() != null)
			compat = compat + "Compatibility loaded for dtlTraders!\n";
		if (getChestCommands() != null)
			compat = compat + "Compatibility loaded for ChestCommands!\n";
		if (getFastCraft() != null)
			compat = compat + "Compatibility loaded for FastCraft!\n";
//		if (getAShops() != null)
//			compat = compat + "Compatibility loaded for AShops!\n";
		if (getSkillAPI() != null)
			compat = compat + "Compatibility loaded for SkillAPI!";
//		if (getSimpleGuiCreator() != null)
//			compat = compat + "Compatibility loaded for SimpleGUI Creator!";
		if (compat == "")
			return null;
		return compat;
	}

	public static boolean isCompatibleInventory(Inventory i, Player p) {
		for (OfflinePlayer op : plugin.getServer().getOfflinePlayers())
			if (i.getTitle() == op.getName())
				return false;
		if (getdtlTraders() != null)
			if (tNpcAPI.isTNpcInventory(p))
				return false;
		if (getChestCommands() != null)
			if (i instanceof com.gmail.filoghost.chestcommands.internal.MenuInventoryHolder)
				return false;
		if (getFastCraft() != null)
			if (co.kepler.fastcraftplus.api.gui.FastCraftAPI.isFastCraftGUI(i))
				return false;
		if (i.getType() == InventoryType.CHEST && i.getHolder() instanceof Chest) {
			Block b = ((Chest) i.getHolder()).getBlock();
			for (BlockFace bf : BlockFace.values()) {
				if (b.getRelative(bf).getType() == Material.WALL_SIGN) {
					for (String s : ((Sign) b.getRelative(bf).getState()).getLines()) {
						if (s.contains("(AShops)")) {
							return false;
						}
					}
				}
			}
		}
		if (getSkillAPI() != null)
			if (i.getHolder() instanceof com.sucy.skill.tree.SkillTree)
				return false;
//		if (getSimpleGuiCreator() != null)
//			for (String t : getSimpleGuiCreator().getAPI(plugin).getGUIs()) {
//				if (i.getTitle().equals(t)) {
//					return false;
//				}
//			}
		return true;
	}
}
