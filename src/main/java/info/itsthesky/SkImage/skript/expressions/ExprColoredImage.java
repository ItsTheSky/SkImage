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

@Name("Colored Image")
@Description("Re-color each pixel of the image, by adding both image and color chose RGB value, and return it.")
@Examples("set {_tinted} to tinted image {_image} with color red")
@Since("1.5")
public class ExprColoredImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprColoredImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] (tinted|colored) [image] %image% with [the] [(color|theme)] %imagecolor%");
	}

	private Expression<BufferedImage> exprImage;
	private Expression<Color> exprColor;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprColor = (Expression<Color>) exprs[1];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		Color color = exprColor.getSingle(e);
		if (image == null || color == null) return new BufferedImage[0];
		return new BufferedImage[] {Utils.tinted(Utils.copiedImage(image), color)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "tinted version of image " + exprImage.toString(e, debug) + " with color " + exprColor.toString(e, debug);
	}

}