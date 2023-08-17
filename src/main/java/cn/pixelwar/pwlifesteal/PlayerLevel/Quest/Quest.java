package cn.pixelwar.pwlifesteal.PlayerLevel.Quest;

public class Quest {

    private String questName;
    private int needProgress;
    private int nowProgress;
    private QuestType questType;
    private String questVariable;

    public Quest(String questName, int needProgress, int nowProgress, QuestType questType, String questVariable) {
        this.questName = questName;
        this.needProgress = needProgress;
        this.nowProgress = nowProgress;
        this.questType = questType;
        this.questVariable = questVariable;
    }
    public Quest(String questName, int needProgress, int nowProgress, QuestType questType) {
        this.questName = questName;
        this.needProgress = needProgress;
        this.nowProgress = nowProgress;
        this.questType = questType;
        this.questVariable = null;
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

    public String getQuestVariable() {
        return questVariable;
    }

    public void setQuestVariable(String questVariable) {
        this.questVariable = questVariable;
    }
}
