package cn.pixelwar.pwlifesteal.Listeners;

import cn.pixelwar.pwlifesteal.File.YamlStorage;
import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.FireSkill;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.*;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
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
    public void onPlayerDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Entity killer = getKiller(event);
        ApplicableRegionSet playerRegions = GetWGRegion.getWGRegion(victim);
        //先检查是不是在spawn中,但是不在pvp中
        if (
                GetWGRegion.checkIfInRegion(playerRegions, "spawn") &&
                        !(GetWGRegion.checkIfInRegion(playerRegions, "pvp"))

        ){
            return;
        }

        //进行数据计数
        //被击杀者
        PlayerStatsManager.playerStatMap.get(victim.getName()).setDeath(PlayerStatsManager.playerStatMap.get(victim.getName()).getDeath()+1);
        PlayerStatsManager.playerStatMap.get(victim.getName()).setKillStreak(0);



        double nowMaxHearts = PlayerStatsManager.playerStatMap.get(victim.getName()).getMaxHearts();
        double aimMaxHearts = nowMaxHearts;
        if (FireSkill.doDEATH_NO_HEART_LOSE(victim)) {
            victim.sendMessage(ChatColorCast.format("&6▸ &f你死亡了! &c但是未损失❤"));
        }else {
            aimMaxHearts = nowMaxHearts - 1;
            victim.sendMessage(ChatColorCast.format("&6▸ &f你死亡了! &c-1&l❤"));
        }
        //玩家已经没有生命了，ban
        if(aimMaxHearts<=0){
            int banTime = PlayerStatsManager.playerStatMap.get(victim.getName()).getBanTime();
            Ban.banPlayer(victim, banTime);
            PlayerStatsManager.setPlayerMaxHearts(victim, 20);
            victim.kickPlayer(ChatColorCast.format("&c你已经被服务器封禁"));
        }else {
            PlayerStatsManager.setPlayerMaxHearts(victim, (nowMaxHearts - 1));
        }
        //玩家杀死玩家
        if (killer!=null) {
            if (killer.getType().equals(EntityType.PLAYER)) {
                Player attacker = (Player) killer;
                int exp = event.getDroppedExp();
                if (exp > 0) {
                    exp = FireSkill.doTRIPLE_EXP_FROM_KILL_PLAYER(exp, attacker);
                    event.setDroppedExp(exp);
                }
                double nowMaxHearts2 = PlayerStatsManager.playerStatMap.get(attacker.getName()).getMaxHearts();
                double aimMaxHearts2 = nowMaxHearts2;
                if (FireSkill.doDOUBLE_HEARTS_FROM_KILL_PLAYER(attacker)) {
                    aimMaxHearts2 = aimMaxHearts2 + 2;
                    attacker.sendMessage(ChatColorCast.format("&6▸ &f你击杀了&d&l"+victim.getName()+" &a+2&l❤"));
                } else {
                    aimMaxHearts2 += 1;
                    attacker.sendMessage(ChatColorCast.format("&6▸ &f你击杀了&d&l"+victim.getName()+" &a+1&l❤"));
                }
                PlayerStatsManager.setPlayerMaxHearts(attacker, aimMaxHearts2);

                //进行数据计数
                //杀人者
                PlayerStatsManager.playerStatMap.get(attacker.getName()).setKill(PlayerStatsManager.playerStatMap.get(attacker.getName()).getKill()+1);
                PlayerStatsManager.playerStatMap.get(attacker.getName()).setKillStreak(PlayerStatsManager.playerStatMap.get(attacker.getName()).getKillStreak()+1);
                PlayerStatsManager.playerStatMap.get(attacker.getName()).setLastKillPlayerName(victim.getName());
                FireSkill.doKILLSTREAK_GET_HEAL(attacker);

                //掉钱

                int victimNowRuby = PlayerStatsManager.playerStatMap.get(victim.getName()).getRuby();
                if(victimNowRuby>10){
//                    int attackerNowRuby = PlayerStatsManager.playerStatMap.get(attacker.getName()).getRuby();
                    int random = NumberFormat.getRandomInt(1, 10) / 2;
                    PlayerStatsManager.removePlayerRuby(victim, random);
                    PlayerStatsManager.givePlayerRuby(attacker, random);
                    attacker.sendMessage(ChatColorCast.format("&6▸ &f你获得了&c&l"+victim.getName()+"&f掉落的&d&l"+random+"紫宝石"));
                    victim.sendMessage(ChatColorCast.format("&c▸ 你因为被其他玩家杀死掉落了&d&l"+random+"紫宝石"));
                }
                double victimNowMoney = Money.getPlayerMoney(victim);
                if(victimNowMoney>10){
//                    int attackerNowRuby = PlayerStatsManager.playerStatMap.get(attacker.getName()).getRuby();
                    int random = NumberFormat.getRandomInt(1, (int) victimNowMoney/20) / 2;
                    Money.takePlayerMoney(victim, random);
                    Money.givePlayerMoney(attacker, random);
                    victim.sendMessage(ChatColorCast.format("&c▸ 你因为被其他玩家杀死掉落了&e&l"+random+"&2&l$"));
                    if (FireSkill.doDOUBLE_MONEY_FROM_KILL_PLAYER(attacker)){
                        Money.givePlayerMoney(attacker, random);
                        attacker.sendMessage(ChatColorCast.format("&6▸ &f你获得了&c&l"+victim.getName()+"&f掉落的&e&l"+random*2+"&2&l$"));
                    }else{
                        attacker.sendMessage(ChatColorCast.format("&6▸ &f你获得了&c&l"+victim.getName()+"&f掉落的&e&l"+random+"&2&l$"));

                    }


                }
            }
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity dead = event.getEntity();
        Entity killer = getKiller(event);
        if (killer!=null){
            //玩家杀死怪物
            if (!dead.getType().equals(EntityType.PLAYER) && killer.getType().equals(EntityType.PLAYER)){
                Player attacker = (Player) killer;
                int exp = event.getDroppedExp();
                if (exp==0) return;
                exp = FireSkill.doDOUBLE_EXP_FROM_KILL_MOB(exp, attacker);
                event.setDroppedExp(exp);
            }
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
    public Entity getKiller(PlayerDeathEvent event) {
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


    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        double damage = e.getDamage();
        //如果是玩家的攻击,怪物被攻击
        if (
                attacker.getType().equals(EntityType.PLAYER) &&
                        (!(victim.getType().equals(EntityType.PLAYER)))
        ){
            damage = FireSkill.doPVE_TRIPLE_DAMAGE(e.getDamage(), (Player) attacker);
            FireSkill.doKNOCKBACK_MOB((Player) attacker,  victim);
            e.setDamage(damage);
        }
        //如果是玩家的攻击,玩家被攻击
        if (
                attacker.getType().equals(EntityType.PLAYER) &&
                        (victim.getType().equals(EntityType.PLAYER))
        ){
            FireSkill.doKNOCKBACK_PLAYER((Player) attacker, (Player) victim);
        }

        //如果是怪物的攻击,玩家被攻击
        if (
                !attacker.getType().equals(EntityType.PLAYER) &&
                victim.getType().equals(EntityType.PLAYER)
        ){
            if (FireSkill.doPVE_DODGE_DAMAGE((Player) victim)){
                e.setCancelled(true);
                return;
            }else {
                damage = FireSkill.doPVE_LESS_DAMAGE(e.getDamage(), (Player) victim);
            }
            e.setDamage(damage);
        }

        //发动低血量技能
        if ( victim.getType().equals(EntityType.PLAYER)){
            Player player = (Player) victim;
            if (player.getHealth()-damage<=player.getMaxHealth()/5) {
                FireSkill.doLOW_HEALTH_RESISTANCE(player);
                FireSkill.doLOW_HEALTH_SPEED(player);
                FireSkill.doLOW_HEALTH_REGENERATION(player);
                FireSkill.doLOW_HEALTH_JUMP(player);
            }
        }

    }

    @EventHandler
    public void PlayerDamagedEvent(EntityDamageEvent e) {
        if (!(e.getEntity().getType().equals(EntityType.PLAYER))){
            return;
        }
        Player player = (Player) e.getEntity();
        double damage = e.getDamage();
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if (FireSkill.doFALL_DODGE_DAMAGE(player)) {
                e.setCancelled(true);
                return;
            }
        }

    }
}
