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
import info.itsthesky.SkImage.Utils;
import org.bukkit.event.Event;

import java.awt.image.BufferedImage;

@Name("Black&White Image")
@Description("Make the image without any other color than gray level, and return it.")
@Examples("set {_blackandwhite} to black and white {_image}")
@Since("1.5")
public class ExprBlackAndWhiteImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprBlackAndWhiteImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] (black[ ]and[ ]white|baw|gray level) [image] %image%");
	}

	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		if (image == null) return new BufferedImage[0];
		int w = image.getWidth();
		int h = image.getHeight();
		return new BufferedImage[] {Utils.blackAndWhite(Utils.copiedImage(image))};
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
		return "black and white version of image " + exprImage.toString(e, debug);
	}

}