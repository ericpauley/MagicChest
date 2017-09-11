package org.zonedabone.magicchest;

import com.gmail.filoghost.chestcommands.ChestCommands;
import net.dandielo.api.traders.tNpcAPI;
import net.dandielo.citizens.traders_v3.bukkit.DtlTraders;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PluginCompatibility {

    private static Plugin plugin;

    public static void initialize(Plugin p) {
        plugin = p;
    }

    private static DtlTraders getdtlTraders() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("dtlTraders");
        // DtlTraders may not be loaded
        if (plugin == null || !(plugin instanceof DtlTraders)) {
            return null;
        }
        return (DtlTraders) plugin;
    }

    private static ChestCommands getChestCommands() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ChestCommands");
        // ChestCommands may not be loaded
        if (plugin == null || !(plugin instanceof ChestCommands)) {
            return null;
        }
        return (ChestCommands) plugin;
    }

    private static co.kepler.fastcraftplus.FastCraftPlus getFastCraft() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("FastCraftPlus");
        // FastCraft may not be loaded
        if (plugin == null || !(plugin instanceof co.kepler.fastcraftplus.FastCraftPlus)) {
            return null;
        }
        return (co.kepler.fastcraftplus.FastCraftPlus) plugin;
    }

    private static com.sucy.skill.SkillAPI getSkillAPI() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("SkillAPI");
        // SkillAPI may not be loaded
        if (plugin == null || !(plugin instanceof com.sucy.skill.SkillAPI)) {
            return null;
        }
        return (com.sucy.skill.SkillAPI) plugin;
    }

    public static String printPluginCompatibilty() {
        String compat = "";
        if (getdtlTraders() != null)
            compat = compat + "Compatibility loaded for dtlTraders!\n";
        if (getChestCommands() != null)
            compat = compat + "Compatibility loaded for ChestCommands!\n";
        if (getFastCraft() != null)
            compat = compat + "Compatibility loaded for FastCraft!\n";
        if (getSkillAPI() != null)
            compat = compat + "Compatibility loaded for SkillAPI!";
        if (compat == "")
            return null;
        return compat;
    }

    public static boolean isCompatibleInventory(Inventory i, Player p) {
        for (OfflinePlayer op : plugin.getServer().getOfflinePlayers())
            if (i.getTitle() == op.getName())
                return false;
        if (getdtlTraders() != null)
            if (tNpcAPI.isTNpcInventory(p))
                return false;
        if (getChestCommands() != null)
            if (i instanceof com.gmail.filoghost.chestcommands.internal.MenuInventoryHolder)
                return false;
        if (getFastCraft() != null)
            if (co.kepler.fastcraftplus.api.FastCraftAPI.isGUI(i))
                return false;
        if (i.getType() == InventoryType.CHEST && i.getHolder() instanceof Chest) {
            Block b = ((Chest) i.getHolder()).getBlock();
            for (BlockFace bf : BlockFace.values()) {
                if (b.getRelative(bf).getType() == Material.WALL_SIGN) {
                    for (String s : ((Sign) b.getRelative(bf).getState()).getLines()) {
                        if (s.contains("(AShops)")) {
                            return false;
                        }
                    }
                }
            }
        }
        if (getSkillAPI() != null)
            if (i.getHolder() instanceof com.sucy.skill.tree.SkillTree)
                return false;

        //last resort check valid titles
        if (!getNativeInventoryTitles().contains(i.getTitle())) {
            return false;
        }

        return true;
    }

    public static ArrayList<String> getNativeInventoryTitles() {
        String validTypes = "container.inventory,container.chest,container.chestDouble,container.enderchest,Minecart with Chest,container.shulkerBox";
        ArrayList<String> listTypes = new ArrayList<String>();
        for (String type : validTypes.split(",")) {
            listTypes.add(type);
        }
        return listTypes;
    }
}
