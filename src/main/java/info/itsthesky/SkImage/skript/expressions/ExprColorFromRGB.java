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
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Color from RGB")
@Description("Return a color from RGB (Red, Green, Blue)")
@Examples("set {_color} to color from rgb 255, 20, 32")
@Since("1.0")
public class ExprColorFromRGB extends SimpleExpression<Color> {

	static {
		Skript.registerExpression(ExprColorFromRGB.class, Color.class, ExpressionType.SIMPLE,
				"[skimage] color from (rgb|redgreenblue) %number%[ ][,][ ]%number%[ ][,][ ]%number%");
	}

	private Expression<Number> exprRed, exprGreen, exprBlue;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprRed = (Expression<Number>) exprs[0];
		exprGreen = (Expression<Number>) exprs[1];
		exprBlue = (Expression<Number>) exprs[2];
		return true;
	}

	@Override
	protected Color[] get(Event e) {
		Number r = exprRed.getSingle(e);
		Number g = exprGreen.getSingle(e);
		Number b = exprBlue.getSingle(e);
		if (b == null || g == null || r == null) return new Color[0];
		return new Color[] {new Color(r.intValue(), g.intValue(), b.intValue())};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "color from rgb " + exprRed.toString(e, debug) + ", " + exprGreen.toString(e, debug) + ", " + exprBlue.toString(e, debug);
	}

}