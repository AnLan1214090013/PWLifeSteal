package cn.pixelwar.pwlifesteal.PlayerStats;

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


}
