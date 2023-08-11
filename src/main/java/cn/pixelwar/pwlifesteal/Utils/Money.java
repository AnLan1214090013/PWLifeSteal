package cn.pixelwar.pwlifesteal.Utils;

import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.BigDecimal;

import static org.bukkit.Bukkit.getServer;

public class Money {

    public static double getPlayerMoney(Player player){
        XConomyAPI xcapi = new XConomyAPI();
        PlayerData playerData = xcapi.getPlayerData(player.getName());
        return playerData.getBalance().doubleValue();
    }
    public static void givePlayerMoney(Player player, double amount){
        XConomyAPI xcapi = new XConomyAPI();
        xcapi.changePlayerBalance(player.getUniqueId(), player.getName(), new BigDecimal(amount), true);
    }
    public static void takePlayerMoney(Player player, double amount){
        XConomyAPI xcapi = new XConomyAPI();
        xcapi.changePlayerBalance(player.getUniqueId(), player.getName(), new BigDecimal(amount), false);
    }


}
