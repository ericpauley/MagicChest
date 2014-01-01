package org.zonedabone.magicchest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class Tools {
	
	public static void SortInventory(Inventory i, Player p) {
		if(PluginCompatibility.isCompatibleInventory(i, p))
		{
			InventoryType type = i.getType();
			PlayerInventory pi = null;
			boolean isPlayerInventory = false;
			boolean good = false;
			if (type == InventoryType.CHEST && p.hasPermission("magicchest.sort.chest"))
				good = true;
			if (type == InventoryType.DISPENSER && p.hasPermission("magicchest.sort.dispenser"))
				good = true;
			if (type == InventoryType.DROPPER && p.hasPermission("magicchest.sort.dropper"))
				good = true;
			if (type == InventoryType.ENDER_CHEST && p.hasPermission("magicchest.sort.enderchest"))
				good = true;
			if (type == InventoryType.HOPPER && p.hasPermission("magicchest.sort.hopper"))
				good = true;
			if (type == InventoryType.PLAYER && p.hasPermission("magicchest.sort.player")) {
				isPlayerInventory = true;
				pi = (PlayerInventory)i;
				good = true;
			}

			if (good && i.getViewers().size() == 1) {
				List<ItemStack> stacks = new ArrayList<ItemStack>();
				if(isPlayerInventory) {
					int skip = 9;
					for (ItemStack is : pi.getContents()) {
						if(skip < 1) {
							if (is == null)
								continue;
							for (ItemStack check : stacks) {
								if (check == null)
									continue;
								if (check.isSimilar(is)) {
									int transfer = Math.min(is.getAmount(), check.getMaxStackSize() - check.getAmount());
									is.setAmount(is.getAmount() - transfer);
									check.setAmount(check.getAmount() + transfer);
								}
							}
							if (is.getAmount() > 0) {
								stacks.add(is);
							}
						} else {
							skip--;
						}
					}
				} else {
					for (ItemStack is : i.getContents()) {
						if (is == null)
							continue;
						for (ItemStack check : stacks) {
							if (check == null)
								continue;
							if (check.isSimilar(is)) {
								int transfer = Math.min(is.getAmount(), check.getMaxStackSize() - check.getAmount());
								is.setAmount(is.getAmount() - transfer);
								check.setAmount(check.getAmount() + transfer);
							}
						}
						if (is.getAmount() > 0) {
							stacks.add(is);
						}
					}
				}
				Collections.sort(stacks, new Comparator<ItemStack>() {

					@Override
					public int compare(ItemStack o1, ItemStack o2) {
						if (o1.getType().toString().compareTo(o2.getType().toString()) > 0) {
							return 1;
						} else if (o1.getType().toString().compareTo(o2.getType().toString()) < 0) {
							return -1;
						} else if (o1.getDurability() > o2.getDurability()) {
							return 1;
						} else if (o1.getDurability() < o2.getDurability()) {
							return -1;
						} else if (o1.getAmount() > o2.getAmount()) {
							return -1;
						} else if (o1.getAmount() < o2.getAmount()) {
							return 1;
						} else {
							return 0;
						}
					}
				});
				if(!isPlayerInventory) {
					i.clear();
					i.setContents(stacks.toArray(new ItemStack[0]));
				} else {
					int slot = 9;
					for (int n = 9; n < 36; n++)
					{
						p.getInventory().setItem(n, new ItemStack(Material.AIR));
					}
					for(ItemStack is : stacks) {
						if(slot <= 35) {
							p.getInventory().setItem(slot, is);
							slot++;
						}
					}
				}
			}
		}
	}
	
	public static Location getInventoryLocation(Inventory i) {
		InventoryHolder ih = i.getHolder();
		Location l = null;
		if(ih instanceof Chest)
			l = ((Chest)ih).getLocation();
		if(ih instanceof DoubleChest)
			l = ((DoubleChest)ih).getLocation();
		if(ih instanceof Hopper)
			l = ((Hopper)ih).getLocation();
		if(ih instanceof Dispenser)
			l = ((Dispenser)ih).getLocation();
		if(ih instanceof Dropper)
			l = ((Dropper)ih).getLocation();
		return l;
	}
	
	public static void addToListFalse(Plugin pl, String p) {
		pl.getConfig().set("players." + p, false);
		pl.saveConfig();
	}

	public static void addToListTrue(Plugin pl, String p) {
		pl.getConfig().set("players." + p, true);
		pl.saveConfig();
	}
	
	public static void addToListInvFalse(Plugin pl, String p) {
		pl.getConfig().set("inv-players." + p, false);
		pl.saveConfig();
	}

	public static void addToInvListTrue(Plugin pl, String p) {
		pl.getConfig().set("inv-players." + p, true);
		pl.saveConfig();
	}
}
