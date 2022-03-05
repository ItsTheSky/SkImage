package info.itsthesky.SkImage.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.SkriptColor;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.TextInfo;
import info.itsthesky.SkImage.skript.tools.Utils;
import info.itsthesky.SkImage.skript.tools.skript.EasyEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

@Name("New Text Info")
@Description({"Create a new text with a specific graphic style, such as:",
"- Color",
"- Font",
"- Centered (horizontally / vertically)"})
@Since("1.5")
public class ExprNewTextInfo extends SimpleExpression<TextInfo> {

	static {
		Skript.registerExpression(
				ExprNewTextInfo.class,
				TextInfo.class,
				ExpressionType.COMBINED,
				"[(create|make)] new text[-info] %string% [with [the] [color] %color%] [with [the] font [style] %font%] [(1¦center[ed] vert[ically])] [(2¦center[ed] hori[zontally])]"
		);
	}

	private Expression<ch.njol.skript.util.Color> exprColor;
	private Expression<Font> exprFont;
	private Expression<String> exprText;
	private boolean isCenterH;
	private boolean isCenterV;

	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
		exprText = (Expression<String>) exprs[0];
		exprColor = (Expression<ch.njol.skript.util.Color>) exprs[1];
		exprFont = (Expression<Font>) exprs[2];
		isCenterV = (parseResult.mark & 1) != 0;
		isCenterH = (parseResult.mark & 2) != 0;
		return true;
	}

	@Nullable
	@Override
	protected TextInfo[] get(@NotNull Event e) {
		final String text = EasyEffect.parseSingle(exprText, e, null);
		final ch.njol.skript.util.Color color = EasyEffect.parseSingle(exprColor, e, SkriptColor.BLACK);
		final Font font = EasyEffect.parseSingle(exprFont, e, Font.getFont("Arial"));
		if (EasyEffect.anyNull(text))
			return new TextInfo[0];
		return new TextInfo[] {new TextInfo(
				text, font, Utils.convert(color),
				isCenterV, isCenterH,
				false, null, null
		)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public @NotNull Class<? extends TextInfo> getReturnType() {
		return TextInfo.class;
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "new text info with text " + exprText.toString(e, debug)
				+ (exprColor != null ? " with color " + exprColor.toString(e, debug) : "")
				+ (exprFont != null ? " with font " + exprFont.toString(e, debug) : "")
				+ (isCenterH ? " centered horizontally" : "")
				+ (isCenterV ? " centered vertically" : "")
				;
	}
}
