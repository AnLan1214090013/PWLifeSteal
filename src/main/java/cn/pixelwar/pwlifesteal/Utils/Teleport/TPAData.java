package cn.pixelwar.pwlifesteal.Utils.Teleport;

import org.bukkit.entity.Player;

public class TPAData {


    private Player receiver;
    private int time;

    public TPAData(Player receiver, int time) {
        this.receiver = receiver;
        this.time = time;
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
