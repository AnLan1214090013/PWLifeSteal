package cn.pixelwar.pwlifesteal.Listeners;

import cn.pixelwar.pwlifesteal.File.YamlStorage;
import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.Ban;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import cn.pixelwar.pwlifesteal.Utils.GetWGRegion;
import cn.pixelwar.pwlifesteal.Utils.NumberFormat;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LifeStealListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        Player p = event.getPlayer();
        //先加载玩家数据
        YamlStorage yamlStorage  = new YamlStorage();


        new BukkitRunnable() {
            @Override
            public void run() {
                boolean isFirst = yamlStorage.CheckYamlFile(p);
                if(!isFirst){
                    yamlStorage.CreatePlayerDataMap(p);
                }
                //设置玩家血量
                double maxHearts = PlayerStatsManager.playerStatMap.get(p.getName()).getMaxHearts();
                double hearts = PlayerStatsManager.playerStatMap.get(p.getName()).getHearts();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setMaxHealth(maxHearts*2);
                        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHearts*2);
//                        p.setHealth(hearts*2);
                        if (isFirst){
                            doFirstJoin(p);
                        }
                    }
                }.runTask(PWLifeSteal.getPlugin());
            }
        }.runTaskAsynchronously(PWLifeSteal.getPlugin());
    }

    public void doFirstJoin(Player player){
        PWLifeSteal.totalPlayerAmount +=1;
        NumberFormat numberFormat = new NumberFormat();
        player.sendMessage(ChatColorCast.format("&d▸ &f欢迎&b&l"+player.getName()+"&f加入嗜血生存. &7(第 "+ numberFormat.getIntFormat(PWLifeSteal.totalPlayerAmount) + " 位玩家)") );
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage("");
        YamlStorage yamlStorage  = new YamlStorage();
        Player p = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                //存储玩家信息
                yamlStorage.savePlayerData(p);
            }
        }.runTaskAsynchronously(PWLifeSteal.getPlugin());
    }


    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event){
        Entity dead = event.getEntity();
        Entity killer = getKiller(event);
        //玩家死亡
        if (dead.getType().equals(EntityType.PLAYER)){
            Player victim = (Player) dead;
            ApplicableRegionSet playerRegions = GetWGRegion.getWGRegion(victim);
            //先检查是不是在spawn中
            if (GetWGRegion.checkIfInRegion(playerRegions, "spawn")){
                return;
            }
            double nowMaxHearts = PlayerStatsManager.playerStatMap.get(victim.getName()).getMaxHearts();
            double aimMaxHearts = nowMaxHearts-1;
            //玩家已经没有生命了，ban
            if(aimMaxHearts<=0){
                int banTime = PlayerStatsManager.playerStatMap.get(victim.getName()).getBanTime();
                Ban.banPlayer(victim, banTime);
            }
            PlayerStatsManager.setPlayerMaxHearts(victim, (nowMaxHearts - 1));
        }


    }

    public Entity getKiller(EntityDeathEvent event) {
        EntityDamageEvent entityDamageEvent = event.getEntity().getLastDamageCause();
        if ((entityDamageEvent != null) && !entityDamageEvent.isCancelled() && (entityDamageEvent instanceof EntityDamageByEntityEvent)) {
            Entity damager = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();

            if (damager instanceof Projectile) {
                LivingEntity shooter = (LivingEntity) ((Projectile) damager).getShooter();
                if (shooter != null) return shooter;
            }

            return damager;
        }

        return null;
    }



}
