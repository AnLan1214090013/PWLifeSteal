package cn.pixelwar.pwlifesteal;


import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import cn.pixelwar.pwlifesteal.Utils.NumberFormat;
import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
public class Placeholders extends PlaceholderExpansion {

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     * This expansion does not require a dependency so we will always return true
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here
     */
    @Override
    public String getAuthor() {
        return "An_Lan";
    }

    /**
     * The placeholder identifier should go here
     * This is what tells PlaceholderAPI to call our onPlaceholderRequest method to obtain
     * a value if a placeholder starts with our identifier.
     * This must be unique and can not contain % or _
     */
    @Override
    public String getIdentifier() {
        return "pwlf";
    }

    /**
     * if an expansion requires another plugin as a dependency, the proper name of the dependency should
     * go here. Set this to null if your placeholders do not require another plugin be installed on the server
     * for them to work
     */
    @Override
    public String getPlugin() {
        return null;
    }

    /**
     * This is the version of this expansion
     */
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        NumberFormat numberFormat = new NumberFormat();

        // %pwlf_kill%
        if (identifier.equals("kill")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "0";
            }
            return numberFormat.getIntFormat(PlayerStatsManager.playerStatMap.get(p.getName()).getKill());
        }
        if (identifier.equals("maxhearts")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "20";
            }
            return numberFormat.getIntFormat((int)PlayerStatsManager.playerStatMap.get(p.getName()).getMaxHearts());
        }
        if (identifier.equals("death")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "0";
            }
            return numberFormat.getIntFormat(PlayerStatsManager.playerStatMap.get(p.getName()).getDeath());
        }
        if (identifier.equals("killstreak")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "0";
            }
            return numberFormat.getIntFormat(PlayerStatsManager.playerStatMap.get(p.getName()).getKillStreak());
        }
        if (identifier.equals("ruby")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "0";
            }
            return numberFormat.getIntFormat(PlayerStatsManager.playerStatMap.get(p.getName()).getRuby());
        }
        if (identifier.equals("lastkill")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "无";
            }
            return PlayerStatsManager.playerStatMap.get(p.getName()).getLastKillPlayerName();
        }
        if (identifier.equals("lastkillstr")) {
            if (!PlayerStatsManager.playerStatMap.containsKey(p.getName())){
                return "";
            }
            String name = PlayerStatsManager.playerStatMap.get(p.getName()).getLastKillPlayerName();
            return ChatColorCast.format("&8[&4☠&c"+name+"&8]") ;
        }

        return null;
    }
}

