package cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import cn.pixelwar.pwlifesteal.Utils.NumberFormat;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class FireSkill {

    public static CheckSkillResult checkSkill(SkillType skillType, Player player){
        HashMap<SkillType, Integer> skillStat = PlayerStatsManager.playerStatMap.get(player.getName()).getSkillStat();
        boolean has = false;
        double chance = 0;
        if (skillStat.containsKey(skillType)){
            has = true;
            chance = skillType.getDefaultChance() + (double) (skillStat.get(skillType) * skillType.getEachLevelChance());
        }
        return new CheckSkillResult(has, chance);
    }
    public static void fireInform(Player player, SkillType skillType){
        player.sendMessage(ChatColorCast.format("&d▸ &f你成功触发了技能: &b&l"+skillType.getDisplayName()));
    }

    //PVE减伤
    public static double doPVE_LESS_DAMAGE(double damage, Player player){
        CheckSkillResult checkSkillResult = checkSkill(SkillType.LESS_DAMAGE_FROM_MOB, player);
        if (!checkSkillResult.hasSkill){
            return damage;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
//            if (random > 5000) {return damage;}
            if (random > checkSkillResult.getChance()*100) {return damage;}
            fireInform(player, SkillType.LESS_DAMAGE_FROM_MOB);
            return damage/2;
        }
    }
    //PVP减伤
    public static double doPVP_LESS_DAMAGE(double damage, Player player){
        CheckSkillResult checkSkillResult = checkSkill(SkillType.LESS_DAMAGE_FROM_PLAYER, player);
        if (!checkSkillResult.hasSkill){
            return damage;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
//            if (random > 5000) {return damage;}
            if (random > checkSkillResult.getChance()*100) {return damage;}
            fireInform(player, SkillType.LESS_DAMAGE_FROM_PLAYER);
            return damage/2;
        }
    }

    //PVE三倍伤
    public static double doPVE_TRIPLE_DAMAGE(double damage, Player player){
        SkillType skillType = SkillType.TRIPLE_DAMAGE_FOR_MOB;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return damage;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return damage;}
            fireInform(player, skillType);
            return damage*3;
        }
    }
    //PVP双倍伤
    public static double doPVP_DOUBLE_DAMAGE(double damage, Player player){
        SkillType skillType = SkillType.DOUBLE_DAMAGE_FOR_PLAYER;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return damage;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return damage;}
            fireInform(player, skillType);
            return damage*2;
        }
    }

    //PVE免伤
    public static boolean doPVE_DODGE_DAMAGE(Player player){
        SkillType skillType = SkillType.DODGE_DAMAGE_FROM_MOB;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return false;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return false;}
//            if (random > 5000) {return false;}
            fireInform(player, skillType);
            return true;
        }
    }

    //坠落免伤
    public static boolean doFALL_DODGE_DAMAGE(Player player){
        SkillType skillType = SkillType.DODGE_FALL_DAMAGE;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return false;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return false;}
//            if (random > 5000) {return false;}
            fireInform(player, skillType);
            return true;
        }
    }
    //死亡不掉最大生命
    public static boolean doDEATH_NO_HEART_LOSE(Player player){
        SkillType skillType = SkillType.DEATH_NO_HEART_LOSE;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return false;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return false;}
//            if (random > 5000) {return false;}
            fireInform(player, skillType);
            return true;
        }
    }
    //击杀获得两颗星
    public static boolean doDOUBLE_HEARTS_FROM_KILL_PLAYER(Player player){
        SkillType skillType = SkillType.DOUBLE_HEARTS_FROM_KILL_PLAYER;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return false;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return false;}
//            if (random > 5000) {return false;}
            fireInform(player, skillType);
            return true;
        }
    }
    //PVE双倍经验
    public static int doDOUBLE_EXP_FROM_KILL_MOB(int exp, Player player){
        SkillType skillType = SkillType.DOUBLE_EXP_FROM_KILL_MOB;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return exp;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return exp;}
//            if (random > 5000) {return false;}
            fireInform(player, skillType);
            return exp*2;
        }
    }
    //PVP3倍经验
    public static int doTRIPLE_EXP_FROM_KILL_PLAYER(int exp, Player player){
        SkillType skillType = SkillType.TRIPLE_EXP_FROM_KILL_PLAYER;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return exp;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return exp;}
//            if (random > 5000) {return false;}
            fireInform(player, skillType);
            return exp*3;
        }
    }

    public static void doLOW_HEALTH_RESISTANCE(Player player){
        SkillType skillType = SkillType.LOW_HEALTH_RESISTANCE;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return;}
            fireInform(player, skillType);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,30*20,1);
            player.addPotionEffect(potionEffect);
        }
    }
    public static void doLOW_HEALTH_JUMP(Player player){
        SkillType skillType = SkillType.LOW_HEALTH_JUMP;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return;}
            fireInform(player, skillType);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.JUMP,30*20,2);
            player.addPotionEffect(potionEffect);
        }
    }
    public static void doLOW_HEALTH_REGENERATION(Player player){
        SkillType skillType = SkillType.LOW_HEALTH_REGENERATION;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return;}
            fireInform(player, skillType);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.REGENERATION,10*20,2);
            player.addPotionEffect(potionEffect);
        }
    }

    public static void doLOW_HEALTH_SPEED(Player player){
        SkillType skillType = SkillType.LOW_HEALTH_SPEED;
        CheckSkillResult checkSkillResult = checkSkill(skillType, player);
        if (!checkSkillResult.hasSkill){
            return;
        }
        else{
            int random = NumberFormat.getRandomInt(0, 10000);
            if (random > checkSkillResult.getChance()*100) {return;}
            fireInform(player, skillType);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED,30*20,3);
            player.addPotionEffect(potionEffect);
        }
    }
}
