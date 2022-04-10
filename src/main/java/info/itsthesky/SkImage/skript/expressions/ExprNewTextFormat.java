package info.itsthesky.SkImage.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.config.Node;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.SkImage;
import info.itsthesky.SkImage.skript.tools.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Name("New Font")
@Description("Create a new font style with a specific font name, a specific size and an optional specific style (plain, bold or italic)")
@Examples("set {_text} to new font style with font name \"Arial\" and with size 16")
@Since("1.0")
public class ExprNewTextFormat extends SimpleExpression<Font> {

	static {
		Skript.registerExpression(ExprNewTextFormat.class, Font.class, ExpressionType.SIMPLE,
				"[skimage] new font[ ](format|style) with [the] [font] [name] %string% [[and] with [the] [style] %-string%] and with [the] [font] size %number%");
	}

	private Expression<String> exprFont;
	private Expression<String> exprStyle;
	private Expression<Number> exprSize;
	private Node node;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprFont = (Expression<String>) exprs[0];
		exprStyle = (Expression<String>) exprs[1];
		exprSize = (Expression<Number>) exprs[2];
		node = SkriptLogger.getNode();
		return true;
	}

	@Override
	protected Font[] get(@NotNull Event e) {
		String font = exprFont.getSingle(e);
		Number size = exprSize.getSingle(e);
		String style = exprStyle == null ? "plain" : (exprStyle.getSingle(e) == null ? "plain" : exprStyle.getSingle(e));
		if (font == null || size == null || style == null) return new Font[0];

		boolean wasFound = false;
		List<String> similar = null;
		for (String name : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
			if (name.toLowerCase().equals(font.toLowerCase())) {
				wasFound = true;
				continue;
			}
			if (name.toLowerCase().contains(font.toLowerCase()) ||
					font.toLowerCase().contains(name.toLowerCase())) {
				if (similar == null)
					similar = new ArrayList<>();
				similar.add(name);
			}
		}
		if (!wasFound) {
			Utils.error(node, "The font named '"+font+"' does not exist / is not loaded." +
					" Similar fonts found: " + (similar == null ? "none" : String.join(", ", similar)));
			return new Font[0];
		}

		int styleID;
		switch (style.toLowerCase()) {
			case "bold":
				styleID = Font.BOLD;
				break;
			case "italic":
				styleID = Font.ITALIC;
				break;
			default:
				styleID = Font.PLAIN;
		}
		Font f = new Font(font, styleID, size.intValue());
		return new Font[] {f};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Font> getReturnType() {
		return Font.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "new text format with font name " + exprFont.toString(e, debug) + " and size " + exprSize.toString(e, debug);
	}

}