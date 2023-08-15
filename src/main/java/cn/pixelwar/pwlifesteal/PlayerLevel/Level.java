package cn.pixelwar.pwlifesteal.PlayerLevel;


import cn.pixelwar.pwlifesteal.PlayerLevel.Reward.Reward;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;

import java.util.List;

public class Level {


    private List<Quest> quests;
    private List<Reward> commonRewards;
    private List<Reward> premiumRewards;
    public Level(){

    }
    public Level(List<Quest> quests, List<Reward> commonRewards, List<Reward> premiumRewards) {
        this.quests = quests;
        this.commonRewards = commonRewards;
        this.premiumRewards = premiumRewards;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public List<Reward> getCommonRewards() {
        return commonRewards;
    }

    public void setCommonRewards(List<Reward> commonRewards) {
        this.commonRewards = commonRewards;
    }

    public List<Reward> getPremiumRewards() {
        return premiumRewards;
    }

    public void setPremiumRewards(List<Reward> premiumRewards) {
        this.premiumRewards = premiumRewards;
    }
}
