package cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill;

import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;

import java.util.List;

import static java.util.Arrays.asList;

public enum SkillType {


    /*
    技能树的结构
    https://github.com/AnLan1214090013/PWLifeSteal/blob/master/src/main/resources/SKILLTREE.png
     */

    DODGE_DAMAGE_FROM_PLAYER(
            asList(
                    "LESS_DAMAGE_FROM_PLAYER",
                    "DOUBLE_DAMAGE_FOR_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率躲避一次玩家的攻击")
            ),
            "PVP闪避",
            0.5,
            0.1,
            10,
            120,
            10
    ),
    DODGE_DAMAGE_FROM_MOB(
            asList(
                    "LESS_DAMAGE_FROM_PLAYER",
                    "TRIPLE_DAMAGE_FOR_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率躲避一次怪物的攻击")
            ),
            "PVE闪避",
            0.8,
            0.15,
            10,
            120,
            10
    ),

    LESS_DAMAGE_FROM_PLAYER(
            asList(
                    "DODGE_DAMAGE_FROM_PLAYER",
                    "LESS_DAMAGE_FROM_MOB",
                    "DODGE_DAMAGE_FROM_MOB"
            ),
            asList(
                    ChatColorCast.format("&7有几率减少&6一半&7受到来自玩家的伤害")
            ),
            "PVP防御",
            1,
            0.2,
            5,
            80,
            10
    ),
    LESS_DAMAGE_FROM_MOB(
            asList(
                    "LESS_DAMAGE_FROM_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率减少&6一半&7受到来自怪物的伤害")
            ),
            "PVE防御",
            1.5,
            0.2,
            5,
            80,
            10
    ),

    DODGE_FALL_DAMAGE(
            asList(
                    "LESS_DAMAGE_FROM_PLAYER",
                    "LOW_HEALTH_RESISTANCE"
            ),
            asList(
                    ChatColorCast.format("&7有几率坠落时不受伤害")
            ),
            "轻功",
            2,
            0.5,
            10,
            140,
            10
    ),

    DOUBLE_DAMAGE_FOR_PLAYER(
            asList(
                    "DODGE_DAMAGE_FROM_PLAYER",
                    "KNOCKBACK_PLAYER",
                    "DOUBLE_MONEY_FROM_KILL_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率对玩家造成双倍伤害")
            ),
            "PVP双倍伤害",
            0.1,
            0.1,
            10,
            160,
            10
    ),
    TRIPLE_DAMAGE_FOR_MOB(
            asList(
                    "DODGE_DAMAGE_FROM_MOB",
                    "KNOCKBACK_MOB",
                    "DOUBLE_EXP_FROM_KILL_MOB"
            ),
            asList(
                    ChatColorCast.format("&7有几率对怪物造成三倍伤害")
            ),
            "PVE三倍伤害",
            0.2,
            0.2,
            10,
            160,
            10
    ),

    DOUBLE_EXP_FROM_KILL_MOB(
            asList(
                    "TRIPLE_DAMAGE_FOR_MOB"
            ),
            asList(
                    ChatColorCast.format("&7有几率击杀怪物时获得双倍经验")
            ),
            "PVE双倍经验",
            0.5,
            0.5,
            10,
            180,
            20
    ),
    DOUBLE_MONEY_FROM_KILL_PLAYER(
            asList(
                    "DOUBLE_DAMAGE_FOR_PLAYER",
                    "DOUBLE_HEARTS_FROM_KILL_PLAYER",
                    "TRIPLE_EXP_FROM_KILL_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率击杀玩家时获得双倍金钱")
            ),
            "PVP双倍金钱",
            0.5,
            0.5,
            10,
            180,
            20
    ),
    DOUBLE_HEARTS_FROM_KILL_PLAYER(
            asList(
                    "DOUBLE_MONEY_FROM_KILL_PLAYER",
                    "KILLSTREAK_GET_HEAL"
            ),
            asList(
                    ChatColorCast.format("&7有几率击杀玩家时获得双倍生命")
            ),
            "PVP双倍生命",
            0.5,
            0.5,
            10,
            220,
            25
    ),
    TRIPLE_EXP_FROM_KILL_PLAYER(
            asList(
                    "DOUBLE_DAMAGE_FOR_PLAYER",
                    "DOUBLE_HEARTS_FROM_KILL_PLAYER",
                    "TRIPLE_EXP_FROM_KILL_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率击杀玩家时获得三倍经验")
            ),
            "PVP三倍经验",
            0.5,
            0.5,
            10,
            200,
            20
    ),

    LOW_HEALTH_RESISTANCE,
    LOW_HEALTH_JUMP,
    LOW_HEALTH_STRENGTH,
    LOW_HEALTH_SPEED,

    KNOCKBACK_PLAYER,
    KNOCKBACK_MOB,

    KILLSTREAK_GET_HEAL(
            asList(
                    "DOUBLE_HEARTS_FROM_KILL_PLAYER"
            ),
            asList(
                    ChatColorCast.format("&7有几率在连杀时被完全治愈")
            ),
            "狂暴",
            1,
            0.5,
            10,
            220,
            25
    ),


    ;
    private List<String> connectedSkills;
    private List<String> description;
    private String displayName;
    private double defaultChance;
    private double eachLevelChance;
    private int maxLevel;
    private int unlockPrize;
    private int eachLevelAddPrize;




    SkillType(List<String> connectedSkills, List<String> description, String displayName, double defaultChance, double eachLevelChance, int maxLevel, int unlockPrize, int eachLevelAddPrize) {
        this.connectedSkills = connectedSkills;
        this.description = description;
        this.displayName = displayName;
        this.defaultChance = defaultChance;
        this.eachLevelChance = eachLevelChance;
        this.maxLevel = maxLevel;
        this.unlockPrize = unlockPrize;
        this.eachLevelAddPrize = eachLevelAddPrize;
    }

    public List<String> getConnectedSkills() {
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

    public int getUnlockPrize() {
        return unlockPrize;
    }

    public int getEachLevelAddPrize() {
        return eachLevelAddPrize;
    }
}
