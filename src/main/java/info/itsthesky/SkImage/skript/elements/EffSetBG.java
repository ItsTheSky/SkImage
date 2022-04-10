package info.itsthesky.SkImage.skript.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.Utils;
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Set Image Background")
@Description("Fill the entire image with unique color")
@Examples("change background of {_image} to color from rgb 0, 0, 0")
@Since("1.0")
public class EffSetBG extends Effect {

	static {
		Skript.registerEffect(EffSetBG.class,
				"[skimage] (set|change|fill) back[ground] of [the] [image] %image% (to|with) [the] [color] %color%");
	}

	private Expression<BufferedImage> exprImage;
	private Expression<ch.njol.skript.util.Color> exprColor;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprColor = (Expression<ch.njol.skript.util.Color>) exprs[1];
		return true;
	}

	@Override
	protected void execute(Event e) {
		ch.njol.skript.util.Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if ( color == null || image == null) return;
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Utils.convert(color));
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "set background of " + exprImage.toString(e, debug) + " to color " + exprColor.toString(e, debug);
	}

}