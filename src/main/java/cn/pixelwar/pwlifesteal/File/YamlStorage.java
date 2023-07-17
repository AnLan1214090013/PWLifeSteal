package cn.pixelwar.pwlifesteal.File;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStat;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.UUID;


public class YamlStorage {

    private final FileConfiguration config = new YamlConfiguration();
    
    public boolean CheckYamlFile(Player player) {

        String playerName = player.getName();
        boolean firstJoin = false;
        boolean isExist = true;

        File dataFolder = new File("plugins/PWLifeSteal/Players");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File dataFile = new File("plugins/PWLifeSteal/Players/" + playerName + ".yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                isExist = false;
                firstJoin = true;
            } catch (IOException ex) {}
        }
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {

        }
        config.set("Name", player.getName());
        try{
        config.save(dataFile);}catch (IOException ex){}
        //如果是第一次加入就弄一个默认的data
        if (firstJoin){
            PlayerStat playerStat = new PlayerStat();
            playerStat.setDefaultStat(player);
            PlayerStatsManager.playerStatMap.put(playerName, playerStat);
        }
        return firstJoin;
    }

        public void CreatePlayerDataMap(Player player) {
            File dataFile = new File("plugins/PWLifeSteal/Players/" + player.getName() + ".yml");

            try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
                config.load(Config);
            } catch (IOException | InvalidConfigurationException ex) {
            }


            //加载数据

            //家的数据
            HashMap<String, Location> homes = new HashMap<>();
            if(config.contains("homes")){
                ConfigurationSection homess = config.getConfigurationSection("homes");
                for (String homeName : homess.getKeys(false)) {
                    String[] locStr = config.getString("homes."+homeName).split(";");
                    Location loc = new Location(
                            Bukkit.getWorld(locStr[0]),
                            Double.valueOf(locStr[1]),
                            Double.valueOf(locStr[2]),
                            Double.valueOf(locStr[3]),
                            Float.valueOf(locStr[4]),
                            Float.valueOf(locStr[5])
                    );
                    homes.put(homeName, loc);
                }
            }

            //技能数据
            HashMap<SkillType, Integer> skillStat = new HashMap<>();
            if(config.contains("skills")){
                ConfigurationSection skillss = config.getConfigurationSection("skills");
                for (String skillName : skillss.getKeys(false)) {
                    int level = config.getInt("skills."+skillName);
                    SkillType skillType = SkillType.getSkillType(skillName);
                    skillStat.put(skillType, level);
                }
            }

            UUID uuid = null;
            double hearts = 0;
            double maxHearts = 0;
            int kill = 0;
            int killStreak = 0;
            int ruby = 0;
            int death = 0;
            int maxHome = 0;
            int tpTime = 0;
            int banTime = 0;
            if (config.contains("uuid")){
                uuid = UUID.fromString(config.getString("uuid"));
            }
            if (config.contains("hearts")){
                hearts = config.getDouble("hearts");
            }
            if (config.contains("maxHearts")){
                maxHearts = config.getDouble("maxHearts");
            }
            if (config.contains("kill")){
                kill = config.getInt("kill");
            }
            if (config.contains("killStreak")){
                killStreak = config.getInt("killStreak");
            }
            if (config.contains("ruby")){
                ruby = config.getInt("ruby");
            }
            if (config.contains("death")){
                death = config.getInt("death");
            }
            if (config.contains("maxHome")){
                maxHome = config.getInt("maxHome");
            }
            if (config.contains("tpTime")){
                tpTime = config.getInt("tpTime");
            }
            if (config.contains("banTime")){
                maxHome = config.getInt("banTime");
            }



            PlayerStat playerStat = new PlayerStat(
                    uuid,
                    hearts,
                    maxHearts,
                    kill,
                    killStreak,
                    ruby,
                    death,
                    homes,
                    maxHome,
                    tpTime,
                    banTime,
                    skillStat
            );
            PlayerStatsManager.playerStatMap.put(player.getName(), playerStat);

        }


    public void savePlayerData(Player player){
        String playerName = player.getName();;
        File dataFolder = new File("plugins/PWLifeSteal/Players");
        File dataFile = new File("plugins/PWLifeSteal/Players/" + playerName + ".yml");
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {}

        //保存home
        config.set("homes", null);
        HashMap<String, Location> homeMap = PlayerStatsManager.playerStatMap.get(playerName).getHomes();
        if (homeMap!=null) {
            homeMap.forEach(
                    (name, loc) -> {
                        String locStr = loc.getWorld().getName() + ";" +
                                loc.getX() + ";" +
                                loc.getY() + ";" +
                                loc.getZ() + ";" +
                                loc.getYaw() + ";" +
                                loc.getPitch();
                        config.set("homes." + name, locStr);
                    });
        }
        //保存技能
        config.set("skills", null);
        HashMap<SkillType, Integer> skillStat = PlayerStatsManager.playerStatMap.get(playerName).getSkillStat();
        if (skillStat!=null) {
            skillStat.forEach(
                    (skillType, level) -> {
                        config.set("skills." + skillType.toString(), level);
                    });
        }

        //保存其他
        config.set("uuid", PlayerStatsManager.playerStatMap.get(playerName).getUUID().toString());
        config.set("hearts", player.getHealth()/2);
        config.set("maxHearts", PlayerStatsManager.playerStatMap.get(playerName).getMaxHearts());
        config.set("kill", PlayerStatsManager.playerStatMap.get(playerName).getKill());
        config.set("killStreak", PlayerStatsManager.playerStatMap.get(playerName).getKillStreak());
        config.set("ruby", PlayerStatsManager.playerStatMap.get(playerName).getRuby());
        config.set("death", PlayerStatsManager.playerStatMap.get(playerName).getDeath());
        config.set("tpTime", PlayerStatsManager.playerStatMap.get(playerName).getTpTime());
        config.set("maxHome", PlayerStatsManager.playerStatMap.get(playerName).getMaxHome());
        config.set("banTime", PlayerStatsManager.playerStatMap.get(playerName).getBanTime());
        try{
            config.save(dataFile);}catch (IOException ex){
            System.out.println("玩家"+playerName+"的等级信息保存出错");
        }
    }
    public void saveOffLinePlayerData(String playerName){
        File dataFolder = new File("plugins/PWLifeSteal/Players");
        File dataFile = new File("plugins/PWLifeSteal/Players/" + playerName + ".yml");
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {}

        //保存home
        config.set("homes", null);
        HashMap<String, Location> homeMap = PlayerStatsManager.playerStatMap.get(playerName).getHomes();
        if (homeMap!=null) {
            homeMap.forEach(
                    (name, loc) -> {
                        String locStr = loc.getWorld().getName() + ";" +
                                loc.getX() + ";" +
                                loc.getY() + ";" +
                                loc.getZ() + ";" +
                                loc.getYaw() + ";" +
                                loc.getPitch();
                        config.set("homes." + name, locStr);
                    });
        }
        //保存技能
        config.set("skills", null);
        HashMap<SkillType, Integer> skillStat = PlayerStatsManager.playerStatMap.get(playerName).getSkillStat();
        if (skillStat!=null) {
            skillStat.forEach(
                    (skillType, level) -> {
                        config.set("skills." + skillType.toString(), level);
                    });
        }

        //保存其他
        config.set("uuid", PlayerStatsManager.playerStatMap.get(playerName).getUUID().toString());
        config.set("maxHearts", PlayerStatsManager.playerStatMap.get(playerName).getMaxHearts());
        config.set("kill", PlayerStatsManager.playerStatMap.get(playerName).getKill());
        config.set("killStreak", PlayerStatsManager.playerStatMap.get(playerName).getKillStreak());
        config.set("tpTime", PlayerStatsManager.playerStatMap.get(playerName).getTpTime());
        config.set("death", PlayerStatsManager.playerStatMap.get(playerName).getDeath());
        config.set("tpTime", PlayerStatsManager.playerStatMap.get(playerName).getTpTime());
        config.set("ruby", PlayerStatsManager.playerStatMap.get(playerName).getRuby());
        config.set("maxHome", PlayerStatsManager.playerStatMap.get(playerName).getMaxHome());
        config.set("banTime", PlayerStatsManager.playerStatMap.get(playerName).getBanTime());
        try{
            config.save(dataFile);}catch (IOException ex){
            System.out.println("玩家"+playerName+"的信息保存出错");
        }
    }
    public void saveAllPlayerData(){
        for (String playerName : PlayerStatsManager.playerStatMap.keySet()){
            saveOffLinePlayerData(playerName);
        }
    }
}
