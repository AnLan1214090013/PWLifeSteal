package cn.pixelwar.pwlifesteal.Utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GetWGRegion {

    public static ApplicableRegionSet getWGRegion(Location location){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager rgm = container.get(BukkitAdapter.adapt(location.getWorld()));
        RegionQuery query = container.createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location));
    }

    public static ApplicableRegionSet getWGRegion(Entity entity){
        Location location = entity.getLocation();
        return getWGRegion(location);
    }

    public static ApplicableRegionSet getWGRegion(Player player){
        Location location = player.getLocation();
        return getWGRegion(location);
    }

    public static boolean checkIfInRegion(ApplicableRegionSet regionSet, String regionName){
        for (ProtectedRegion region : regionSet){
            if (region.getId().equals(regionName)){
                return true;
            }
        }
        return false;
    }

}
