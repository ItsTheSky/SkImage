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

import java.awt.image.BufferedImage;

@Name("Blur Image")
@Description("Make the image blurred and return it. Can't remove black border, expect by resizing the image.")
@Examples("set {_blur} to blurred image {_image} with force 5")
@Since("1.5")
public class ExprImageBlurred extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprImageBlurred.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] blur[red] [image] %image% with [the] (force|power) %integer%");
	}

	private Expression<BufferedImage> exprImage;
	private Expression<Integer> exprForce;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprForce = (Expression<Integer>) exprs[1];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		Integer force = exprForce.getSingle(e);
		if (image == null || force == null) return new BufferedImage[0];
		int w = image.getWidth();
		int h = image.getHeight();
		return new BufferedImage[] {Utils.blur(Utils.copiedImage(image), force)};
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
		return "blurred version of image " + exprImage.toString(e, debug) + " with force " + exprForce.toString(e, debug);
	}

}