package cn.pixelwar.pwlifesteal.PlayerLevel.Reward;

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
}
