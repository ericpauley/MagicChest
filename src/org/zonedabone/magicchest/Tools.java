package org.zonedabone.magicchest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.zonedabone.magicchest.API.InventorySortEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tools {

    public static void SortInventory(Inventory i, Player p) {
        if (PluginCompatibility.isCompatibleInventory(i, p)) {
            InventorySortEvent ise = new InventorySortEvent(i);
            Bukkit.getServer().getPluginManager().callEvent(ise);
            if (!ise.isCancelled()) {
                InventoryType type = i.getType();
                PlayerInventory pi = null;
                boolean isPlayerInventory = false;
                boolean good = false;
                if (type == InventoryType.CHEST
                        && p.hasPermission("magicchest.sort.chest"))
                    good = true;
                if (type == InventoryType.DISPENSER
                        && p.hasPermission("magicchest.sort.dispenser"))
                    good = true;
                if (type == InventoryType.DROPPER
                        && p.hasPermission("magicchest.sort.dropper"))
                    good = true;
                if (type == InventoryType.ENDER_CHEST
                        && p.hasPermission("magicchest.sort.enderchest"))
                    good = true;
                if (type == InventoryType.HOPPER
                        && p.hasPermission("magicchest.sort.hopper"))
                    good = true;
                if (type == InventoryType.SHULKER_BOX
                        && p.hasPermission("magicchest.sort.shulkerbox"))
                    good = true;
                if (type == InventoryType.PLAYER
                        && p.hasPermission("magicchest.sort.player")) {
                    isPlayerInventory = true;
                    pi = (PlayerInventory) i;
                    good = true;
                }

                if (good && i.getViewers().size() == 1) {
                    List<ItemStack> stacks = new ArrayList<ItemStack>();
                    if (isPlayerInventory) {
                        int skip = 9;
                        for (ItemStack is : pi.getStorageContents()) {
                            if (skip < 1) {
                                if (is == null)
                                    continue;
                                for (ItemStack check : stacks) {
                                    if (check == null)
                                        continue;
                                    if (check.isSimilar(is)) {
                                        int transfer = Math.min(
                                                is.getAmount(),
                                                check.getMaxStackSize()
                                                        - check.getAmount());
                                        is.setAmount(is.getAmount() - transfer);
                                        check.setAmount(check.getAmount()
                                                + transfer);
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
                                    int transfer = Math.min(
                                            is.getAmount(),
                                            check.getMaxStackSize()
                                                    - check.getAmount());
                                    is.setAmount(is.getAmount() - transfer);
                                    check.setAmount(check.getAmount()
                                            + transfer);
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
                            if (o1.getType().toString()
                                    .compareTo(o2.getType().toString()) > 0) {
                                return 1;
                            } else if (o1.getType().toString()
                                    .compareTo(o2.getType().toString()) < 0) {
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
                    if (!isPlayerInventory) {
                        i.clear();
                        i.setContents(stacks.toArray(new ItemStack[0]));
                    } else {
                        int slot = 9;
                        for (int n = 9; n < 36; n++) {
                            p.getInventory().setItem(n,
                                    new ItemStack(Material.AIR));
                        }
                        for (ItemStack is : stacks) {
                            if (slot <= 35) {
                                p.getInventory().setItem(slot, is);
                                slot++;
                            }
                        }
                    }
                }
            }
        }
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
