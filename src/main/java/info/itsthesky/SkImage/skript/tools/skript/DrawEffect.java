package info.itsthesky.SkImage.skript.tools.skript;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class DrawEffect extends EasyElement {

    public static void register(Class<? extends EasyElement> clazz, String pattern) {

    }

    protected abstract void draw(int x, int y, Graphics2D graphics);

    private Expression<Number> exprX;
    private Expression<Number> exprY;
    private Expression<Graphics2D> exprGraphics;

    @Override
    protected void execute(@NotNull Event e) {
        final Number x = parseSingle(exprX, e, null);
        final Number y = parseSingle(exprY, e, null);
        final Graphics2D graphics = parseSingle(exprGraphics, e, null);
        if (anyNull(x, y, graphics))
            return;
        draw(x.intValue(), y.intValue(), graphics);
    }

    public boolean parsing(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprX = (Expression<Number>) exprs[0];
        exprY = (Expression<Number>) exprs[1];
        exprGraphics = (Expression<Graphics2D>) exprs[exprs.length - 1];
        return parsing(exprs, matchedPattern, isDelayed, parseResult);
    }

}
