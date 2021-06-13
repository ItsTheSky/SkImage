package info.itsthesky.SkImage.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Draw Rectangle on Image")
@Description("Draw a rectangle on a specific image (with color, location and size)")
@Examples("draw rect at 0, 0 with size 50, 50 with color from rgb 50, 30, 60 on {_image}")
@Since("1.0")
public class EffDrawRect extends Effect {

	static {
		Skript.registerEffect(EffDrawRect.class,
				"[skimage] draw rect[angle] [with anti[-]aliases] at [the [pixel] location] %integer%[ ][,][ ]%integer% with [the] size %integer%[ ][,][ ]%integer% with [(color|colored)] %imagecolor% [[with] [rotation] %-number% degree[s] [angle] [using origin location %-number%,[ ]%-number%]] on [the] [image] %image%");
	}

	private Expression<Number> exprX, exprY;
	private Expression<Number> exprSizeX, exprSizeY;
	private Expression<Number> exprRotation;
	private Expression<Number> exprRotOriginX, exprRotOriginY;
	private Expression<Color> exprColor;
	private Expression<BufferedImage> exprImage;
	private boolean hasAliases = false;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprX = (Expression<Number>) exprs[0];
		exprY = (Expression<Number>) exprs[1];
		exprSizeX = (Expression<Number>) exprs[2];
		exprSizeY = (Expression<Number>) exprs[3];
		exprColor = (Expression<Color>) exprs[4];
		exprRotation = (Expression<Number>) exprs[5];
		exprRotOriginX = (Expression<Number>) exprs[6];
		exprRotOriginY = (Expression<Number>) exprs[7];
		exprImage = (Expression<BufferedImage>) exprs[8];
		hasAliases = parseResult.expr.contains("with anti");
		return true;
	}

	@Override
	protected void execute(Event e) {
		Number x = exprX.getSingle(e);
		Number y = exprY.getSingle(e);
		Number sizeX = exprSizeX.getSingle(e);
		Number sizeY = exprSizeY.getSingle(e);
		Number rot = exprRotation == null ? null : (exprRotation.getSingle(e) == null ? null : exprRotation.getSingle(e));
		Number rotPosX = exprRotOriginX == null ? null : (exprRotOriginX.getSingle(e) == null ? null : exprRotOriginX.getSingle(e));
		Number rotPosY = exprRotOriginY == null ? null : (exprRotOriginY.getSingle(e) == null ? null : exprRotOriginY.getSingle(e));
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (x == null || y == null || sizeX == null || sizeY == null || color == null || image == null) return;
		Graphics2D g2d = image.createGraphics();
		if (hasAliases) {
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHints(rh);
		}
		if (rot != null) {
			if (rotPosX == null || rotPosY == null) {
				g2d.rotate(Math.toRadians(rot.intValue()), x.intValue(), y.intValue());
			} else {
				g2d.rotate(Math.toRadians(rot.intValue()), rotPosX.intValue() - x.intValue(), rotPosX.intValue() - y.intValue());
			}
		}
		g2d.setColor(color);
		g2d.fillRect(x.intValue(), y.intValue(), sizeX.intValue(), sizeY.intValue());
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "draw rectangle on " + exprImage.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug) + " with size " + exprSizeX.toString(e, debug) + ", " + exprSizeY.toString(e, debug);
	}

}