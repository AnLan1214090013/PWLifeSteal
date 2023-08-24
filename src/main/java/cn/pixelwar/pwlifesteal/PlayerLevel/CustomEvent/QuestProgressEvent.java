package cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent;

import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.Quest;
import cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QuestProgressEvent extends Event implements Cancellable {
    private Player player;
    private Quest quest;
    private boolean isCancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public QuestProgressEvent(Player player, Quest quest, boolean isCancelled) {
        this.player = player;
        this.quest = quest;
        this.isCancelled = isCancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }



    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
