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

import java.awt.image.BufferedImage;

@Name("New Image")
@Description("Create an return a new Image")
@Examples("set {_image} to new image with size 150, 200")
@Since("1.0")
public class ExprNewImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprNewImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] new image with [width] %integer% and [with] [height] %integer%",
				"[skimage] new image with size %integer%[ ][,][ ]%integer%");
	}

	private Expression<Integer> exprWidth;
	private Expression<Integer> exprHeight;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprWidth = (Expression<Integer>) exprs[0];
		exprHeight = (Expression<Integer>) exprs[1];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		Integer width = exprWidth.getSingle(e);
		Integer height = exprHeight.getSingle(e);
		if (width != null && height != null) {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
			return new BufferedImage[] {image};
		}
		return new BufferedImage[0];
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
		return "image with width " + exprWidth.toString(e, debug) + " and with height " + exprHeight.toString(e, debug);
	}

}