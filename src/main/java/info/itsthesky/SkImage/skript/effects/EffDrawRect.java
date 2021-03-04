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
import info.itsthesky.SkImage.skript.tools.skript.AsyncEffect;
import org.bukkit.event.Event;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Name("Draw Rectangle on Image")
@Description("Draw a rectangle on a specific image (with color, location and size)")
@Examples("draw rect at 0, 0 with size 50, 50 with color from rgb 50, 30, 60 on {_image}")
@Since("1.0")
public class EffDrawRect extends Effect {

	static {
		Skript.registerEffect(EffDrawRect.class,
				"[skimage] draw rect[angle] at [the [pixel] location] %integer%[ ][,][ ]%integer% with [the] size %integer%[ ][,][ ]%integer% with [(color|colored)] %imagecolor% on [the] [image] %image%");
	}

	private Expression<Integer> exprX, exprY;
	private Expression<Integer> exprSizeX, exprSizeY;
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
		exprImage = (Expression<BufferedImage>) exprs[5];
		return true;
	}

	@Override
	protected void execute(Event e) {
		Integer x = exprX.getSingle(e);
		Integer y = exprY.getSingle(e);
		Integer sizeX = exprSizeX.getSingle(e);
		Integer sizeY = exprSizeY.getSingle(e);
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (x == null || y == null || sizeX == null || sizeY == null || color == null || image == null) return;
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.fillRect(x, y, sizeX, sizeY);
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "draw rectangle on " + exprImage.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug) + " with size " + exprSizeX.toString(e, debug) + ", " + exprSizeY.toString(e, debug);
	}

}