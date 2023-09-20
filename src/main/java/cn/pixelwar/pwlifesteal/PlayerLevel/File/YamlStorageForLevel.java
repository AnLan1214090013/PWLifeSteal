package cn.pixelwar.pwlifesteal.PlayerLevel.File;

import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerLevel.Level;
import cn.pixelwar.pwlifesteal.PlayerLevel.PlayerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import cn.pixelwar.pwlifesteal.PlayerLevel.Reward.Reward;
import cn.pixelwar.pwlifesteal.PlayerLevel.ServerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
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
            } catch (IOException ex) {
            }
        }
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {

        }
        config.set("Name", player.getName());
        try {
            config.save(dataFile);
        } catch (IOException ex) {
        }
        //如果是第一次加入就弄一个默认的data
        if (firstJoin) {
            PlayerLevelManager.setDefaultPlayerLevel(player);
        }
        return firstJoin;
    }

    public void initLevelSystem() {
//        File dataFile = new File("plugins/PWLifeSteal/levels-settings.yml");
        FileConfiguration levelConfig = PWLifeSteal.getInstance().getLevelConfig();
        ConfigurationSection levelNumSection = levelConfig.getConfigurationSection("levels");
        for (String levelNum : levelNumSection.getKeys(false)) {
            Level level;
//            Bukkit.getLogger().info("levelNum : "+levelNum);
            ConfigurationSection questsSection = levelConfig.getConfigurationSection("levels." + levelNum + ".quests");
//            List<String> questsSection =  levelConfig.getStringList("levels."+levelNum+".quests");
            HashMap<Integer, Quest> quests = new HashMap<>();
            for (String questNum : questsSection.getKeys(false)) {
//                Bukkit.getLogger().info("quest : "+questType);
                Quest quest = null;
                int needProgress = levelConfig.getInt("levels." + levelNum + ".quests." + questNum + ".needProgress");
                String variable = levelConfig.getString("levels." + levelNum + ".quests." + questNum + ".variable");
                String name = levelConfig.getString("levels." + levelNum + ".quests." + questNum + ".name");
                String questType = levelConfig.getString("levels." + levelNum + ".quests." + questNum + ".type");

                if (variable != null) {
                    quest = new Quest(name, needProgress, 0, QuestType.getQuestTypeByName(questType), variable);
                } else {
                    quest = new Quest(name, needProgress, 0, QuestType.getQuestTypeByName(questType));
                }
                quests.put(Integer.parseInt(questNum), quest);
            }
            ConfigurationSection commonRewardsSection = levelConfig.getConfigurationSection("levels." + levelNum + ".commonRewards");
            List<Reward> commonRewardsList = new ArrayList<>();
            for (String commonRewardNum : commonRewardsSection.getKeys(false)) {
                Reward commonReward = null;
                String desc = levelConfig.getString("levels." + levelNum + ".commonRewards." + commonRewardNum + ".desc");
                String type = levelConfig.getString("levels." + levelNum + ".commonRewards." + commonRewardNum + ".type");
                ;
                int amount = levelConfig.getInt("levels." + levelNum + ".commonRewards." + commonRewardNum + ".amount");
                ;
                String variable = levelConfig.getString("levels." + levelNum + ".commonRewards." + commonRewardNum + ".variable");

                if (variable != null) {
                    commonReward = new Reward(desc, type, amount, variable);
                } else {
                    commonReward = new Reward(desc, type, amount);
                }
                commonRewardsList.add(commonReward);
            }

            ConfigurationSection premiumRewardsSection = levelConfig.getConfigurationSection("levels." + levelNum + ".premiumRewards");
            List<Reward> premiumRewardsList = new ArrayList<>();
            for (String premiumRewardNum : premiumRewardsSection.getKeys(false)) {
                Reward premiumReward = null;
                String desc = levelConfig.getString("levels." + levelNum + ".premiumRewards." + premiumRewardNum + ".desc");
                String type = levelConfig.getString("levels." + levelNum + ".premiumRewards." + premiumRewardNum + ".type");
                ;
                int amount = levelConfig.getInt("levels." + levelNum + ".premiumRewards." + premiumRewardNum + ".amount");
                ;
                String variable = levelConfig.getString("levels." + levelNum + ".premiumRewards." + premiumRewardNum + ".variable");

                if (variable != null) {
                    premiumReward = new Reward(desc, type, amount, variable);
                } else {
                    premiumReward = new Reward(desc, type, amount);
                }
                premiumRewardsList.add(premiumReward);
            }

            level = new Level(quests, commonRewardsList, premiumRewardsList);
            ServerLevelManager.allLevels.put(Integer.parseInt(levelNum), level);


        }


    }

    public void createPlayerLevelData(String playerName) {
        File dataFolder = new File("plugins/PWLifeSteal/PlayerLevels");
        File dataFile = new File("plugins/PWLifeSteal/PlayerLevels/" + playerName + ".yml");
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {
        }

        //加载premium信息
        boolean isPremium = config.isSet("premium.isPremium") ? config.getBoolean("premium.isPremium") : false;
        List<Integer> gotRewards = config.isSet("premium.premiumRewardGetList") ? (List<Integer>) config.getList("premium.premiumRewardGetList") : new ArrayList<>();
        PlayerLevelManager.premiumRewardGetMap.put(playerName, gotRewards);
        PlayerLevelManager.isPremiumMap.put(playerName, isPremium);

        //加载level信息
        int levelNum = config.isSet("level.num") ? config.getInt("level.num") : 1;
        //如果已经达到最高等级
        if (levelNum > ServerLevelManager.allLevels.size()) {
            PlayerLevelManager.playerLevelNumHashMap.put(playerName, levelNum);
        } else {
            PlayerLevelManager.playerLevelNumHashMap.put(playerName, levelNum);

            HashMap<Integer, Quest> quests = new HashMap<>();
            Level level = ServerLevelManager.allLevels.get(levelNum);

            Level serverLevel = ServerLevelManager.allLevels.get(levelNum);
            if (config.contains("level.quests")) {
                ConfigurationSection questsSection = config.getConfigurationSection("level.quests");
                for (String questNum : questsSection.getKeys(false)) {
                    Quest quest = null;
                    int num = Integer.parseInt(questNum);
                    int needProgress = serverLevel.getQuests().get(num).getNeedProgress();
                    int nowProgress = config.isSet("level.quests." + num + ".progress") ? config.getInt("level.quests." + num + ".progress") : 0;
                    String variable = serverLevel.getQuests().get(num).getQuestVariable();
                    String name = serverLevel.getQuests().get(num).getQuestName();
                    QuestType questType = serverLevel.getQuests().get(num).getQuestType();

                    if (variable != null) {
                        quest = new Quest(name, needProgress, nowProgress, questType, variable);
                    } else {
                        quest = new Quest(name, needProgress, nowProgress, questType);
                    }
                    quests.put(num, quest);
                }
                level = new Level(quests, serverLevel.getCommonRewards(), serverLevel.getPremiumRewards());
            }

            PlayerLevelManager.playerLevelHashMap.put(playerName, level);
        }


    }

    public void savePlayerLevelData(String playerName) {
        File dataFolder = new File("plugins/PWLifeSteal/PlayerLevels");
        File dataFile = new File("plugins/PWLifeSteal/PlayerLevels/" + playerName + ".yml");
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {
        }

        //保存premium信息
        config.set("premium", null);
        if (PlayerLevelManager.isPremiumMap.containsKey(playerName)) {
            config.set("premium.isPremium", PlayerLevelManager.isPremiumMap.get(playerName));
        }
        if (PlayerLevelManager.premiumRewardGetMap.containsKey(playerName)) {
            config.set("premium.premiumRewardGetList", PlayerLevelManager.premiumRewardGetMap.get(playerName));
        }
        //保存level信息
        config.set("level", null);
        if (PlayerLevelManager.playerLevelNumHashMap.containsKey(playerName)) {
            config.set("level.num", PlayerLevelManager.playerLevelNumHashMap.get(playerName));
        } else {
            config.set("level.num", 1);
        }
        if (PlayerLevelManager.playerLevelHashMap.containsKey(playerName)) {
            Level level = PlayerLevelManager.playerLevelHashMap.get(playerName);
            HashMap<Integer, Quest> quests = level.getQuests();
            quests.forEach((num, quest) -> {
                config.set("level.quests." + num + ".progress", quest.getNowProgress());
            });
        }
        try {
            config.save(dataFile);
        } catch (IOException ex) {
            System.out.println("玩家" + playerName + "的信息保存出错");
        }
    }

}
