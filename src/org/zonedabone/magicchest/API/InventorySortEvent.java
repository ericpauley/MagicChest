package org.zonedabone.magicchest.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class InventorySortEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Inventory inventory;

    public InventorySortEvent(Inventory i) {
        inventory = i;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
