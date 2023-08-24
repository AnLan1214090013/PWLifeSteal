package cn.pixelwar.pwlifesteal.PlayerLevel.Listeners;

import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.LevelDoneEvent;
import cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent.QuestProgressEvent;
import cn.pixelwar.pwlifesteal.PlayerLevel.PlayerLevelManager;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.List;

/**
 * @author pyf
 * @description
 */

public class QuestListeners implements Listener {

    @EventHandler
    public void onQuestProgress(QuestProgressEvent event){
        Player player = event.getPlayer();
        Quest quest = event.getQuest();
        String msg = ChatColorCast.format("&f玩家等级 &d▸ &f"+quest.getQuestName()+" &f(&a"+ quest.getNowProgress()+"/"+quest.getNeedProgress()+"&f)");
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(msg));
        player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 2F);
    }
    @EventHandler
    public void onLevelDone(LevelDoneEvent event){
        Player player = event.getPlayer();
        String title = ChatColorCast.format("&a&l已完成所有任务");
        String subtitle = ChatColorCast.format("&f等级 &d▸ &b&l" + event.getOldLevel()+" &7(/level查看并领取奖励)");
        player.sendTitle(title, subtitle, 40, 120, 40);
        player.playSound(player.getEyeLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        HashMap<Integer, Quest> quests = PlayerLevelManager.getPlayerNowQuests(player);
        HashMap<Integer, Quest> newQuests = quests;
        for (Quest quest : quests.values()){

            QuestType questType = quest.getQuestType();
            if (questType.equals(QuestType.BREAK)){
                String variable = quest.getQuestVariable();
                int nowProgress = quest.getNowProgress();
                int needProgress = quest.getNeedProgress();
                //破坏任何方块
                if (variable.equals("ANY_BLOCK")){
                    PlayerLevelManager.setQuestProgressForPlayer(player, questType, "ANY_BLOCK", nowProgress+1);
                }

            }
        }

    }


}
