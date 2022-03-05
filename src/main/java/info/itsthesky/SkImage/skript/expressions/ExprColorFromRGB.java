package info.itsthesky.SkImage.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@Name("Color from RGB")
@Description("Return a color from RGB (Red, Green, Blue)")
@Examples("set {_color} to color from rgb 255, 20, 32")
@Since("1.0")
public class ExprColorFromRGB extends SimpleExpression<ch.njol.skript.util.Color> {

	static {
		Skript.registerExpression(ExprColorFromRGB.class, ch.njol.skript.util.Color.class, ExpressionType.SIMPLE,
				"[skimage] color from (rgb|redgreenblue) %number%[ ][,][ ]%number%[ ][,][ ]%number%");
	}

	private Expression<Number> exprRed, exprGreen, exprBlue;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
		exprRed = (Expression<Number>) exprs[0];
		exprGreen = (Expression<Number>) exprs[1];
		exprBlue = (Expression<Number>) exprs[2];
		return true;
	}

	@Override
	protected ch.njol.skript.util.Color[] get(@NotNull Event e) {
		Number r = exprRed.getSingle(e);
		Number g = exprGreen.getSingle(e);
		Number b = exprBlue.getSingle(e);
		if (b == null || g == null || r == null) return new ch.njol.skript.util.Color[0];
		return new ch.njol.skript.util.Color[] {new ColorRGB(r.intValue(), g.intValue(), b.intValue())};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public @NotNull Class<? extends ch.njol.skript.util.Color> getReturnType() {
		return ch.njol.skript.util.Color.class;
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "color from rgb " + exprRed.toString(e, debug) + ", " + exprGreen.toString(e, debug) + ", " + exprBlue.toString(e, debug);
	}

}