package cn.pixelwar.pwlifesteal.Skript.Home;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class EffectHomeList extends Effect {

    private Expression<Player> playerin;
    static {
        Skript.registerEffect(EffectHomeList.class, new String[] {
                "homelist %player%",
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
        return "go home to player: " + playerin.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        PlayerStatsManager.listHome(player);

    }
}
