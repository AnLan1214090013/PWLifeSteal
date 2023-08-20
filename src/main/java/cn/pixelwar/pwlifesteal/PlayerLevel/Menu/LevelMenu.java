package cn.pixelwar.pwlifesteal.PlayerLevel.Menu;

import cn.pixelwar.pwlifesteal.PlayerLevel.ServerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import cn.pixelwar.pwlifesteal.Utils.NumberFormat;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelMenu {


    public void openLevelMenu(Player player, int page){
        Inventory gui = Bukkit.createInventory(player, 54, ChatColorCast.format("&8玩家等级 ("+page+"/"+ (int)(ServerLevelManager.allLevels.size()/18+1)+")"));

        int commonSlot[] = {9,10,11,12,13,14,15,16,17,   27,18,29,30,31,32,33,34,35};
        int premiumSlot[] = {18,19,20,21,22,23,24,25,26,   36,37,38,39,40,41,42,43,44};
        //第一页: 0 - 17
        //第2页: 18 - 35
        int count = 0;
        for(int level = (page-1)*18; level<(page-1)*18+17;level++){

            if (ServerLevelManager.allLevels.containsKey(level)){

                    gui.setItem(commonSlot[count], getCommonItem(level, player));
                    gui.setItem(commonSlot[count]+9, getPremiumItem(level, player));

            }
            count+=1;
        }

        //第0级
        ItemStack zero = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta itemMeta1 = zero.getItemMeta();
        itemMeta1.setDisplayName(ChatColorCast.format("&a&l等级 0"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColorCast.format("&7此等级默认解锁"));
        itemMeta1.setLore(lore);
        zero.setItemMeta(itemMeta1);
        gui.setItem(9, zero);
        ItemStack zeroP = new ItemStack(Material.MINECART);
        ItemMeta itemMeta2 = zeroP.getItemMeta();
        itemMeta2.setDisplayName(ChatColorCast.format("&6&l等级 0额外奖励"));
        List<String> lore2 = new ArrayList<>();
        lore.add("");
        itemMeta2.setLore(lore2);
        zeroP.setItemMeta(itemMeta2);
        gui.setItem(18, zeroP);
        player.openInventory(gui);
    }

    public ItemStack getCommonItem(int level, Player player){
        NumberFormat numberFormat = new NumberFormat();
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        String displayName = ChatColorCast.format("&c&l等级" + level);
        List<String> lore = new ArrayList<>();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }
    public ItemStack getPremiumItem(int level, Player player){
        NumberFormat numberFormat = new NumberFormat();
        ItemStack item = new ItemStack(Material.CHEST_MINECART);
        ItemMeta itemMeta = item.getItemMeta();
        String displayName = ChatColorCast.format("&6&l等级" + level+"额外奖励");
        List<String> lore = new ArrayList<>();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }


}
