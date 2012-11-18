package org.zonedabone.magicchest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicChest extends JavaPlugin {
	
	public MagicChestListener mcl;
	
	@Override
	public void onEnable() {
		mcl = new MagicChestListener();
		getServer().getPluginManager().registerEvents(this.mcl, this);
		saveDefaultConfig();
		getConfig().options().copyDefaults();
		//load conf
		ConfOps.load(this);
	}
	
	public void onDisable() {
		//save all config
		ConfOps.save(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			Player plcmd = (Player)sender;
			if(args.length > 0)
			{
				//turn individual player sort on
				if(args[0].equalsIgnoreCase("on"))
				{
					//check for perms
					if(plcmd.hasPermission("magicchest.sort"))
					{
						if(ConfOps.players.containsKey(plcmd.getName()))
						{
							ConfOps.players.put(plcmd.getName(), true);
							saveConfig();
							return true;
						}
						else
						{
							ConfOps.players.put(plcmd.getName(), true);
							saveConfig();
							return true;
						}
					}
					//nope!
					sendPM(plcmd, "Your permissions do not allow you to sort chests!");
					return false;
				}
				//turn individual player sort off
				if(args[0].equalsIgnoreCase("off"))
				{
					//check for perms
					if(plcmd.hasPermission("magicchest.sort"))
					{
						if(ConfOps.players.containsKey(plcmd.getName()))
						{
							ConfOps.players.put(plcmd.getName(), false);
							saveConfig();
							return true;
						}
						else
						{
							ConfOps.players.put(plcmd.getName(), false);
							saveConfig();
							return true;
						}
					}
					//nope!
					sendPM(plcmd, "Your permissions do not allow you to sort chests!");
					return false;
				}
				return false;
			}
			sendPM(plcmd, "You did not provide enough arguments!");
			return false;
		}
		return false;
	}
	
	public void sendPM(String pl, String msg)
	{
		getServer().getPlayer(pl).sendMessage(ChatColor.GREEN + " " + msg + ChatColor.RESET);
	}
	
	public void sendPM(Player pl, String msg)
	{
		pl.sendMessage(ChatColor.GREEN + "[MagicChest] " + msg + ChatColor.RESET);
	}
	
}
