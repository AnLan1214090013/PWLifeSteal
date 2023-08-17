package cn.pixelwar.pwlifesteal.File;

import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerLevel.Level;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import cn.pixelwar.pwlifesteal.PlayerLevel.Reward.Reward;
import cn.pixelwar.pwlifesteal.PlayerLevel.ServerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStat;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.Teleport.Teleport;
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
import java.util.*;


public class YamlStorageForLevel {

    private final FileConfiguration config = new YamlConfiguration();
    
    public boolean CheckYamlFile(Player player) {

        String playerName = player.getName();
        boolean firstJoin = false;
        boolean isExist = true;

        File dataFolder = new File("plugins/PWLifeSteal/PlayerLevels");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File dataFile = new File("plugins/PWLifeSteal/PlayerLevels/" + playerName + ".yml");
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

    public void initLevelSystem(){
//        File dataFile = new File("plugins/PWLifeSteal/levels-settings.yml");
        FileConfiguration levelConfig = PWLifeSteal.getInstance().getLevelConfig();
        ConfigurationSection levelNumSection =  levelConfig.getConfigurationSection("levels");
        for (String levelNum : levelNumSection.getKeys(false)){
            Level level;
//            Bukkit.getLogger().info("levelNum : "+levelNum);
            ConfigurationSection questsSection =  levelConfig.getConfigurationSection("levels."+levelNum+".quests");
//            List<String> questsSection =  levelConfig.getStringList("levels."+levelNum+".quests");
            List<Quest> quests = new ArrayList<>();
            for (String questNum : questsSection.getKeys(false)){
//                Bukkit.getLogger().info("quest : "+questType);
                Quest quest = null;
                int needProgress = levelConfig.getInt("levels."+levelNum+".quests."+questNum+".needProgress");
                String variable = levelConfig.getString("levels."+levelNum+".quests."+questNum+".variable");
                String name = levelConfig.getString("levels."+levelNum+".quests."+questNum+".name");
                String questType = levelConfig.getString("levels."+levelNum+".quests."+questNum+".questType");

                if (variable!=null){
                    quest = new Quest(name, needProgress, 0, QuestType.getQuestTypeByName(questType), variable);
                }else{
                    quest = new Quest(name, needProgress, 0, QuestType.getQuestTypeByName(questType));
                }
                quests.add(quest);
            }
            List<String> commonRewardsStringList =  levelConfig.getStringList("levels."+levelNum+".commonRewards");
            List<Reward> commonRewardsList = new ArrayList<>();
            for (String commonRewardString : commonRewardsStringList){
                Reward commonReward = null;
                String[] strings = commonRewardString.split(";");
                //no variable
                if (strings.length==2){
                    commonReward = new Reward(strings[0], Integer.parseInt(strings[1]));
                }
                if (strings.length==3){
                    commonReward = new Reward(strings[0], Integer.parseInt(strings[2]), strings[1]);
                }
                if (commonReward!=null) {
                    commonRewardsList.add(commonReward);
                }
            }
            List<String> premiumRewardsStringList =  levelConfig.getStringList("levels."+levelNum+".premiumRewards");
            List<Reward> premiumRewardsList = new ArrayList<>();
            for (String premiumRewardString : premiumRewardsStringList){
                Reward premiumReward = null;
                String[] strings = premiumRewardString.split(";");
                //no variable
                if (strings.length==2){
                    premiumReward = new Reward(strings[0], Integer.parseInt(strings[1]));
                }
                if (strings.length==3){
                    premiumReward = new Reward(strings[0], Integer.parseInt(strings[2]), strings[1]);
                }
                if (premiumReward!=null) {
                    commonRewardsList.add(premiumReward);
                }
            }
            level= new Level(quests, commonRewardsList, premiumRewardsList);
            ServerLevelManager.allLevels.put(Integer.parseInt(levelNum), level );


        }



    }


}
