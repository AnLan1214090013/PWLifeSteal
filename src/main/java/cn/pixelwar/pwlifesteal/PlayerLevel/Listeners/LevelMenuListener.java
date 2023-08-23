package cn.pixelwar.pwlifesteal.PlayerLevel.Listeners;

import cn.pixelwar.pwlifesteal.Menu.SkillTreeMenu;
import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerLevel.Menu.LevelMenu;
import cn.pixelwar.pwlifesteal.PlayerLevel.PlayerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerLevel.Reward.Reward;
import cn.pixelwar.pwlifesteal.PlayerLevel.ServerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import de.netzkronehd.wgregionevents.events.RegionEnterEvent;
import de.netzkronehd.wgregionevents.events.RegionLeaveEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class LevelMenuListener implements Listener {


    @EventHandler
    public void onClickSkillMenu(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getView().getTitle().contains("玩家等级")) {
            Inventory viewInventory = event.getView().getTopInventory();
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getEyeLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 2.0f);
            event.setCancelled(true);
            //点的是菜单
            if (event.getClickedInventory().equals(viewInventory)) {
                ItemStack click = event.getCurrentItem();
                if (click==null){
                    return;
                }
                //如果点的是蓝色玻璃，领取普通奖励
                if (click.getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)){
                    //先解锁下一级
                    int nowLevel = PlayerLevelManager.playerLevelNumHashMap.get(player.getName());
                    PlayerLevelManager.setNewLevelForPlayer(player, nowLevel+1);
                    //发放奖励
                    List<Reward> commonRewards = ServerLevelManager.allLevels.get(nowLevel).getCommonRewards();
                    player.playSound(player.getEyeLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE,1f,1f);
                    player.sendMessage(ChatColorCast.format(" "));
                    player.sendMessage(ChatColorCast.format(" &f你已经达到了&a&l等级 "+nowLevel));
                    player.sendMessage(ChatColorCast.format(" "));
                    player.sendMessage(ChatColorCast.format(" &f获得奖励: "));
                    for (Reward reward : commonRewards){
                        reward.giveReward(player);
                        player.sendMessage(ChatColorCast.format(" &d▸ "+reward.getDesc()));
                        player.sendMessage(ChatColorCast.format(" "));
                    }

                    //刷新一下按钮
                    LevelMenu levelMenu = new LevelMenu();
                    viewInventory.setItem(event.getSlot(), levelMenu.getCommonItem(nowLevel, player));
                    return;
                }
                //领取premium奖励
                if (click.getType().equals(Material.TNT_MINECART)){
                    //先标记为领过
                    List<Integer> gots = PlayerLevelManager.premiumRewardGetMap.get(player.getName());
                    NBTItem nbtItem = new NBTItem(event.getCurrentItem());
                    int nowLevel = nbtItem.getInteger("level");
                    gots.add(nowLevel);
                    PlayerLevelManager.premiumRewardGetMap.put(player.getName(), gots);

                    //发放奖励
                    List<Reward> Rewards = ServerLevelManager.allLevels.get(nowLevel).getPremiumRewards();
                    player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1f,0.1f);
                    player.sendMessage(ChatColorCast.format(" &f获得额外奖励: "));
                    for (Reward reward : Rewards){
                        reward.giveReward(player);
                        player.sendMessage(ChatColorCast.format(" &6▸ "+reward.getDesc()));
                        player.sendMessage(ChatColorCast.format(" "));
                    }

                    //刷新
                    LevelMenu levelMenu = new LevelMenu();
                    viewInventory.setItem(event.getSlot(), levelMenu.getCommonItem(nowLevel, player));
                    return;
                }


            }
        }
    }


    @EventHandler
    public void onRegionEnter(RegionEnterEvent e)
    {
        if (e.getRegion().getId().equals("spawn")){
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 1, false, false, false));
        }
    }

    @EventHandler
    public void onRegionLeave(RegionLeaveEvent e)
    {
        if (e.getRegion().getId().equals("spawn")){
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
    @EventHandler
    public void onRegionLeft(RegionLeftEvent e)
    {
        if (e.getRegion().getId().equals("spawn")){
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }


}
