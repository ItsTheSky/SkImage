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

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Average Image Color")
@Description("Get the average (main) color of an image.")
@Examples("set {_color} to average color of {_image}")
@Since("1.5.2")
public class ExprAverageColor extends SimpleExpression<Color> {

	static {
		Skript.registerExpression(ExprAverageColor.class, Color.class, ExpressionType.SIMPLE,
				"[skimage] (average|main) color of [the] [image] %image%");
	}

	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		return true;
	}

	@Override
	protected Color[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		if (image == null ) return new Color[0];
		return new Color[] {Utils.mainColor(image)};
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
		return "main color of image " + exprImage.toString(e, debug);
	}

}