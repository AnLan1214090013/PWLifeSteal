package cn.pixelwar.pwlifesteal.Skript.Home;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import cn.pixelwar.pwlifesteal.Utils.ChatColorCast;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectAddHome  extends Effect {

    private Expression<Player> playerin;
    private Expression<String> namein;
    static {
        Skript.registerEffect(EffectAddHome.class, new String[] {
                "addhome %player% %string%",
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        this.namein = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "add home to player: " + playerin.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        String name = namein.getSingle(event);
        PlayerStatsManager.addHome(player, name, player.getLocation());
    }
}
