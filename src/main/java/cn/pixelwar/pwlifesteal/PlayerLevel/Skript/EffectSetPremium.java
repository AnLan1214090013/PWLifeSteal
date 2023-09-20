package cn.pixelwar.pwlifesteal.PlayerLevel.Skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.PlayerLevel.Menu.LevelMenu;
import cn.pixelwar.pwlifesteal.PlayerLevel.PlayerLevelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectSetPremium extends Effect {

    private Expression<Player> playerin;

    static {
        Skript.registerEffect(EffectSetPremium.class, new String[]{
                "setlevelpremium %player%",
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "open level menu for player: ";
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        PlayerLevelManager.setPremium(player);
    }


}
