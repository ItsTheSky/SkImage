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
import info.itsthesky.SkImage.skript.tools.TextInfo;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("Draw Text on Image")
@Description({"Draw advanced graphic text at a specific image location.",
"The aliases toggle either the font will be smoothed or not."})
@Examples("draw new text \"Hello world\" with color red with font \"Arial Black\" centered horizontally")
@Since("1.4")
public class EffDrawText extends Effect {

	static {
		Skript.registerEffect(EffDrawText.class,
				"[skimage] draw [text] %textinfo% [(1¦with anti aliases)] at [x] %number%[ ](,|and)[ ][y] %number% on [the] [image] %image%");
	}

	private Expression<TextInfo> exprText;
	private Expression<Font> exprFont;
	private Expression<Number> exprX, exprY;
	private Expression<BufferedImage> exprImage;
	private boolean antiAliases;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprText = (Expression<TextInfo>) exprs[0];
		exprX = (Expression<Number>) exprs[1];
		exprY = (Expression<Number>) exprs[2];
		exprImage = (Expression<BufferedImage>) exprs[3];
		antiAliases = parseResult.mark == 1;
		return true;
	}

	@Override
	@SuppressWarnings("ALL")
	protected void execute(Event e) {
		TextInfo text = exprText.getSingle(e);
		Number x = exprX.getSingle(e);
		Number y = exprY.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (EasyElement.anyNull(text, x, y, image))
			return;
		text.apply(image, antiAliases, x.intValue(), y.intValue());
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "draw text " + exprText.toString(e, debug) + " on " + exprImage.toString(e, debug) + " at " + exprX.toString(e, debug) + ", " + exprY.toString(e, debug);
	}

}
