package cn.pixelwar.pwlifesteal.Listeners;

import cn.pixelwar.pwlifesteal.Menu.SkillTreeMenu;
import cn.pixelwar.pwlifesteal.PWLifeSteal;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import cn.pixelwar.pwlifesteal.Utils.GetWGRegion;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.netzkronehd.wgregionevents.events.RegionEnterEvent;
import de.netzkronehd.wgregionevents.events.RegionLeaveEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MenuListener implements Listener {


    @EventHandler
    public void onClickSkillMenu(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getView().getTitle().contains("技能树")) {
            Inventory viewInventory = event.getView().getTopInventory();
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getEyeLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 2.0f);
            //点的是背包里
            if (event.getClickedInventory().equals(player.getInventory())) {
                event.setCancelled(true);
            }
            //点的是菜单
            if (event.getClickedInventory().equals(viewInventory)) {
                event.setCancelled(true);
                ItemStack click = event.getCurrentItem();
                if (click==null){
                    return;
                }
                //如果已经满级
                if (click.getType().equals(Material.ORANGE_DYE)){
                    return;
                }
                if (click.getType().equals(Material.GRAY_DYE) || click.getType().equals(Material.LIME_DYE)){
                    NBTItem nbtItem = new NBTItem(click);
                    if (nbtItem.hasTag("skill")){
                        SkillType skillType = SkillType.getSkillType(nbtItem.getString("skill"));
                        int cost = nbtItem.getInteger("cost");
                        int playerRuby = PlayerStatsManager.playerStatMap.get(player.getName()).getRuby();
                        if (cost>playerRuby){
                            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.1f);
                            player.sendMessage(ChatColorCast.format("&d▸ &f你没有足够的&d&l紫宝石&f来升级技能!"));
                            return;
                        }
                        PlayerStatsManager.levelUpSkill(player, skillType);
                        PlayerStatsManager.removePlayerRuby(player, cost);
                        player.playSound(player.getEyeLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1f);
                        player.sendMessage(ChatColorCast.format("&a▸ &f升级技能成功! &b&l"+skillType.getDisplayName()+"&7(等级"+PlayerStatsManager.playerStatMap.get(player.getName()).getSkillStat().get(skillType)+")"));
                        player.closeInventory();
                        Bukkit.getScheduler().runTaskLater(PWLifeSteal.getPlugin(),() -> {
                            SkillTreeMenu skillTreeMenu = new SkillTreeMenu();
                            skillTreeMenu.openSkillTreeMenu(player);
                        }, 1l);


                    }

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
