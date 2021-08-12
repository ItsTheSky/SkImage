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

@Name("Resized Image")
@Description("Resize and return an Image with new width, height and with specific algorithm (Bytes, so 1, 2, 4, 8 and 16)")
@Examples("set {_image} to resized {_image} to size 50, 30 with algorithm 2")
@Since("1.0")
public class ExprResizedImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprResizedImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] resized [the] [image] %image% (with|to) [the] [size] %number%[ ][,][ ]%number% [with algo[rithm] %number%]");
	}

	private Expression<BufferedImage> exprImage1;
	private Expression<Number> exprSizeX, exprSizeY, exprAlgo;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage1 = (Expression<BufferedImage>) exprs[0];
		exprSizeX = (Expression<Number>) exprs[1];
		exprSizeY = (Expression<Number>) exprs[2];
		exprAlgo = (Expression<Number>) exprs[3];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage1.getSingle(e);
		Number sizeX = exprSizeX.getSingle(e);
		Number sizeY = exprSizeY.getSingle(e);
		Number algo = exprAlgo.getSingle(e);
		if (algo == null) algo = 1;
		if (sizeX == null || sizeY == null || image == null) return new BufferedImage[0];
		if (algo.intValue() == 1 || algo.intValue() == 2 || algo.intValue() == 4 || algo.intValue() == 8 || algo.intValue() == 16) {
			return new BufferedImage[] {
					Utils.resizedImage(Utils.copiedImage(image), sizeX.intValue(), sizeY.intValue(), algo.intValue())
			};
		} else {
			Skript.error("The algorithm ID is not valid. Valid ones are 1, 2, 4, 8 and 16 !");
			return new BufferedImage[0];
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "resized image " + exprImage1.toString(e, debug) + " to size " + exprSizeX.toString(e, debug) + ", " + exprSizeY.toString(e, debug);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}
}
