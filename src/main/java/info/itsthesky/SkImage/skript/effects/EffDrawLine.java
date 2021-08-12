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

@Name("Draw Line on Image")
@Description("Draw a line on a specific image (with color, location and size)")
@Examples("draw line with size 5 from 50, 30 to 60, 40 with color from rgb 150, 30, 233 on {_image}")
@Since("1.0")
public class EffDrawLine extends Effect {

	static {
		Skript.registerEffect(EffDrawLine.class,
				"[skimage] draw line with [the] (size|width) %number% from %number%[ ][,][ ]%number% to %number%[ ][,][ ]%number% with [(color|colored)] %imagecolor% on [the] [image] %image%");
	}

	private Expression<Integer> exprSize;
	private Expression<Integer> exprFromX, exprFromY;
	private Expression<Integer> exprToX, exprToY;
	private Expression<Color> exprColor;
	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprSize = (Expression<Integer>) exprs[0];
		exprFromX = (Expression<Integer>) exprs[1];
		exprFromY = (Expression<Integer>) exprs[2];
		exprToX = (Expression<Integer>) exprs[3];
		exprToY = (Expression<Integer>) exprs[4];
		exprColor = (Expression<Color>) exprs[5];
		exprImage = (Expression<BufferedImage>) exprs[6];
		return true;
	}

	@Override
	protected void execute(Event e) {
		Integer fromX = exprFromX.getSingle(e);
		Integer fromY = exprFromY.getSingle(e);
		Integer toX = exprToX.getSingle(e);
		Integer toY = exprToY.getSingle(e);
		Integer size = exprSize.getSingle(e);
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (color == null || image == null || size == null || fromX == null || fromY == null || toX == null || toY == null) return;
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(size));
		g2d.drawLine(fromX, fromY, toX, toY);
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}