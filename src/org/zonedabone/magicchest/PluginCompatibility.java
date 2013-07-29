package org.zonedabone.magicchest;

import net.dandielo.citizens.traders_v3.bukkit.DtlTraders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginCompatibility {

	public static DtlTraders getdtlTraders() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("dtlTraders");

		// DtlTraders may not be loaded
		if (plugin == null || !(plugin instanceof DtlTraders)) {
			return null;
		}

		return (DtlTraders) plugin;
	}
}
