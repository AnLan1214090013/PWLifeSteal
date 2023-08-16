package cn.pixelwar.pwlifesteal.PlayerLevel.Quest;

public class Quest<T> {

    private String questName;
    private int needProgress;
    private int nowProgress;
    private QuestType questType;
    private T questVariable;

    public Quest(String questName, int needProgress, int nowProgress, QuestType questType, T questVariable) {
        this.questName = questName;
        this.needProgress = needProgress;
        this.nowProgress = nowProgress;
        this.questType = questType;
        this.questVariable = questVariable;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public int getNeedProgress() {
        return needProgress;
    }

    public void setNeedProgress(int needProgress) {
        this.needProgress = needProgress;
    }

    public int getNowProgress() {
        return nowProgress;
    }

    public void setNowProgress(int nowProgress) {
        this.nowProgress = nowProgress;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public T getQuestVariable() {
        return questVariable;
    }

    public void setQuestVariable(T questVariable) {
        this.questVariable = questVariable;
    }
}
