package cn.pixelwar.pwlifesteal.PlayerLevel.Menu;

import cn.pixelwar.pwlifesteal.PlayerLevel.Level;
import cn.pixelwar.pwlifesteal.PlayerLevel.PlayerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Reward.Reward;
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
import java.util.Queue;

public class LevelMenu {


    public void openLevelMenu(Player player, int page){
        Inventory gui = Bukkit.createInventory(player, 54, ChatColorCast.format("&8玩家等级 ("+page+"/"+ (int)(ServerLevelManager.allLevels.size()/18+1)+")"));

        int[] commonSlot = {9,10,11,12,13,14,15,16,17,   27,18,29,30,31,32,33,34,35};
        int[] premiumSlot = {18,19,20,21,22,23,24,25,26,   36,37,38,39,40,41,42,43,44};
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
        itemMeta2.setDisplayName(ChatColorCast.format("&6&l等级 0 额外奖励"));
        List<String> lore2 = new ArrayList<>();
        lore.add("");
        itemMeta2.setLore(lore2);
        zeroP.setItemMeta(itemMeta2);
        gui.setItem(18, zeroP);

        //箭头
        if (page==1 && ServerLevelManager.allLevels.size()>17){
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta m = next.getItemMeta();
            m.setDisplayName(ChatColorCast.format("&a▸ &l下一页"));
            next.setItemMeta(m);
            NBTItem nbtItem = new NBTItem(next);
            nbtItem.setInteger("page", page);
            next = nbtItem.getItem();


            gui.setItem(52, next);
        }
        if (page==(int)(ServerLevelManager.allLevels.size()/18+1) && page!=1){
            ItemStack last = new ItemStack(Material.ARROW);
            ItemMeta m = last.getItemMeta();
            m.setDisplayName(ChatColorCast.format("&a◂ &l上一页"));
            last.setItemMeta(m);
            NBTItem nbtItem2 = new NBTItem(last);
            nbtItem2.setInteger("page", page);
            last = nbtItem2.getItem();
            gui.setItem(46, last);
        }
        if ( page!=1 && page!=(int)(ServerLevelManager.allLevels.size()/18+1))
        {
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta m = next.getItemMeta();
            m.setDisplayName(ChatColorCast.format("&a▸ &l下一页"));
            next.setItemMeta(m);
            NBTItem nbtItem = new NBTItem(next);
            nbtItem.setInteger("page", page);
            next = nbtItem.getItem();
            gui.setItem(52, next);
            ItemStack last = new ItemStack(Material.ARROW);
            ItemMeta m2 = last.getItemMeta();
            m2.setDisplayName(ChatColorCast.format("&a◂ &l上一页"));
            last.setItemMeta(m2);
            NBTItem nbtItem2 = new NBTItem(last);
            nbtItem2.setInteger("page", page);
            last = nbtItem2.getItem();
            gui.setItem(46, last);
        }
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta m = book.getItemMeta();
        m.setDisplayName(ChatColorCast.format("&6&l嗜血生存玩家等级系统"));
        List<String> bookLore = new ArrayList<>();
        bookLore.add("");
        bookLore.add(ChatColorCast.format("&7你可以通过完成下面这些等级需要"));
        bookLore.add(ChatColorCast.format("&7的&d任务&7来获取奖励并&a解锁&7新的"));
        bookLore.add(ChatColorCast.format("&7等级和权限"));
        bookLore.add(ChatColorCast.format("&7 "));
        bookLore.add(ChatColorCast.format("&7&o你可以通过购买&6&l等级+&7&o来获得"));
        bookLore.add(ChatColorCast.format(" "));
        bookLore.add(ChatColorCast.format("&7更多奖励(具体内容请看PW商店)"));
        m.setLore(bookLore);
        book.setItemMeta(m);
        gui.setItem(4, book);

        player.openInventory(gui);
    }

    public ItemStack getCommonItem(int level, Player player){
        int nowLevelNum = PlayerLevelManager.playerLevelNumHashMap.get(player.getName());
        Level serverLevel = ServerLevelManager.allLevels.get(level);
        ItemStack item = null;
        String itemName = "";
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColorCast.format("&7通过完成该等级的任务来增加你的"));
        lore.add(ChatColorCast.format("&7等级，解锁奖励和一些新的权限"));
        lore.add(ChatColorCast.format("&7"));
        lore.add(ChatColorCast.format("&7等级任务:"));
        //玩家还没达到的等级
        if (level > nowLevelNum){
            item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            itemName = ChatColorCast.format("&c&l等级 "+level);
            for (Quest q : serverLevel.getQuests().values()){
                lore.add(ChatColorCast.format("&8 ▸ &c&l???"));
            }
            lore.add(ChatColorCast.format("&8 ▸ &7达到该等级解锁任务"));
            lore.add(ChatColorCast.format("&7"));
            lore.add(ChatColorCast.format("&7等级奖励:"));
            for (Reward reward : serverLevel.getCommonRewards()){
                lore.add(ChatColorCast.format("&7 ▸ "+reward.getDesc()));
            }
            lore.add(ChatColorCast.format("&7"));
            lore.add(ChatColorCast.format("&7请先达到一个等级"));
        }
        //玩家已经解锁过的等级
        if (level < nowLevelNum){
            item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            itemName = ChatColorCast.format("&a&l等级 "+level);
            for (Quest q : serverLevel.getQuests().values()){
                lore.add(ChatColorCast.format("&a&l ✔ &7&m"+q.getQuestName()+"&f("+q.getNeedProgress()+"/"+q.getNeedProgress()+")"));
            }
            lore.add(ChatColorCast.format("&7"));
            lore.add(ChatColorCast.format("&7等级奖励:"));
            for (Reward reward : serverLevel.getCommonRewards()){
                lore.add(ChatColorCast.format("&7 ▸ "+reward.getDesc()));
            }
            lore.add(ChatColorCast.format("&7"));
            lore.add(ChatColorCast.format("&a你已经解锁了该等级"));
        }
        //玩家当前的等级
        if (level == nowLevelNum){
            Level nowLevel = PlayerLevelManager.playerLevelHashMap.get(player.getName());
            HashMap<Integer, Quest> quests = nowLevel.getQuests();
            boolean isDone = PlayerLevelManager.checkLevelIsDone(player, nowLevelNum);
            //还没有完成等级
            if (!isDone){
                item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                itemName = ChatColorCast.format("&e&l等级 "+level);
                for (Quest q : quests.values()){
                    //该任务已经完成
                    if (q.getNeedProgress()<=q.getNowProgress()){
                        lore.add(ChatColorCast.format("&a&l ✔ &7&m"+q.getQuestName()+"&f("+q.getNeedProgress()+"/"+q.getNeedProgress()+")"));
                    }else{
                        //该任务未完成
                        lore.add(ChatColorCast.format("&c&l ✖ &7"+q.getQuestName()+"&f("+q.getNowProgress()+"/"+q.getNeedProgress()+")"));
                    }
                }
                lore.add(ChatColorCast.format("&7"));
                lore.add(ChatColorCast.format("&7等级奖励:"));
                for (Reward reward : serverLevel.getCommonRewards()){
                    lore.add(ChatColorCast.format("&7 ▸ "+reward.getDesc()));
                }
                lore.add(ChatColorCast.format("&7"));
                lore.add(ChatColorCast.format("&c完成所有任务解锁等级/领取奖励"));
            }else{
                //已经完成等级
                item = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                itemName = ChatColorCast.format("&b&l等级 "+level);
                for (Quest q : serverLevel.getQuests().values()){
                    lore.add(ChatColorCast.format("&a&l ✔ &7&m"+q.getQuestName()+"&f("+q.getNeedProgress()+"/"+q.getNeedProgress()+")"));
                }
                lore.add(ChatColorCast.format("&7"));
                lore.add(ChatColorCast.format("&7等级奖励:"));
                for (Reward reward : serverLevel.getCommonRewards()){
                    lore.add(ChatColorCast.format("&7 ▸ "+reward.getDesc()));
                }
                lore.add(ChatColorCast.format("&7"));
                lore.add(ChatColorCast.format("&b你已经完成了所有的等级任务"));
                lore.add(ChatColorCast.format("&b点击领取你的奖励"));
            }
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }
    public ItemStack getPremiumItem(int level, Player player){
        int nowLevelNum = PlayerLevelManager.playerLevelNumHashMap.get(player.getName());
        Level serverLevel = ServerLevelManager.allLevels.get(level);
        ItemStack item = null;
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColorCast.format("&7你可以通过解锁&6&l等级+&7来获得"));
        lore.add(ChatColorCast.format("&7每一个等级的更多奖励"));
        lore.add(ChatColorCast.format("&7"));
        lore.add(ChatColorCast.format("&7额外奖励:"));
        for (Reward reward : serverLevel.getPremiumRewards()){
            lore.add(ChatColorCast.format("&7 ▸ "+reward.getDesc()));
        }
        //有 等级+
        if(PlayerLevelManager.isPremiumMap.get(player.getName())){
            //已经领过
            if (PlayerLevelManager.checkGotPremiumReward(player, level)){
                item = new ItemStack(Material.MINECART);
                lore.add(ChatColorCast.format("&7"));
                lore.add(ChatColorCast.format("&a你已经领过该奖励了"));
            }else{
                //没有领过
                //可以领
                if (PlayerLevelManager.playerLevelNumHashMap.get(player.getName())>level){
                    item = new ItemStack(Material.TNT_MINECART);
                    lore.add(ChatColorCast.format("&7"));
                    lore.add(ChatColorCast.format("&a你当前可以点击领取该奖励"));
                }else{
                    //不能领
                    item = new ItemStack(Material.CHEST_MINECART);
                    lore.add(ChatColorCast.format("&7"));
                    lore.add(ChatColorCast.format("&c你需要达到该等级来解锁奖励"));
                }
            }

        }else{
            //没解锁
            item = new ItemStack(Material.CHEST_MINECART);
            lore.add(ChatColorCast.format("&7"));
            lore.add(ChatColorCast.format("&c你需要购买&6&l等级+&c来解锁奖励"));
        }


        ItemMeta itemMeta = item.getItemMeta();
        String displayName = ChatColorCast.format("&6&l等级 " + level+" 额外奖励");
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger("level", level);
        item = nbtItem.getItem();
        return item;
    }


}
