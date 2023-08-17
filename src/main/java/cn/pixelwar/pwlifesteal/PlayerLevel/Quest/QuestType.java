package cn.pixelwar.pwlifesteal.PlayerLevel.Quest;

public enum QuestType {
    KILL_MOB,
    BREAK


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
