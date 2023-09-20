package cn.pixelwar.pwlifesteal.Skript.Effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.GetMythicMobsItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class EffectGiveMMItem extends Effect {

    private Expression<Number> numberin;
    private Expression<String> itemnamein;
    private Expression<Player> playerin;

    static {
        Skript.registerEffect(EffectGiveMMItem.class, new String[]{
                "givemmitem %player% %string% %number%",
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        this.itemnamein = (Expression<String>) expressions[1];
        this.numberin = (Expression<Number>) expressions[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "give ruby to player: ";
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        String name = itemnamein.getSingle(event);
        int amount = (numberin.getSingle(event)).intValue();
        ItemStack itemStack = GetMythicMobsItem.getMMItem(name);
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }


}
