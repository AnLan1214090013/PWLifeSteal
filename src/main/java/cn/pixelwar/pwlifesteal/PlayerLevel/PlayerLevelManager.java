package cn.pixelwar.pwlifesteal.PlayerLevel;

import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.LevelDoneEvent;
import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.QuestProgressEvent;
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

    public static HashMap<String, Level> playerLevelHashMap = new HashMap<>();
    public static HashMap<String, Integer> playerLevelNumHashMap = new HashMap<>();
    public static HashMap<String, Boolean> isPremiumMap = new HashMap<>();

    //<玩家， 已经领过的等级>
    public static HashMap<String, List<Integer>> premiumRewardGetMap = new HashMap<>();

    public static HashMap<Integer, Quest> getPlayerNowQuests(Player player) {
        HashMap<Integer, Quest> quests = PlayerLevelManager.playerLevelHashMap.containsKey(player.getName()) ? PlayerLevelManager.playerLevelHashMap.get(player.getName()).getQuests() : new HashMap<>();
        return quests;
    }

    public static void setNewLevelForPlayer(Player player, int level) {
        //如果超出这个范围
        if (!ServerLevelManager.allLevels.containsKey(level)) {
            playerLevelHashMap.remove(player.getName());
            playerLevelNumHashMap.put(player.getName(), level);
            return;
        }
        Level l = ServerLevelManager.allLevels.get(level);
        playerLevelHashMap.put(player.getName(), l);
        playerLevelNumHashMap.put(player.getName(), level);
    }

    public static void setQuestProgressForPlayer(Player player, QuestType questType, String questVariable, int nowProgress) {
        Level nowLevel = playerLevelHashMap.get(player.getName());
        HashMap<Integer, Quest> questList = nowLevel.getQuests();
        HashMap<Integer, Quest> newQuestList = new HashMap<>();
        questList.forEach((num, quest) -> {
            QuestType qt = quest.getQuestType();
            String qv = quest.getQuestVariable();
            if (qt.equals(questType) && qv.equals(questVariable)) {
                if (quest.getNeedProgress() > quest.getNowProgress()) {
                    quest.setNowProgress(nowProgress);
                    Bukkit.getScheduler().runTask(PWLifeSteal.getPlugin(), () -> {
                        QuestProgressEvent questProgressEvent = new QuestProgressEvent(player, quest, false);
                        Bukkit.getPluginManager().callEvent(questProgressEvent);
                    });
                    //如果这一等级已经完成
                    if (checkLevelIsDone(player, playerLevelNumHashMap.get(player.getName()))) {
                        Bukkit.getScheduler().runTask(PWLifeSteal.getPlugin(), () -> {
                            LevelDoneEvent event = new LevelDoneEvent(
                                    false,
                                    playerLevelNumHashMap.get(player.getName()),
                                    playerLevelNumHashMap.get(player.getName()) + 1,
                                    player,
                                    false
                            );
                            Bukkit.getPluginManager().callEvent(event);
                        });
                    }
                }
            }
            newQuestList.put(num, quest);
        });
        nowLevel.setQuests(newQuestList);
        playerLevelHashMap.put(player.getName(), nowLevel);
    }


    public static boolean checkLevelIsDone(Player player, int level) {
        int nowLevelNum = playerLevelNumHashMap.get(player.getName());
        if (level != nowLevelNum) return false;

        Level nowLevel = playerLevelHashMap.get(player.getName());
        HashMap<Integer, Quest> questList = nowLevel.getQuests();
        boolean isDone = true;
        for (Quest quest : questList.values()) {
            if (quest.getNeedProgress() != quest.getNowProgress()) {
                isDone = false;
            }
        }
        return isDone;
    }

    public static boolean checkGotPremiumReward(Player player, int level) {
        for (int gotLevel : PlayerLevelManager.premiumRewardGetMap.get(player.getName())) {
            if (gotLevel == level) {
                return true;
            }
        }
        return false;
    }

    public static void setDefaultPlayerLevel(Player player) {
        playerLevelHashMap.put(player.getName(), ServerLevelManager.allLevels.get(1));
        playerLevelNumHashMap.put(player.getName(), 1);
        isPremiumMap.put(player.getName(), false);
        List<Integer> gotLevels = new ArrayList<>();
        premiumRewardGetMap.put(player.getName(), gotLevels);
    }

    public static void setPremium(Player player) {
        isPremiumMap.put(player.getName(), true);
    }


}
