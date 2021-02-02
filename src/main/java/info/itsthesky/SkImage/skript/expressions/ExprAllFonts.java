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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Name("All Registered Fonts")
@Description("Returns list of all active and enabled font")
@Examples("set {_fonts::*} to all registered font")
@Since("1.3")
public class ExprAllFonts extends SimpleExpression<String[]> {

	static {
		Skript.registerExpression(ExprAllFonts.class, String[].class, ExpressionType.SIMPLE,
				"[skimage] all [registered] [image] font[s]");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected String[][] get(Event e) {
		List<String> s = new ArrayList<>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		return new String[][] {s.toArray(new String[0])};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String[]> getReturnType() {
		return String[].class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "all registered font";
	}

}