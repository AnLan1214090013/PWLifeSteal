package cn.pixelwar.pwlifesteal.PlayerStats;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import cn.pixelwar.pwlifesteal.Utils.Teleport.Teleport;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerStatsManager {


    //<playerName, STAT>
    public static ConcurrentHashMap<String, PlayerStat> playerStatMap = new ConcurrentHashMap<>();

    public static void setPlayerMaxHearts(Player player, double amount) {
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setMaxHearts(amount);
        playerStatMap.put(player.getName(), playerStat);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount * 2);
    }

    public static void addPlayerKills(Player player) {
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setKill(playerStat.getKill() + 1);
        playerStatMap.put(player.getName(), playerStat);
    }

    public static void addPlayerDeaths(Player player) {
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setDeath(playerStat.getDeath() + 1);
        playerStatMap.put(player.getName(), playerStat);
    }

    public static void addPlayerKillStreak(Player player) {
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setKillStreak(playerStat.getKillStreak() + 1);
        playerStatMap.put(player.getName(), playerStat);
    }

    public static void givePlayerRuby(Player player, int amount) {
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setRuby(playerStat.getRuby() + amount);
        playerStatMap.put(player.getName(), playerStat);
    }

    public static void removePlayerRuby(Player player, int amount) {
        PlayerStat playerStat = playerStatMap.get(player.getName());
        int nowAmount = playerStat.getRuby() - amount;
        if (nowAmount < 0) {
            nowAmount = 0;
        }
        playerStat.setRuby(nowAmount);
        playerStatMap.put(player.getName(), playerStat);
    }

    public static void levelUpSkill(Player player, SkillType skillType) {
        //如果玩家已经有了这个技能
        if (playerStatMap.get(player.getName()).getSkillStat().containsKey(skillType)) {
            //如果没有达到最高级
            if (playerStatMap.get(player.getName()).getSkillStat().get(skillType) < skillType.getMaxLevel()) {
                int nowLevel = playerStatMap.get(player.getName()).getSkillStat().get(skillType);
                playerStatMap.get(player.getName()).getSkillStat().put(skillType, nowLevel + 1);
            }
        } else {
            playerStatMap.get(player.getName()).getSkillStat().put(skillType, 1);
        }
    }

    public static void addHome(Player player, String name, Location location) {
        //HashMap<String, Location>
        //如果玩家已经有了这个技能
        HashMap<String, Location> homes = playerStatMap.get(player.getName()).getHomes();
        if (homes.containsKey(name)) {
            player.sendMessage(ChatColorCast.format("&c▸ 这个家已经存在"));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }
        if (homes.size() >= playerStatMap.get(player.getName()).getMaxHome()) {
            player.sendMessage(ChatColorCast.format("&c▸ 家的数量已经达到上限，请升级&aVIP&c来解锁更多上限"));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }
        boolean isRight = name.matches("^[0-9a-zA-Z]+$");
        if (!isRight) {
            player.sendMessage(ChatColorCast.format("&c▸ 请勿在家的名字中使用&e&l中文"));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }
        homes.put(name, location);
        playerStatMap.get(player.getName()).setHomes(homes);
        player.sendMessage(ChatColorCast.format("&a▸ &f设置家&a" + name + "&f成功"));
        player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
    }

    public static void delHome(Player player, String name) {
        //HashMap<String, Location>
        //如果玩家已经有了这个技能
        HashMap<String, Location> homes = playerStatMap.get(player.getName()).getHomes();
        if (!homes.containsKey(name)) {
            player.sendMessage(ChatColorCast.format("&c▸ 请核对名字后再删除"));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }
        homes.remove(name);
        playerStatMap.get(player.getName()).setHomes(homes);
        player.sendMessage(ChatColorCast.format("&a▸ &f删除家&a" + name + "&f成功"));
        player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
    }

    public static void goHome(Player player, String name) {
        //HashMap<String, Location>
        //如果玩家已经有了这个技能
        HashMap<String, Location> homes = playerStatMap.get(player.getName()).getHomes();
        if (!homes.containsKey(name)) {
            player.sendMessage(ChatColorCast.format("&c▸ 名为&e" + name + "&c的家不存在"));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }
        Location location = homes.get(name);
        Teleport teleport = new Teleport();
        teleport.teleportPlayer(player, location);
    }

    public static void listHome(Player player) {
        //HashMap<String, Location>
        //如果玩家已经有了这个技能
        HashMap<String, Location> homes = playerStatMap.get(player.getName()).getHomes();
        player.sendMessage(" ");
        player.sendMessage(" ");
        player.sendMessage(ChatColorCast.format("&b&l" + player.getName() + "&d&l家列表 &f(" + homes.size() + "/" + PlayerStatsManager.playerStatMap.get(player.getName()).getMaxHome() + ")"));
        player.sendMessage(" ");
        AtomicInteger i = new AtomicInteger();
        homes.forEach(
                (key, value) -> {
                    i.getAndIncrement();
                    player.sendMessage(ChatColorCast.format(" &d&l#" + i + " &7▸ &f名字: &e" + key + "&6 | &f坐标: &b" + value.getBlockX() + "," + value.getBlockY() + "," + value.getBlockZ()));
                }
        );
        player.sendMessage(" ");
        player.sendMessage(" ");
    }


}
