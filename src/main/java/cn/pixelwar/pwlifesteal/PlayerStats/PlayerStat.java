package cn.pixelwar.pwlifesteal.PlayerStats;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.PlayerSkill;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerStat {

    private UUID uuid;
    private double hearts;
    private double maxHearts;
    private int kill;
    private int killStreak;
    private int death;
    private HashMap<String, Location> homes;
    private int maxHome;
    private int tpTime;

    //小时
    private int banTime;
    private HashMap<SkillType, PlayerSkill> skillStat;

    public PlayerStat(UUID uuid, double hearts, double maxHearts, int kill, int killStreak, int death, HashMap<String, Location> homes, int maxHome, int tpTime, int banTime, HashMap<SkillType, PlayerSkill> skillStat) {
        this.uuid = uuid;
        this.hearts = hearts;
        this.maxHearts = maxHearts;
        this.kill = kill;
        this.killStreak = killStreak;
        this.death = death;
        this.homes = homes;
        this.maxHome = maxHome;
        this.tpTime = tpTime;
        this.banTime = banTime;
        this.skillStat = skillStat;
    }

    public PlayerStat(){}

    public void setDefaultStat(Player player){
        this.uuid = player.getUniqueId();
        this.hearts = 20;
        this.maxHearts = 20;
        this.kill = 0;
        this.killStreak = 0;
        this.death = 0;
        this.homes = null;
        //默认最多1个家
        this.maxHome = 1;
        //默认10s的传送时间
        this.tpTime = 10;
        //默认ban2天
        this.banTime = 48;
        this.skillStat = null;
    }

    public void setBanTime(int banTime) {
        this.banTime = banTime;
    }

    public int getBanTime() {
        return banTime;
    }

    public void setSkillStat(HashMap<SkillType, PlayerSkill> skillStat) {
        this.skillStat = skillStat;
    }

    public HashMap<SkillType, PlayerSkill> getSkillStat() {
        return skillStat;
    }

    public void addMaxHome(Player player, int amount){
        this.maxHome += amount;
    }

    public void removeTPTime(Player player, int amount){
        this.tpTime = this.tpTime - amount;
        if (this.tpTime<3){
            this.tpTime = 3;
        }
    }
    public UUID getUUID() {
        return uuid;
    }

    public double getHearts() {
        return hearts;
    }

    public double getMaxHearts() {
        return maxHearts;
    }

    public int getKill() {
        return kill;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public int getDeath() {
        return death;
    }

    public HashMap<String, Location> getHomes() {
        return homes;
    }

    public int getMaxHome() {
        return maxHome;
    }

    public int getTpTime() {
        return tpTime;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setHearts(double hearts) {
        this.hearts = hearts;
    }

    public void setMaxHearts(double maxHearts) {
        this.maxHearts = maxHearts;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public void setHomes(HashMap<String, Location> homes) {
        this.homes = homes;
    }

    public void setMaxHome(int maxHome) {
        this.maxHome = maxHome;
    }

    public void setTpTime(int tpTime) {
        this.tpTime = tpTime;
    }
}
