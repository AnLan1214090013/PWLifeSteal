package cn.pixelwar.pwlifesteal.PlayerLevel.Listeners;

import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.LevelDoneEvent;
import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.QuestProgressEvent;
import cn.pixelwar.pwlifesteal.PlayerLevel.PlayerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.FireSkill;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author pyf
 * @description
 */

public class QuestListeners implements Listener {

    @EventHandler
    public void onQuestProgress(QuestProgressEvent event){
        Player player = event.getPlayer();
        Quest quest = event.getQuest();
        String msg = ChatColorCast.format("&f玩家等级 &d▸ &f"+quest.getQuestName()+" &f(&a"+ quest.getNowProgress()+"/"+quest.getNeedProgress()+"&f)");
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(msg));
        player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 2F);
    }
    @EventHandler
    public void onLevelDone(LevelDoneEvent event){
        Player player = event.getPlayer();
        String title = ChatColorCast.format("&a&l已完成所有任务");
        String subtitle = ChatColorCast.format("&f等级 &d▸ &b&l" + event.getOldLevel()+" &7(/level查看并领取奖励)");
        player.sendTitle(title, subtitle, 40, 120, 40);
        player.playSound(player.getEyeLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        Bukkit.getScheduler().runTaskAsynchronously(PWLifeSteal.getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<Integer, Quest> quests = PlayerLevelManager.getPlayerNowQuests(player);
                for (Quest quest : quests.values()) {

                    QuestType questType = quest.getQuestType();
                    if (questType.equals(QuestType.BREAK)) {
                        String variable = quest.getQuestVariable();
                        int nowProgress = quest.getNowProgress();
                        //破坏任何方块
                        if (variable.equals("ANY_BLOCK")) {
                            PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                            return;
                        }
                        //如果指定多种方块
                        if (variable.contains(";")) {
                            String[] vs = variable.split(";");
                            List<Material> materials = new ArrayList<>();
                            for (String v : vs) {
                                materials.add(Material.getMaterial(v));
                            }
                            for (Material m : materials) {
                                if (m.equals(material)) {
                                    PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                                    return;
                                }
                            }
                        }
                        //如果就是指定一种方块
                        Material m = Material.getMaterial(variable);
                        if (m == null) {
//                    Bukkit.getLogger().info("材料名称错误: "+variable);
                            continue;
                        }
                        if (m.equals(material)) {
                            PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                            return;
                        }
                    }
                }
            }
        });
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        Bukkit.getScheduler().runTaskAsynchronously(PWLifeSteal.getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<Integer, Quest> quests = PlayerLevelManager.getPlayerNowQuests(player);
                for (Quest quest : quests.values()) {
                    QuestType questType = quest.getQuestType();
                    if (questType.equals(QuestType.PLACE)) {
                        String variable = quest.getQuestVariable();
                        int nowProgress = quest.getNowProgress();
                        //放置任何方块
                        if (variable.equals("ANY_BLOCK")) {
                            PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                            return;
                        }
                        //如果指定多种方块
                        if (variable.contains(";")) {
                            String[] vs = variable.split(";");
                            List<Material> materials = new ArrayList<>();
                            for (String v : vs) {
                                materials.add(Material.getMaterial(v));
                            }
                            for (Material m : materials) {
                                if (m.equals(material)) {
                                    PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                                    return;
                                }
                            }
                        }
                        //如果就是指定一种方块
                        Material m = Material.getMaterial(variable);
                        if (m == null) {
//                    Bukkit.getLogger().info("材料名称错误: "+variable);
                            continue;
                        }
                        if (m.equals(material)) {
                            PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                            return;
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack==null){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Bukkit.getScheduler().runTaskAsynchronously(PWLifeSteal.getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<Integer, Quest> quests = PlayerLevelManager.getPlayerNowQuests(player);
                for (Quest quest : quests.values()) {
                    QuestType questType = quest.getQuestType();
                    if (questType.equals(QuestType.CRAFT)) {
                        String variable = quest.getQuestVariable();
                        int nowProgress = quest.getNowProgress();
                        Material m = Material.getMaterial(variable);
                        if (m == null) {
                            Bukkit.getLogger().info("合成任务材料名称错误: " + variable);
                            continue;
                        }
                        if (m.equals(itemStack.getType())) {
                            PlayerLevelManager.setQuestProgressForPlayer(player, questType, variable, nowProgress + 1);
                            return;
                        }

                    }
                }
            }
        });

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        Entity killer = getKiller(event);
        if (killer != null) {
            //玩家杀死怪物
            Entity dead = event.getEntity();
            if (!dead.getType().equals(EntityType.PLAYER) && killer.getType().equals(EntityType.PLAYER)) {
                Player attacker = (Player) killer;
                Bukkit.getScheduler().runTaskAsynchronously(PWLifeSteal.getPlugin(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        HashMap<Integer, Quest> quests = PlayerLevelManager.getPlayerNowQuests(attacker);
                        for (Quest quest : quests.values()) {
                            QuestType questType = quest.getQuestType();
                            if (questType.equals(QuestType.KILL_MOB)) {
                                String variable = quest.getQuestVariable();
                                int nowProgress = quest.getNowProgress();
                                EntityType entityType = EntityType.valueOf(variable);
                                if (entityType.equals(dead.getType())) {
                                    PlayerLevelManager.setQuestProgressForPlayer(attacker, questType, variable, nowProgress + 1);
                                    return;
                                }

                            }
                        }
                    }
                });
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        Entity killer = getKiller(event);
        //玩家杀死玩家
        if (killer!=null) {
            if (killer.getType().equals(EntityType.PLAYER)) {
                Player attacker = (Player) killer;
                Player victim = event.getEntity();
                Bukkit.getScheduler().runTaskAsynchronously(PWLifeSteal.getPlugin(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        HashMap<Integer, Quest> quests = PlayerLevelManager.getPlayerNowQuests(attacker);
                        for (Quest quest : quests.values()) {
                            QuestType questType = quest.getQuestType();
                            if (questType.equals(QuestType.KILL_PLAYER)) {
                                String variable = quest.getQuestVariable();
                                int nowProgress = quest.getNowProgress();
                                PlayerLevelManager.setQuestProgressForPlayer(attacker, questType, variable, nowProgress + 1);
                                return;
                            }


                        }
                    }
                });

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


}
