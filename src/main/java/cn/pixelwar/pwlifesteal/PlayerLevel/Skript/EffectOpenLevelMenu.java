package cn.pixelwar.pwlifesteal.PlayerLevel.Skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwlifesteal.PlayerLevel.Menu.LevelMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectOpenLevelMenu extends Effect {

    private Expression<Player> playerin;
    private Expression<Number> pagein;

    static {
        Skript.registerEffect(EffectOpenLevelMenu.class, new String[]{
                "level %player% %number%",
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        this.pagein = (Expression<Number>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "open level menu for player: ";
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        int page = pagein.getSingle(event).intValue();
        LevelMenu levelMenu = new LevelMenu();
        levelMenu.openLevelMenu(player, page);
    }


}
