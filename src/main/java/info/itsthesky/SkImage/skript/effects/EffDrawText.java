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
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

@Name("Draw Text on Image")
@Description("Draw text at specific pixel location and with specific color on image. Use `with align center` at the end to make the text center.")
@Examples("draw \"Hello World :D\" with font style {_font} at 0, 50 with color from rgb 50, 32, 12 on {_image}")
@Since("1.0")
public class EffDrawText extends Effect {

	static {
		Skript.registerEffect(EffDrawText.class,
				"[skimage] draw [text] %string% [with anti[-]aliases] with [the] font [style] %font% at [x] %integer%[ ](,|and)[ ][y] %integer% with [color] %imagecolor% on [the] [image] %image%",
				"[skimage] draw [text] %string% [with anti[-]aliases] with [the] font [style] %font% at [x] %integer%[ ](,|and)[ ][y] %integer% with [color] %imagecolor% on [the] [image] %image% with align center");
	}

	private Expression<String> exprText;
	private Expression<Font> exprFont;
	private Expression<Integer> exprX, exprY;
	private Expression<Color> exprColor;
	private Expression<BufferedImage> exprImage;
	private boolean hasAliases = false;
	private Integer isCenter;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprText = (Expression<String>) exprs[0];
		exprFont = (Expression<Font>) exprs[1];
		exprX = (Expression<Integer>) exprs[2];
		exprY = (Expression<Integer>) exprs[3];
		exprColor = (Expression<Color>) exprs[4];
		exprImage = (Expression<BufferedImage>) exprs[5];
		isCenter = matchedPattern;
		hasAliases = parseResult.expr.contains("with anti");
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
		if (hasAliases) {
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHints(rh);
		}
		g2d.setFont(font);
		g2d.setColor(color);
		if (isCenter == 0) {
			g2d.drawString(text, x, y);
		} else {
			TextLayout textLayout = new TextLayout(text, g2d.getFont(),
					g2d.getFontRenderContext());
			double textHeight = textLayout.getBounds().getHeight();
			double textWidth = textLayout.getBounds().getWidth();
			g2d.drawString(text, (x / 2 - (int) textWidth / 2),
					(y / 2 + (int) textHeight / 2));
		}
		g2d.dispose();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "draw text " + exprText.toString(e, debug) + " on " + exprImage.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug);
	}

}
