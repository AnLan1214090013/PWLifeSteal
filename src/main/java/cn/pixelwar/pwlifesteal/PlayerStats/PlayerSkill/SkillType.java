package cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill;

import java.util.List;

import static java.util.Arrays.asList;

public enum SkillType {


    /*
    技能树的结构:








              LESS_DAMAGE_FROM_PLAYER - LESS_DAMAGE_FROM_PLAYER - LESS_DAMAGE_FROM_MOB
                                                    |
                                           LESS_DAMAGE_FROM_MOB
     */

//    DODGE_DAMAGE_FOR_PLAYER(asList(
//            ChatColorCast.format("&7有几率点燃你周围的区域来保护你"),
//            ChatColorCast.format("&7同时你获得抗火效果")
//    )
//    ),
//    DODGE_DAMAGE_FOR_MOB,
//
//    LESS_DAMAGE_FROM_PLAYER,
//    LESS_DAMAGE_FROM_MOB,
//
//    DODGE_FALL_DAMAGE,
//
//    DOUBLE_DAMAGE_FOR_PLAYER,
//    TRIPLE_DAMAGE_FOR_MOB,
//
//    DOUBLE_EXP_FROM_KILL_MOB,
//    DOUBLE_MONEY_FROM_KILL_PLAYER,
//    DOUBLE_HEARTS_FROM_KILL_PLAYER,
//    TRIPLE_EXP_FROM_KILL_PLAYER,
//
//    LOW_HEALTH_RESISTANCE,
//    LOW_HEALTH_JUMP,
//    LOW_HEALTH_STRENGTH,
//    LOW_HEALTH_SPEED,
//
//    KNOCKBACK_PLAYER,
//    KNOCKBACK_MOB,
//
//    KILLSTREAK_GET_HEAL,


    ;
    private List<SkillType> connectedSkills;
    private List<String> description;
    private String displayName;
    private double defaultChance;
    private double eachLevelChance;
    private int maxLevel;
    private int prize;

    SkillType(List<SkillType> connectedSkills, List<String> description, String displayName, double defaultChance, double eachLevelChance, int maxLevel, int prize) {
        this.connectedSkills = connectedSkills;
        this.description = description;
        this.displayName = displayName;
        this.defaultChance = defaultChance;
        this.eachLevelChance = eachLevelChance;
        this.maxLevel = maxLevel;
        this.prize = prize;
    }

    public List<SkillType> getConnectedSkills() {
        return connectedSkills;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDefaultChance() {
        return defaultChance;
    }

    public double getEachLevelChance() {
        return eachLevelChance;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public List<String> getDescription() {
        return description;
    }

    public int getPrize() {
        return prize;
    }
}
