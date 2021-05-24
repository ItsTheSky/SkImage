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
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Color distance")
@Description("Return the distance between two java.awt colors")
@Examples("set {_dif} to color distance between {_c1} and {_c2}")
@Since("1.5.2")
public class ExprColorDistance extends SimpleExpression<Double> {

	static {
		Skript.registerExpression(ExprColorDistance.class, Double.class, ExpressionType.SIMPLE,
				"[skimage] [color] distance between [the] %imagecolor% and %imagecolor%");
	}

	private Expression<Color> exprC1, exprC2;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprC1 = (Expression<Color>) exprs[0];
		exprC2 = (Expression<Color>) exprs[1];
		return true;
	}

	@Override
	protected Double[] get(Event e) {
		Color c1 = exprC1.getSingle(e);
		Color c2 = exprC2.getSingle(e);
		if (c1 == null || c2 == null) return new Double[0];
		return new Double[] {Utils.colorDistance(c1, c2)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public @NotNull Class<? extends Double> getReturnType() {
		return Double.class;
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "color distance between " + exprC1.toString(e, debug) + " and " + exprC2.toString(e, debug);
	}

}