package org.zonedabone.magicchest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Tools {
	
	public static void SortInventory(InventoryOpenEvent e) {
		if(PluginCompatibility.isCompatibleInventory(e))
		{
			InventoryType type = e.getInventory().getType();
			boolean good = false;
			if (type == InventoryType.CHEST && e.getPlayer().hasPermission("magicchest.sort.chest"))
				good = true;
			if (type == InventoryType.DISPENSER && e.getPlayer().hasPermission("magicchest.sort.dispenser"))
				good = true;
			if (type == InventoryType.DROPPER && e.getPlayer().hasPermission("magicchest.sort.dropper"))
				good = true;
			if (type == InventoryType.ENDER_CHEST && e.getPlayer().hasPermission("magicchest.sort.enderchest"))
				good = true;
			if (type == InventoryType.HOPPER && e.getPlayer().hasPermission("magicchest.sort.hopper"))
				good = true;

			if (good && e.getViewers().size() == 1) {
				List<ItemStack> stacks = new ArrayList<ItemStack>();
				for (ItemStack is : e.getInventory().getContents()) {
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
				e.getInventory().clear();
				e.getInventory().setContents(stacks.toArray(new ItemStack[0]));
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
}
