package cn.pixelwar.pwlifesteal.Menu;

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

public class SkillTreeMenu {


    public void openSkillTreeMenu(Player player){
        Inventory gui = Bukkit.createInventory(player, 54, ChatColorCast.format("&8技能树     当前红宝石: &c&l"+PlayerStatsManager.playerStatMap.get(player.getName()).getRuby()));
        for (SkillType skillType : SkillType.values()){
            ItemStack item = getSkillItem(skillType, player);
            gui.setItem(skillType.getMenuSlot(), item);
        }
        player.openInventory(gui);
    }

    public ItemStack getSkillItem(SkillType skillType, Player player){
        NumberFormat numberFormat = new NumberFormat();
        HashMap<SkillType, Integer> skillStat = PlayerStatsManager.playerStatMap.get(player.getName()).getSkillStat();
        ItemStack item = new ItemStack(Material.GRAY_DYE);
        ItemMeta itemMeta = item.getItemMeta();
        //如果已经解锁过
        if (skillStat.containsKey(skillType)){
            //如果没达到最高等级
            if (skillStat.get(skillType)<skillType.getMaxLevel()) {
                String chance = numberFormat.getDoubleFormat(skillType.getDefaultChance() + (double) (skillStat.get(skillType) * skillType.getEachLevelChance())) ;
                item = new ItemStack(Material.LIME_DYE);
                String displayName = ChatColorCast.format("&a&l" + skillType.getDisplayName() + " &f(等级" + skillStat.get(skillType) + "/" + skillType.getMaxLevel() + ")");
                List<String> lore = new ArrayList<>();
                for (String s : skillType.getDescription()) {
                    lore.add(s);
                }
                lore.add(" ");
                lore.add(ChatColorCast.format("&4&l| &f当前触发几率: &b" + chance + "%"));
                lore.add(ChatColorCast.format("&4&l| &f下一级花费: &c" + (skillType.getUnlockPrize() + skillType.getEachLevelAddPrize() * skillStat.get(skillType)) + " 红宝石"));
                lore.add(" ");
                lore.add(ChatColorCast.format("&a▸ 点击升级"));
                itemMeta.setDisplayName(displayName);
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setString("skill", skillType.toString());
                nbtItem.setInteger("cost", (skillType.getUnlockPrize() + skillType.getEachLevelAddPrize() * skillStat.get(skillType)));
                item = nbtItem.getItem();
            }else{
                String chance = numberFormat.getDoubleFormat(skillType.getDefaultChance() + (double) (skillStat.get(skillType) * skillType.getEachLevelChance())) ;
                item = new ItemStack(Material.ORANGE_DYE);
                String displayName = ChatColorCast.format("&6&l" + skillType.getDisplayName() + " &f(等级" + skillStat.get(skillType) + "/" + skillType.getMaxLevel() + ")");
                List<String> lore = new ArrayList<>();
                for (String s : skillType.getDescription()) {
                    lore.add(s);
                }
                lore.add(" ");
                lore.add(ChatColorCast.format("&4&l| &f当前触发几率: &b" + chance + "%"));
//                lore.add(ChatColorCast.format("&4&l| &f下一级花费: &c" + (skillType.getUnlockPrize() + skillType.getEachLevelAddPrize() * skillStat.get(skillType)) + " 红宝石"));
                lore.add(" ");
                lore.add(ChatColorCast.format("&6已达到最高等级"));
                itemMeta.setDisplayName(displayName);
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
            }
        }
        //如果还没有解锁
        else{
            //检查是否已经解锁连接的
            boolean isUnlock = false;
            for (String skillName : skillType.getConnectedSkills()){
                SkillType connected = SkillType.getSkillType(skillName);
                if (skillStat.containsKey(connected)){
                    isUnlock = true;
                }
            }
            //如果可以解锁
            if (skillType.equals(SkillType.LESS_DAMAGE_FROM_MOB) || isUnlock){
                String chance = numberFormat.getDoubleFormat(skillType.getDefaultChance()) ;
                String displayName = ChatColorCast.format("&7&l"+skillType.getDisplayName()+" &f(等级0/"+skillType.getMaxLevel()+")");
                List<String> lore = new ArrayList<>();
                for (String s : skillType.getDescription()){
                    lore.add(s);
                }
                lore.add(" ");
                lore.add(ChatColorCast.format("&4&l| &f当前触发几率: &b"+chance+"%"));
                lore.add(ChatColorCast.format("&4&l| &f解锁花费: &c"+skillType.getUnlockPrize()+" 红宝石"));
                lore.add(" ");
                lore.add(ChatColorCast.format("&a▸ 点击解锁技能"));
                itemMeta.setDisplayName(displayName);
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setString("skill", skillType.toString());
                nbtItem.setInteger("cost", skillType.getUnlockPrize());
                item = nbtItem.getItem();
            }
            //如果不能解锁
            else{
                String chance = numberFormat.getDoubleFormat(skillType.getDefaultChance()) ;
                String displayName = ChatColorCast.format("&7&l"+skillType.getDisplayName()+" &f(等级0/"+skillType.getMaxLevel()+")");
                List<String> lore = new ArrayList<>();
                for (String s : skillType.getDescription()){
                    lore.add(s);
                }
                lore.add(" ");
                lore.add(ChatColorCast.format("&4&l| &f当前触发几率: &b"+chance+"%"));
                lore.add(ChatColorCast.format("&4&l| &f解锁花费: &c"+skillType.getUnlockPrize()+" 红宝石"));
                lore.add(" ");
                lore.add(ChatColorCast.format("&c&l✖ &c当前技能不能解锁,你需要"));
                lore.add(ChatColorCast.format("&c先解锁与这个技能相连的技能"));
                itemMeta.setDisplayName(displayName);
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
            }
        }

        return item;


    }


}
