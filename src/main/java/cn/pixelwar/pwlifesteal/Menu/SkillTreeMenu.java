package cn.pixelwar.pwlifesteal.Menu;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkillTreeMenu {


    public void openSkillTreeMenu(Player player){
        Inventory gui = Bukkit.createInventory(player, 54, ChatColorCast.format("&8技能树"));
    }

    public void getSkillItem(SkillType skillType){

    }


}
