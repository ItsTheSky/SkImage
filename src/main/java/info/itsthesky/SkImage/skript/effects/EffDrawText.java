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

@Name("Draw Text on Image")
@Description("Draw text at specific pixel location and with specific color on image")
@Examples("draw \"Hello World :D\" with font style {_font} at 0, 50 with color from rgb 50, 32, 12 on {_image}")
@Since("1.0")
public class EffDrawText extends Effect {

	static {
		Skript.registerEffect(EffDrawText.class,
				"[skimage] draw [text] %string% with [the] font [style] %font% at [x] %integer%[ ](,|and)[ ][y] %integer% with [color] %imagecolor% on [the] [image] %image%");
	}

	private Expression<String> exprText;
	private Expression<Font> exprFont;
	private Expression<Integer> exprX, exprY;
	private Expression<Color> exprColor;
	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprText = (Expression<String>) exprs[0];
		exprFont = (Expression<Font>) exprs[1];
		exprX = (Expression<Integer>) exprs[2];
		exprY = (Expression<Integer>) exprs[3];
		exprColor = (Expression<Color>) exprs[4];
		exprImage = (Expression<BufferedImage>) exprs[5];
		return true;
	}

	@Override
	protected void execute(Event e) {
		String text = exprText.getSingle(e);
		Font font = exprFont.getSingle(e);
		Integer x = exprX.getSingle(e);
		Integer y = exprY.getSingle(e);
		Color color = exprColor.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (text == null || x == null || y == null || image == null) return;
		Graphics2D g2d = image.createGraphics();
		g2d.setFont(font);
		g2d.setColor(color);
		g2d.drawString(text, x, y);
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
