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

@Name("Draw Image on Image")
@Description("Draw an Image on another Image")
@Examples("draw image {_image1} on {_image} at 0, 50")
@Since("1.0")
public class EffDrawImage extends Effect {

	static {
		Skript.registerEffect(EffDrawImage.class,
				"[skimage] draw [the] [image] %image% on [the] [other] [image] %image% at %integer%[ ][,][ ]%integer%");
	}

	private Expression<BufferedImage> exprImage1, exprImage2;
	private Expression<Integer> exprX, exprY;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage1 = (Expression<BufferedImage>) exprs[0];
		exprImage2 = (Expression<BufferedImage>) exprs[1];
		exprX = (Expression<Integer>) exprs[2];
		exprY = (Expression<Integer>) exprs[3];
		return true;
	}

	@Override
	protected void execute(Event e) {
		BufferedImage image1 = exprImage1.getSingle(e);
		BufferedImage image2 = exprImage2.getSingle(e);
		Integer x = exprX.getSingle(e);
		Integer y = exprY.getSingle(e);
		if (image1 == null || image2 == null || y == null || x == null) return;
		Graphics2D g2d = image2.createGraphics();
		g2d.drawImage(image1, x, y, null);
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
