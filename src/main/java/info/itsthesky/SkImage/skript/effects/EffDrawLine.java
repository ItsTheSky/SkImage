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

	private Expression<Number> exprSize;
	private Expression<Number> exprFromX, exprFromY;
	private Expression<Number> exprToX, exprToY;
	private Expression<Color> exprColor;
	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprSize = (Expression<Number>) exprs[0];
		exprFromX = (Expression<Number>) exprs[1];
		exprFromY = (Expression<Number>) exprs[2];
		exprToX = (Expression<Number>) exprs[3];
		exprToY = (Expression<Number>) exprs[4];
		exprColor = (Expression<Color>) exprs[5];
		exprImage = (Expression<BufferedImage>) exprs[6];
		return true;
	}

	@Override
	protected void execute(Event e) {
		Number fromX = exprFromX.getSingle(e);
		Number fromY = exprFromY.getSingle(e);
		Number toX = exprToX.getSingle(e);
		Number toY = exprToY.getSingle(e);
		Number size = exprSize.getSingle(e);
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (color == null || image == null || size == null || fromX == null || fromY == null || toX == null || toY == null) return;
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(size.intValue()));
		g2d.drawLine(fromX.intValue(), fromY.intValue(), toX.intValue(), toY.intValue());
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}