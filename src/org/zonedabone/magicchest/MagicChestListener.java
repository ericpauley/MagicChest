package org.zonedabone.magicchest;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MagicChestListener implements Listener {

    MagicChest pl;

    public MagicChestListener(MagicChest pl) {
        this.pl = pl;
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if (!pl.getConfig().getBoolean("override")) {
            if (p.isOp() || p.hasPermission("magicchest.sort")) {
                if (!pl.getConfig().getBoolean("default_sorting")) {
                    if (pl.getConfig().getConfigurationSection("players") != null) {
                        if (pl.getConfig().getConfigurationSection("players").contains(p.getName())) {
                            if (pl.getConfig().getBoolean("players." + p.getName())) {
                                Tools.SortInventory(e.getInventory(), (Player) e.getPlayer());
                            }
                        } else {
                            Tools.addToListFalse(pl, p.getName());
                            Chat.sendPM(p, "If you would like to sort chests automatically, type /mgcs on.");
                        }
                    } else {
                        Tools.addToListFalse(pl, p.getName());
                        Chat.sendPM(p, "If you would like to sort chests automatically, type /mgcs on.");
                    }
                } else {
                    if (pl.getConfig().getConfigurationSection("players") != null) {
                        if (pl.getConfig().getConfigurationSection("players").contains(p.getName())) {
                            if (pl.getConfig().getBoolean("players." + p.getName())) {
                                Tools.SortInventory(e.getInventory(), (Player) e.getPlayer());
                            }
                        } else {
                            Chat.sendPM(p, "Your chest will sort their items automatically now.  To turn this off, type /mgcs off.");
                            Tools.addToListTrue(pl, p.getName());
                            Tools.SortInventory(e.getInventory(), (Player) e.getPlayer());
                        }
                    } else {
                        Chat.sendPM(p, "Your chest will sort their items automatically now.  To turn this off, type /mgcs off.");
                        Tools.addToListTrue(pl, p.getName());
                        Tools.SortInventory(e.getInventory(), (Player) e.getPlayer());
                    }
                }
            }
        } else {
            Tools.SortInventory(e.getInventory(), (Player) e.getPlayer());
        }
    }

    @EventHandler
    private void onItemPickupOpen(EntityPickupItemEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER) {
            Player p = (Player) e.getEntity();

            if (!pl.getConfig().getBoolean("override")) {
                if (p.isOp() || p.hasPermission("magicchest.sort")) {
                    if (!pl.getConfig().getBoolean("default_sorting")) {
                        if (pl.getConfig().getConfigurationSection("inv-players") != null) {
                            if (pl.getConfig().getConfigurationSection("inv-players").contains(p.getName())) {
                                if (pl.getConfig().getBoolean("inv-players." + p.getName())) {
                                    Tools.SortInventory(p.getInventory(), p);
                                }
                            } else {
                                Tools.addToListInvFalse(pl, p.getName());
                                Chat.sendPM(p, "If you would like to sort your inventory automatically on item pickup, type /mgcs invon.");
                            }
                        } else {
                            Tools.addToListInvFalse(pl, p.getName());
                            Chat.sendPM(p, "If you would like to sort your inventory automatically on item pickup, type /mgcs invon.");
                        }
                    } else {
                        if (pl.getConfig().getConfigurationSection("inv-players") != null) {
                            if (pl.getConfig().getConfigurationSection("inv-players").contains(p.getName())) {
                                if (pl.getConfig().getBoolean("inv-players." + p.getName())) {
                                    Tools.SortInventory(p.getInventory(), p);
                                }
                            } else {
                                Chat.sendPM(p, "Your inventory will sort it's items on item pickup automatically now.  To turn this off, type /mgcs invoff.");
                                Tools.addToInvListTrue(pl, p.getName());
                                Tools.SortInventory(p.getInventory(), p);
                            }
                        } else {
                            Chat.sendPM(p, "Your inventory will sort it's items on item pickup automatically now.  To turn this off, type /mgcs invoff.");
                            Tools.addToInvListTrue(pl, p.getName());
                            Tools.SortInventory(p.getInventory(), p);
                        }
                    }
                }
            } else {
                Tools.SortInventory(p.getInventory(), p);
            }
        }
    }
}