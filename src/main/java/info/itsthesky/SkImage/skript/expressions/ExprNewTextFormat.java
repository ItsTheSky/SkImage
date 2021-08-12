package info.itsthesky.SkImage.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.SkImage;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

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

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprFont = (Expression<String>) exprs[0];
		exprStyle = (Expression<String>) exprs[1];
		exprSize = (Expression<Number>) exprs[2];
		return true;
	}

	@Override
	protected Font[] get(@NotNull Event e) {
		String font = exprFont.getSingle(e);
		Number size = exprSize.getSingle(e);
		String style = exprStyle == null ? "plain" : (exprStyle.getSingle(e) == null ? "plain" : exprStyle.getSingle(e));
		if (font == null || size == null || style == null) return new Font[0];
		if (!Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).contains(font)) {
			SkImage.getInstance().getLogger().severe("The font named " + font + " doesn't exist or is not loaded in the JVM!");
			return new Font[0];
		}
		int styleID;
		switch (style) {
			case "bold":
			case "Bold":
				styleID = Font.BOLD;
				break;
			case "italic":
			case "Italic":
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