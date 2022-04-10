package info.itsthesky.SkImage.old.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.skript.DrawEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawImage extends DrawEffect {

    static {
        register(
                DrawImage.class,
                "image[s] %image%"
        );
    }

    private Expression<BufferedImage> exprImage;

    @Override
    protected void draw(int x, int y, Graphics2D graphics) {

    }

    @Override
    public boolean parsing(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprImage = (Expression<BufferedImage>) exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "null";
    }
}
