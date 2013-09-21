package org.zonedabone.magicchest;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Tools {
	static boolean alpha = true;
	public static void SortInventory(InventoryOpenEvent e)
	{
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
				if(alpha) {
					SortedMap<String, ItemStack> alphastacks = new TreeMap<String, ItemStack>();
					for(ItemStack item : stacks) {
						boolean hasitem = false;
						for(String k : alphastacks.keySet()) {
							if(k.contains(item.getType().toString())) {
								hasitem = true;
							} else {
								hasitem = false;
							}
						}
						if(!hasitem && (!item.hasItemMeta() && (!item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore()))) {
							if(item.getItemMeta() != null && item.getItemMeta().hasDisplayName()) {
								alphastacks.put(item.getItemMeta().getDisplayName() + ":" + String.valueOf(item.getDurability()), item);
								Bukkit.broadcastMessage(item.getItemMeta().getDisplayName() + ":" + String.valueOf(item.getDurability()));
							} else {
								alphastacks.put(item.getType().toString() + ":" + String.valueOf(item.getDurability()), item);
								Bukkit.broadcastMessage(item.getType().toString() + ":" + String.valueOf(item.getDurability()));
							}
						} else {
							int icnt = 0;
							int lorecnt = 0;
							for(String n : alphastacks.keySet()) {
								if(alphastacks.get(n).getItemMeta().hasDisplayName() && n.contains(item.getItemMeta().getDisplayName())) {
									icnt++;
								}
								if(alphastacks.get(n).getItemMeta().hasLore()) {
									lorecnt++;
								}
							}
							if(!item.getItemMeta().hasLore() && item.getItemMeta().hasDisplayName()) {
								alphastacks.put(item.getItemMeta().getDisplayName() + ":" + String.valueOf(item.getDurability()) + "," + String.valueOf(lorecnt) + "," + String.valueOf(icnt), item);
								Bukkit.broadcastMessage(item.getItemMeta().getDisplayName() + ":" + String.valueOf(item.getDurability()) + "," + String.valueOf(lorecnt) + "," + String.valueOf(icnt));
							} else {
								if(item.getItemMeta().hasLore() && !item.getItemMeta().hasDisplayName()) {
									alphastacks.put(item.getType().toString() + ":" + String.valueOf(item.getDurability()) + "," + String.valueOf(lorecnt) + "," + String.valueOf(icnt), item);
									Bukkit.broadcastMessage(item.getType().toString() + ":" + String.valueOf(item.getDurability()) + "," + String.valueOf(lorecnt) + "," + String.valueOf(icnt));
								} else {
									if(!item.getItemMeta().hasLore() && !item.getItemMeta().hasDisplayName())
										alphastacks.put(item.getType().toString() + ":" + String.valueOf(item.getDurability()) + "," + String.valueOf(icnt), item);
									Bukkit.broadcastMessage(item.getType().toString() + ":" + String.valueOf(item.getDurability()) + "," + String.valueOf(icnt));
								}
							}
						}
					}
					stacks.clear();
					for(Entry<String, ItemStack> item : alphastacks.entrySet()) {
						stacks.add(item.getValue());
					}
				}
//				else
//				{
//					Collections.sort(stacks, new Comparator<ItemStack>() {
//
//						@Override
//						public int compare(ItemStack o1, ItemStack o2) {
//							if (o1.getTypeId() > o2.getTypeId()) {
//								return 1;
//							} else if (o1.getTypeId() < o2.getTypeId()) {
//								return -1;
//							} else if (o1.getData() != null && o2.getData() != null && o1.getData().getData() > o2.getData().getData()) {
//								return 1;
//							} else if (o1.getData() != null && o2.getData() != null && o1.getData().getData() < o2.getData().getData()) {
//								return -1;
//							} else if (o1.getAmount() > o2.getAmount()) {
//								return -1;
//							} else if (o1.getAmount() < o2.getAmount()) {
//								return 1;
//							} else {
//								return 0;
//							}
//						}
//					});
//				}
				e.getInventory().clear();
				e.getInventory().setContents(stacks.toArray(new ItemStack[0]));
			}
		}
	}

	public static void addToListFalse(Plugin pl, String p)
	{
		pl.getConfig().set("players." + p, false);
		pl.saveConfig();
	}

	public static void addToListTrue(Plugin pl, String p)
	{
		pl.getConfig().set("players." + p, true);
		pl.saveConfig();
	}
}
