package cn.pixelwar.pwlifesteal.PlayerLevel.Reward;

import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.GetMythicMobsItem;
import cn.pixelwar.pwlifesteal.Utils.GiveItem;
import cn.pixelwar.pwlifesteal.Utils.Money;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Reward {

    String desc;
    String type;
    int amount;
    String variable;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Reward(String desc, String type, int amount, String variable) {
        this.desc = desc;
        this.type = type;
        this.amount = amount;
        this.variable = variable;
    }

    public Reward(String desc, String type, int amount) {
        this.desc = desc;
        this.type = type;
        this.amount = amount;
    }

    public void giveReward(Player player){
        switch (this.type){
            case "EXP":
                player.giveExp(this.amount);
            case "MONEY":
                Money.givePlayerMoney(player, this.amount);
            case  "RUBY":
                PlayerStatsManager.givePlayerRuby(player, this.amount);
            case "ITEM":
                ItemStack itemStack = new ItemStack(Material.getMaterial(this.variable));
                itemStack.setAmount(this.amount-1);
                GiveItem.giveItem(player, itemStack);
            case "CUSTOM_ITEM":
                ItemStack itemStack2 = GetMythicMobsItem.getMMItem(this.variable);
                GiveItem.giveItem(player, itemStack2);
            default:
                return;
        }



    }



}
