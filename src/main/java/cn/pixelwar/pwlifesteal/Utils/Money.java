//package cn.pixelwar.pwlifesteal.Utils;
//
//import net.milkbowl.vault.economy.Economy;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.RegisteredServiceProvider;
//
//import static org.bukkit.Bukkit.getServer;
//
//public class Money {
//
//    public static double getPlayerMoney(Player player){
//
//        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
//        if (rsp == null) {
//            return 0;
//        }
//        Economy econ = rsp.getProvider();
//        return econ.getBalance(player);
//
//    }
//    public static void givePlayerMoney(Player player, double amount){
//        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
//        if (rsp == null) {
//            return;
//        }
//        Economy econ = rsp.getProvider();
//        econ.depositPlayer(player, amount);
//    }
//    public static void takePlayerMoney(Player player, double amount){
//        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
//        if (rsp == null) {
//            return;
//        }
//        Economy econ = rsp.getProvider();
//        econ.withdrawPlayer(player, amount);
//    }
//
//
//}
