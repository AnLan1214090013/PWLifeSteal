package cn.pixelwar.pwlifesteal.PlayerLevel.Quest.QuestVariable;

import javax.swing.text.html.parser.Entity;

public class KillMobVariable {

    private Entity mobType;

    public Entity getMobType() {
        return mobType;
    }

    public void setMobType(Entity mobType) {
        this.mobType = mobType;
    }

    public KillMobVariable(Entity mobType) {
        this.mobType = mobType;
    }
}
