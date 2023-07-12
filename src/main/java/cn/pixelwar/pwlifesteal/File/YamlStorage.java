package cn.pixelwar.pwlifesteal.File;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.PlayerSkill;
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
            HashMap<SkillType, PlayerSkill> skillStat = new HashMap<>();

            PlayerStat playerStat = new PlayerStat(
                    UUID.fromString(config.getString("uuid")),
                    config.getDouble("hearts"),
                    config.getDouble("maxHearts"),
                    config.getInt("kill"),
                    config.getInt("killStreak"),
                    config.getInt("death"),
                    homes,
                    config.getInt("maxHome"),
                    config.getInt("tpTime"),
                    config.getInt("banTime"),
                    skillStat
            );
            PlayerStatsManager.playerStatMap.put(player.getName(), playerStat);

        }
//
//        //加载intdata
//        IntDataType[] intDataTypes = IntDataType.values();
//        IntData digData = new IntData();
//        for (IntDataType intDataType : intDataTypes){
//            if (config.contains("intData."+intDataType.toString())){
//                int num = config.getInt("intData."+intDataType.toString());
//                digData.singleMap.put(intDataType, num);
//            }else{
//                digData.singleMap.put(intDataType, intDataType.getDefaultNum());
//            }
//        }
//        IntDataManager.IntDataMap.put(player.getName(), digData);
//
//        //加载doubledata
//        DoubleDataType[] doubleDataTypes = DoubleDataType.values();
//        DoubleData doubleData = new DoubleData();
//        for (DoubleDataType doubleDataType : doubleDataTypes){
//            if (config.contains("doubleData."+doubleDataType.toString())){
//                double num = config.getDouble("doubleData."+doubleDataType.toString());
//                doubleData.singleMap.put(doubleDataType, num);
////                Bukkit.broadcastMessage("存在且加载"+doubleDataType.toString()+": "+num);
//            }else{
//                doubleData.singleMap.put(doubleDataType, doubleDataType.getDefaultNum());
////                Bukkit.broadcastMessage("不存在且加载"+doubleDataType.toString()+": "+doubleDataType.getDefaultNum());
//            }
//        }
//        DoubleDataManager.DoubleDataMap.put(player.getName(), doubleData);
//
//
//
//
//
//        //加载home
//        if(config.contains("homes")){
//            ConfigurationSection homes = config.getConfigurationSection("homes");
//            HomeData homeData = new HomeData();
//
//            for (String homeName : homes.getKeys(false)) {
//                String[] locStr = config.getString("homes."+homeName).split(";");
//                Location loc = new Location(
//                        Bukkit.getWorld(locStr[0]),
//                        Double.valueOf(locStr[1]),
//                        Double.valueOf(locStr[2]),
//                        Double.valueOf(locStr[3]),
//                        Float.valueOf(locStr[4]),
//                        Float.valueOf(locStr[5])
//                );
//                homeData.addHome(homeName, loc);
//            }
//            HomeManager.playerHomeMap.put(player.getName(), homeData);
//        }else{
//            HomeData homeData = new HomeData();
//            HomeManager.playerHomeMap.put(player.getName(), homeData);
//        }
//
//    }

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

        //保存其他
        config.set("uuid", PlayerStatsManager.playerStatMap.get(playerName).getUUID().toString());
        config.set("hearts", player.getHealth()/2);
        config.set("maxHearts", PlayerStatsManager.playerStatMap.get(playerName).getMaxHearts());
        config.set("kill", PlayerStatsManager.playerStatMap.get(playerName).getKill());
        config.set("killStreak", PlayerStatsManager.playerStatMap.get(playerName).getKillStreak());
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

        //保存其他
        config.set("uuid", PlayerStatsManager.playerStatMap.get(playerName).getUUID().toString());
        config.set("maxHearts", PlayerStatsManager.playerStatMap.get(playerName).getMaxHearts());
        config.set("kill", PlayerStatsManager.playerStatMap.get(playerName).getKill());
        config.set("killStreak", PlayerStatsManager.playerStatMap.get(playerName).getKillStreak());
        config.set("death", PlayerStatsManager.playerStatMap.get(playerName).getDeath());
        config.set("tpTime", PlayerStatsManager.playerStatMap.get(playerName).getTpTime());
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
