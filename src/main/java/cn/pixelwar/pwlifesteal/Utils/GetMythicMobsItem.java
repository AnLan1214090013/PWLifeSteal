package cn.pixelwar.pwlifesteal.Utils;

import io.lumine.mythic.api.MythicPlugin;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * @author pyf
 * @description
 */

public class GetMythicMobsItem {

    public static ItemStack getMMItem(String itemName) {
        return MythicBukkit.inst().getItemManager().getItemStack(itemName);
    }

}
