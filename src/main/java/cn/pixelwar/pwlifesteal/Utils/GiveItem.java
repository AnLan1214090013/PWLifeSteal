package cn.pixelwar.pwlifesteal.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem {

    public static void giveItem(Player player, ItemStack itemStack) {

        if (player.getInventory().firstEmpty() == -1) {

            player.getWorld().dropItem(player.getLocation(), itemStack);

        } else {
            player.getInventory().addItem(itemStack);
        }


    }
}
