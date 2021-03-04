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

@Name("Copied Image")
@Description("Return the same image, with same pixel and data, but not the same instance.")
@Examples("set {_v2} to copied {_v1}")
@Since("1.6")
public class ExprCopiedImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprCopiedImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] (duplicated|copied) [image] %image%");
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
		return new BufferedImage[] {Utils.copiedImage(image)};
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