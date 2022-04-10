package info.itsthesky.SkImage.old.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class DisposeGraphics extends EasyElement {

    static {
        Skript.registerEffect(
                DisposeGraphics.class,
                "(save|dispose) [graphic[s]] %graphic%"
        );
    }

    private Expression<Graphics2D> exprGraphics;

    @Override
    protected void execute(@NotNull Event e) {
        final Graphics2D graphics = parseSingle(exprGraphics, e, null);
        if (anyNull(graphics))
            return;
        graphics.dispose();
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "dispose graphics " + exprGraphics.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprGraphics = (Expression<Graphics2D>) exprs[0];
        return true;
    }
}
