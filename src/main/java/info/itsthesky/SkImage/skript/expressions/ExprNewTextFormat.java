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
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;

@Name("New Font")
@Description("Create an return a new Font")
@Examples("set {_text} to new font style with font name \"Arial\" and with size 16")
@Since("1.0")
public class ExprNewTextFormat extends SimpleExpression<Font> {

	static {
		Skript.registerExpression(ExprNewTextFormat.class, Font.class, ExpressionType.SIMPLE,
				"[skimage] new font[ ](format|style) with [the] [font] [name] %string% and with [the] [font] size %integer%");
	}

	private Expression<String> exprFont;
	private Expression<Integer> exprSize;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprFont = (Expression<String>) exprs[0];
		exprSize = (Expression<Integer>) exprs[1];
		return true;
	}

	@Override
	protected Font[] get(Event e) {
		String font = exprFont.getSingle(e);
		Integer size = exprSize.getSingle(e);
		if (font == null || size == null) return new Font[0];
		Font f = new Font(font, Font.PLAIN, size);
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
		return "";
	}

}