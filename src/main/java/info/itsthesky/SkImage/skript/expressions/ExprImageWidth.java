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

@Name("Image Width")
@Description("Return the width size of an image")
@Examples("set {_width} to width of {_image}")
@Since("1.0")
public class ExprImageWidth extends SimpleExpression<Number> {

	static {
		Skript.registerExpression(ExprImageWidth.class, Number.class, ExpressionType.SIMPLE,
				"[skimage] [the] [pixel] width of [the] [image] %image%");
	}

	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		return true;
	}

	@Override
	protected Integer[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		 if (image == null) return new Integer[0];
		 return new Integer[] {image.getWidth()};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "image width of " + exprImage.toString(e, debug);
	}

}