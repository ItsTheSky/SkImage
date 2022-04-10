package info.itsthesky.SkImage.old.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("New Graphics")
@Description({"Create a new Graphics2D where shapes and texts can be drawn.",
"Don't forget to **Dispose** your graphics after drawing on it!"})
@Examples("set {_g2d} to new graphics from {_image}")
public class CreateGraphics extends SimpleExpression<Graphics2D> {

    static {
        Skript.registerExpression(
                CreateGraphics.class,
                Graphics2D.class,
                ExpressionType.COMBINED,
                "[new] graphic[s] (of|from) %image%"
        );
    }

    private Expression<BufferedImage> exprImage;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprImage = (Expression<BufferedImage>) exprs[0];
        return true;
    }

    @Nullable
    @Override
    protected Graphics2D[] get(@NotNull Event e) {
        final BufferedImage image = EasyElement.parseSingle(exprImage, e, null);
        if (image == null)
            return new Graphics2D[0];
        return new Graphics2D[] {image.createGraphics()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Graphics2D> getReturnType() {
        return Graphics2D.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "new graphics from " + exprImage.toString(e, debug);
    }
}
