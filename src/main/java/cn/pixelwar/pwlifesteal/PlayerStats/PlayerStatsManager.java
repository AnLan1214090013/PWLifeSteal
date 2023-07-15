package cn.pixelwar.pwlifesteal.PlayerStats;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerStatsManager {


    //<playerName, STAT>
    public static ConcurrentHashMap<String, PlayerStat> playerStatMap = new ConcurrentHashMap<>();

    public static void setPlayerMaxHearts(Player player, double amount){
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setMaxHearts(amount);
        playerStatMap.put(player.getName(), playerStat);
    }
    public static void addPlayerKills(Player player){
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setKill(playerStat.getKill()+1);
        playerStatMap.put(player.getName(), playerStat);
    }
    public static void addPlayerDeaths(Player player){
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setDeath(playerStat.getDeath()+1);
        playerStatMap.put(player.getName(), playerStat);
    }
    public static void addPlayerKillStreak(Player player){
        PlayerStat playerStat = playerStatMap.get(player.getName());
        playerStat.setKillStreak(playerStat.getKillStreak()+1);
        playerStatMap.put(player.getName(), playerStat);
    }

    public static void levelUpSkill(Player player, SkillType skillType){
        //如果玩家已经有了这个技能
        if (playerStatMap.get(player.getName()).getSkillStat().containsKey(skillType)){
            //如果没有达到最高级
            if(playerStatMap.get(player.getName()).getSkillStat().get(skillType)< skillType.getMaxLevel()){
                int nowLevel = playerStatMap.get(player.getName()).getSkillStat().get(skillType);
                playerStatMap.get(player.getName()).getSkillStat().put(skillType, nowLevel+1);
            }
        }else{
            playerStatMap.get(player.getName()).getSkillStat().put(skillType, 1);
        }
    }


}
