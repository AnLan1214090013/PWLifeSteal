package cn.pixelwar.pwlifesteal.Skript.Effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectGiveRuby extends Effect {

    private Expression<Number> numberin;
    private Expression<Player> playerin;
    static {
        Skript.registerEffect(EffectGiveRuby.class, new String[] {
                "giveruby %player% %number%",
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        this.numberin = (Expression<Number>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "give ruby to player: ";
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        int number = (numberin.getSingle(event)).intValue();
        PlayerStatsManager.givePlayerRuby(player, number);
    }


}
