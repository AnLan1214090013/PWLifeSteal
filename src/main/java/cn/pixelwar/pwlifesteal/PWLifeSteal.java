package cn.pixelwar.pwlifesteal;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import cn.pixelwar.pwlifesteal.File.YamlStorage;
import cn.pixelwar.pwlifesteal.Listeners.LifeStealListener;
import cn.pixelwar.pwlifesteal.Listeners.MenuListener;
import cn.pixelwar.pwlifesteal.Listeners.SpawnListener;
//import net.milkbowl.vault.economy.Economy;
import cn.pixelwar.pwlifesteal.Utils.Teleport.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class PWLifeSteal extends JavaPlugin {
    static PWLifeSteal instance;
//    private static Economy econ = null;
    SkriptAddon addon;

    private static Plugin plugin;
    public static int totalPlayerAmount;
    public static FileConfiguration config;
    @Override
    public void onEnable() {
        plugin = this;
        registerEvents();
        setupConfig();
        setupSK();
        setupTimer();
//        if (!setupEconomy() ) {
//            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }
    }

    @Override
    public void onDisable() {
       updateConfig();
       YamlStorage yamlStorage = new YamlStorage();
        yamlStorage.saveAllPlayerData();
    }
    public static PWLifeSteal getInstance() {
        return instance;
    }
    public static Plugin getPlugin() {
        return plugin;
    }
    public void registerEvents(){
        getServer().getPluginManager().registerEvents((Listener) new SpawnListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener) new LifeStealListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener) new MenuListener(), (Plugin)this);
    }

    public void setupConfig(){
        saveDefaultConfig();
        reloadConfig();
        config = getConfig();
        totalPlayerAmount=PWLifeSteal.config.getInt("players");
        YamlStorage yamlStorage = new YamlStorage();
        yamlStorage.loadWarps();
    }

    private void updateConfig(){
        final FileConfiguration config2 = new YamlConfiguration();
        File configFile = new File("plugins/PWLifeSteal/config.yml");
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(configFile), "UTF-8")) {
            config2.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {}
        config2.set("players", totalPlayerAmount);
        try{
            config2.save(configFile);}catch (IOException ex){
            System.out.println("config信息保存出错");
        }
    }
    public void setupSK(){
        this.addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("cn.pixelwar.pwlifesteal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[PWLifeSteal-skript] 已经成功启动!");
    }

    private void setupTimer(){
        Teleport teleport = new Teleport();
        teleport.tpaTimer();
    }

}
