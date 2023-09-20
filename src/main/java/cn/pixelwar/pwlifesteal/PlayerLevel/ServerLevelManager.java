package cn.pixelwar.pwlifesteal.PlayerLevel;

import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import cn.pixelwar.pwlifesteal.PlayerLevel.Reward.Reward;

import java.util.HashMap;
import java.util.List;

public class ServerLevelManager {

    //这里存储所有的level<等级，level>
    public static HashMap<Integer, Level> allLevels = new HashMap<>();


//    public static Level createLevel(List<Quest> quests, List<Reward> commonRewards, List<Reward> premiumRewards){
//        Level level = new Level(quests, commonRewards, premiumRewards);
//        return level;
//    }
//
//    public static Quest createNewQuest(int needProgress, int nowProgress, QuestType questType, String questVariable){
//        return new Quest(needProgress, nowProgress, questType, questVariable);
//    }


}
