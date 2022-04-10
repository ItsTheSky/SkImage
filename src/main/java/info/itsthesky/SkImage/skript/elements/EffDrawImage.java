package info.itsthesky.SkImage.skript.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Draw Image on Image")
@Description("Draw an Image on another Image")
@Examples("draw image {_image1} on {_image} at 0, 50")
@Since("1.0")
public class EffDrawImage extends EasyElement {

	static {
		Skript.registerEffect(EffDrawImage.class,
				"[skimage] draw [the] [image] %image% on [the] [other] [image] %image% at %number%[ ][,][ ]%number%");
	}

	private Expression<BufferedImage> exprImage1, exprImage2;
	private Expression<Number> exprX;
	private Expression<Number> exprY;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage1 = (Expression<BufferedImage>) exprs[0];
		exprImage2 = (Expression<BufferedImage>) exprs[1];
		exprX = (Expression<Number>) exprs[2];
		exprY = (Expression<Number>) exprs[3];
		return validate(exprImage2);
	}

	@Override
	protected void execute(@NotNull Event e) {
		BufferedImage image1 = exprImage1.getSingle(e);
		BufferedImage image2 = exprImage2.getSingle(e);
		Number x = exprX.getSingle(e);
		Number y = exprY.getSingle(e);
		if (image1 == null || image2 == null || y == null || x == null) return;
		Graphics2D g2d = image2.createGraphics();
		g2d.drawImage(image1, x.intValue(), y.intValue(), null);
		g2d.dispose();
		exprImage2.change(e, new BufferedImage[] {image2}, Changer.ChangeMode.SET);
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "draw image " + exprImage1.toString(e, debug) + " on the image " + exprImage2.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug);
	}

}
