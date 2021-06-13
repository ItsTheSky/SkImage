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

@Name("Draw Rounded Rectangle on Image")
@Description("Draw a rectangle with rounded arc on a specific image (with color, location and size)")
@Examples("draw rounded rect at 0, 0 with size 50, 50 with color from rgb 50, 30, 60 with arc size 50, 50 on {_image}")
@Since("1.0")
public class EffDrawRoundRect extends Effect {

	static {
		Skript.registerEffect(EffDrawRoundRect.class,
				"[skimage] draw round[ed] rect[angle] [with anti[-]aliases] at [the [pixel] location] %number%[ ][,][ ]%number% with [the] size %number%[ ][,][ ]%number% with [(color|colored)] %imagecolor% with arc (size|pixel) %number%[ ][,][ ]%number% on [the] [image] %image%");
	}

	private Expression<Number> exprX;
	private Expression<Number> exprY;
	private Expression<Number> exprSizeX;
	private Expression<Number> exprSizeY;
	private Expression<Number> exprArcY;
	private Expression<Number> exprArcX;
	private Expression<Color> exprColor;
	private boolean hasAliases = false;
	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprX = (Expression<Number>) exprs[0];
		exprY = (Expression<Number>) exprs[1];
		exprSizeX = (Expression<Number>) exprs[2];
		exprSizeY = (Expression<Number>) exprs[3];
		exprColor = (Expression<Color>) exprs[4];
		exprArcX = (Expression<Number>) exprs[5];
		exprArcY = (Expression<Number>) exprs[6];
		exprImage = (Expression<BufferedImage>) exprs[7];
		hasAliases = parseResult.expr.contains("with anti");
		return true;
	}

	@Override
	protected void execute(Event e) {
		Number x = exprX.getSingle(e);
		Number y = exprY.getSingle(e);
		Number sizeX = exprSizeX.getSingle(e);
		Number sizeY = exprSizeY.getSingle(e);
		Number arcX = exprArcX.getSingle(e);
		Number arcY = exprArcY.getSingle(e);
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (x == null || y == null || sizeX == null || sizeY == null || color == null || image == null || arcX == null || arcY == null) return;
		Graphics2D g2d = image.createGraphics();
		if (hasAliases) {
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHints(rh);
		}
		g2d.setColor(color);
		g2d.fillRoundRect(x.intValue(), y.intValue(), sizeX.intValue(), sizeY.intValue(), arcX.intValue(), arcY.intValue());
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "draw rounded rectangle on " + exprImage.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug) + " with size " + exprSizeX.toString(e, debug) + ", " + exprSizeY.toString(e, debug);
	}

}