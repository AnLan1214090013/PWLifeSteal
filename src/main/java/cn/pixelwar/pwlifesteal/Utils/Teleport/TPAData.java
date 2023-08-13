package cn.pixelwar.pwlifesteal.Utils.Teleport;

import org.bukkit.entity.Player;

public class TPAData {


    private Player receiver;
    private Player sender;
    private int time;

    public TPAData(Player receiver, Player sender, int time) {
        this.receiver = receiver;
        this.sender = sender;
        this.time = time;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public int getTime() {
        return time;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
