package cn.pixelwar.pwlifesteal.PlayerLevel.Quest;

public enum QuestType {
    KILL_MOB,
    BREAK,
    CRAFT,
    PLACE,
    KILL_PLAYER,

    ;


    public static QuestType getQuestTypeByName(String name){
        for (QuestType questType : QuestType.values()){
            if (questType.toString().equals(name)){
                return questType;
            }
        }
        return null;
    }
}
