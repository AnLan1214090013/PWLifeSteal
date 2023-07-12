package cn.pixelwar.pwlifesteal.Utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;

public class Ban {

    public static void banPlayer(Player player, int hours){
        Date nowTime = new Date();
        long l = nowTime.getTime();
        System.out.println(l);
        Date unbannedTime = new Date(l+(hours*60*60*1000));
        Bukkit.getBanList(BanList.Type.NAME).addBan(
                player.getName(),
                "你的生命值已经耗尽",
                unbannedTime,
                "PIXEL WAR");
    }

}
