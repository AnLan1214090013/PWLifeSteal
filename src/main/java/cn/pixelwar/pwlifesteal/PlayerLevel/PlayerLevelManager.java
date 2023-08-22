package cn.pixelwar.pwlifesteal.PlayerLevel;

import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.LevelDoneEvent;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author pyf
 * @description
 */

public class PlayerLevelManager {

    public static HashMap<Player , Level> playerLevelHashMap = new HashMap<>();
    public static HashMap<Player , Integer> playerLevelNumHashMap = new HashMap<>();
    public static HashMap<Player , Boolean> isPremiumMap = new HashMap<>();

    public static void setNewLevelForPlayer(Player player, int level){
        Level l = ServerLevelManager.allLevels.get(level);
        playerLevelHashMap.put(player, l);
        playerLevelNumHashMap.put(player, level);
    }

    public static void setQuestProgressForPlayer(Player player, QuestType questType, String questVariable, int nowProgress){
        Level nowLevel = playerLevelHashMap.get(player);
        List<Quest> questList = nowLevel.getQuests();
        List<Quest> newQuestList = new ArrayList<>();
        for (Quest quest : questList){
            QuestType qt = quest.getQuestType();
            String qv = quest.getQuestVariable();
            if (qt.equals(questType) && qv.equals(questVariable)){
                if (quest.getNeedProgress() > quest.getNowProgress()) {
                    quest.setNowProgress(nowProgress);
                    //如果这一等级已经完成
                    if (checkLevelIsDone(player, playerLevelNumHashMap.get(player))){
                        LevelDoneEvent event = new LevelDoneEvent(
                                false,
                                playerLevelNumHashMap.get(player),
                                playerLevelNumHashMap.get(player)+1,
                                player,
                                false
                                );
                        Bukkit.getPluginManager().callEvent(event);
                    }
                }
            }
            newQuestList.add(quest);
        }
        nowLevel.setQuests(newQuestList);
        playerLevelHashMap.put(player, nowLevel);
    }

    public static boolean checkLevelIsDone(Player player, int level){
        int nowLevelNum = playerLevelNumHashMap.get(player);
        if (level!=nowLevelNum)return false;

        Level nowLevel = playerLevelHashMap.get(player);
        List<Quest> questList = nowLevel.getQuests();
        boolean isDone = true;
        for (Quest quest : questList){
            if (quest.getNeedProgress() != quest.getNowProgress()){
                isDone = false;
            }
        }
        return isDone;
    }


}
