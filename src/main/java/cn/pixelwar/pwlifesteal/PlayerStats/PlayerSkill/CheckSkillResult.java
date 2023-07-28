package cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill;

public class CheckSkillResult {

    public boolean hasSkill;
    public double chance;

    public CheckSkillResult(boolean hasSkill, double chance) {
        this.hasSkill = hasSkill;
        this.chance = chance;
    }

    public boolean isHasSkill() {
        return hasSkill;
    }

    public double getChance() {
        return chance;
    }
}
