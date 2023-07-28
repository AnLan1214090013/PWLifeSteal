package cn.pixelwar.pwlifesteal.Listeners;

import cn.pixelwar.pwlifesteal.Utils.GetWGRegion;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.netzkronehd.wgregionevents.events.RegionEnterEvent;
import de.netzkronehd.wgregionevents.events.RegionLeaveEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpawnListener implements Listener {

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent event){
        event.blockList().clear();
    }
    @EventHandler
    public void onFireDamage(EntityDamageEvent event){
        if (!(event.getEntity().getType().equals(EntityType.PLAYER))){
            return;
        }


        if (
                event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)||
                event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)
        ){
            Player player = (Player) event.getEntity();
            ApplicableRegionSet playerRegions = GetWGRegion.getWGRegion(player);
            //先检查是不是在spawn中，但是又不在pvp中
            if (
                    GetWGRegion.checkIfInRegion(playerRegions, "spawn") &&
                    !(GetWGRegion.checkIfInRegion(playerRegions, "pvp"))

            )
            {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onRegionEnter(RegionEnterEvent e)
    {
        if (e.getRegion().getId().equals("spawn")){
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 1, false, false, false));
        }
    }

    @EventHandler
    public void onRegionLeave(RegionLeaveEvent e)
    {
        if (e.getRegion().getId().equals("spawn")){
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
    @EventHandler
    public void onRegionLeft(RegionLeftEvent e)
    {
        if (e.getRegion().getId().equals("spawn")){
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }


}
