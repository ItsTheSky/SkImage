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

@Name("Resized Image")
@Description("Resize and return an Image with new width, height and with specific algorithm (Bytes, so 1, 2, 4, 8 and 16)")
@Examples("set {_image} to resized {_image} to size 50, 30 with algorithm 2")
@Since("1.0")
public class ExprResizedImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprResizedImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] resized [the] [image] %image% (with|to) [the] [size] %integer%[ ][,][ ]%integer% [with algo[rithm] %integer%]");
	}

	private Expression<BufferedImage> exprImage1;
	private Expression<Integer> exprSizeX, exprSizeY, exprAlgo;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage1 = (Expression<BufferedImage>) exprs[0];
		exprSizeX = (Expression<Integer>) exprs[1];
		exprSizeY = (Expression<Integer>) exprs[2];
		exprAlgo = (Expression<Integer>) exprs[3];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage1.getSingle(e);
		Integer sizeX = exprSizeX.getSingle(e);
		Integer sizeY = exprSizeY.getSingle(e);
		Integer algo = exprAlgo.getSingle(e);
		if (algo == null) {
			algo = 1;
		}
		if (sizeX == null || sizeY == null || image == null) return new BufferedImage[0];
		if (algo == 1 || algo == 2 || algo == 4 || algo == 8 || algo == 16) {
			Image temp = image.getScaledInstance(sizeX, sizeY, algo);
			BufferedImage resized = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = resized.createGraphics();
			g2d.drawImage(temp, 0, 0, null);
			g2d.dispose();
			return new BufferedImage[] {resized};
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
