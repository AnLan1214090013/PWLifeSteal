package cn.pixelwar.pwlifesteal.Skript.Effect.Teleport;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.Utils.Teleport.Teleport;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectTPA extends Effect {

    private Expression<Player> senderin;
    private Expression<Player> receiverin;
    static {
        Skript.registerEffect(EffectTPA.class, new String[] {
                "tpa %player% %player%",
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.senderin = (Expression<Player>) expressions[0];
        this.receiverin = (Expression<Player>) expressions[1];

        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "tpa player: " + senderin.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Player sender = senderin.getSingle(event);
        Player receiver = receiverin.getSingle(event);
        Teleport teleport = new Teleport();
        teleport.sendTPRequest(sender, receiver);


    }
}

