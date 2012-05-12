package org.zonedabone.magicchest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicChest extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (!e.getPlayer().hasPermission("magicchest.sort"))
			return;
		if (e.getInventory().getType() == InventoryType.CHEST && e.getViewers().size() == 1) {
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			for (ItemStack is : e.getInventory().getContents()) {
				if (is == null)
					continue;
				for (ItemStack check : stacks) {
					if (check == null)
						continue;
					if (check.getType() == is.getType() && ((check.getData() == null && is.getData() == null) || check.getData().getData() == is.getData().getData())) {
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
					if (o1.getTypeId() > o2.getTypeId()) {
						return 1;
					} else if (o1.getTypeId() < o2.getTypeId()) {
						return -1;
					} else if (o1.getData() != null && o2.getData() != null && o1.getData().getData() > o2.getData().getData()) {
						return 1;
					} else if (o1.getData() != null && o2.getData() != null && o1.getData().getData() < o2.getData().getData()) {
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
