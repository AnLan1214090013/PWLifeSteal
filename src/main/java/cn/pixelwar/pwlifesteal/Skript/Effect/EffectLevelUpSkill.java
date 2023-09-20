package cn.pixelwar.pwlifesteal.Skript.Effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.Menu.SkillTreeMenu;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerSkill.SkillType;
import cn.pixelwar.pwlifesteal.PlayerStats.PlayerStatsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectLevelUpSkill extends Effect {

    private Expression<String> skillin;
    private Expression<Player> playerin;

    static {
        Skript.registerEffect(EffectLevelUpSkill.class, new String[]{
                "skilllevelup %player% %string%",
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        this.skillin = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "levelup skill for player: ";
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        SkillType skill = SkillType.getSkillType(skillin.getSingle(event));
        PlayerStatsManager.levelUpSkill(player, skill);
    }


}
