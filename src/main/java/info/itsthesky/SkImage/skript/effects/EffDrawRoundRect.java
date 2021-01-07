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
				"[skimage] draw round[ed] rect[angle] at [the [pixel] location] %integer%[ ][,][ ]%integer% with [the] size %integer%[ ][,][ ]%integer% with [(color|colored)] %imagecolor% with arc (size|pixel) %integer%[ ][,][ ]%integer% on [the] [image] %image%");
	}

	private Expression<Integer> exprX, exprY;
	private Expression<Integer> exprSizeX, exprSizeY;
	private Expression<Integer> exprArcX, exprArcY;
	private Expression<Color> exprColor;
	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprX = (Expression<Integer>) exprs[0];
		exprY = (Expression<Integer>) exprs[1];
		exprSizeX = (Expression<Integer>) exprs[2];
		exprSizeY = (Expression<Integer>) exprs[3];
		exprColor = (Expression<Color>) exprs[4];
		exprArcX = (Expression<Integer>) exprs[5];
		exprArcY = (Expression<Integer>) exprs[6];
		exprImage = (Expression<BufferedImage>) exprs[7];
		return true;
	}

	@Override
	protected void execute(Event e) {
		Integer x = exprX.getSingle(e);
		Integer y = exprY.getSingle(e);
		Integer sizeX = exprSizeX.getSingle(e);
		Integer sizeY = exprSizeY.getSingle(e);
		Integer arcX = exprArcX.getSingle(e);
		Integer arcY = exprArcY.getSingle(e);
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (x == null || y == null || sizeX == null || sizeY == null || color == null || image == null || arcX == null || arcY == null) return;
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.fillRoundRect(x, y, sizeX, sizeY, arcX, arcY);
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "draw rounded rectangle on " + exprImage.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug) + " with size " + exprSizeX.toString(e, debug) + ", " + exprSizeY.toString(e, debug);
	}

}