package org.zonedabone.magicchest;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.plugin.Plugin;

public class ConfOps {

	//list of player strings that this plugin is disabled for
	public static HashMap<String, Boolean> players = new HashMap<String, Boolean>();
	
	//save all player prefs to conf.yml
	public static void save(Plugin pg)
	{
		for(Entry<String, Boolean> e : players.entrySet())
		{
			pg.getConfig().set("players." + e.getKey(), e.getValue());
		}
		pg.saveConfig();
	}
	
	//load all player prefs to players HashMap
	public static void load(Plugin pg)
	{
		if(pg.getConfig().getConfigurationSection("players").getKeys(false) != null)
		{
			for(String key : pg.getConfig().getConfigurationSection("players").getKeys(false))
			{
				if(pg.getConfig().getString("players." + key) != null)
				{
					ConfOps.players.put(key, pg.getConfig().getBoolean("players." + key));
				}
			}
		}
	}
}
