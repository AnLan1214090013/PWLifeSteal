package cn.pixelwar.pwlifesteal.PlayerLevel.CustomEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
    public class LevelDoneEvent extends Event implements Cancellable {
        private int oldLevel;
        private int newLevel;
        private Player player;

        private boolean isCancelled;

        public LevelDoneEvent(boolean isAsync, int oldLevel, int newLevel, Player player, boolean isCancelled) {
            super(isAsync);
            this.oldLevel = oldLevel;
            this.newLevel = newLevel;
            this.player = player;
            this.isCancelled = isCancelled;
        }

        public int getOldLevel() {
            return oldLevel;
        }

        public int getNewLevel() {
            return newLevel;
        }

        public Player getPlayer() {
            return player;
        }

        private static final HandlerList HANDLERS_LIST = new HandlerList();

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
