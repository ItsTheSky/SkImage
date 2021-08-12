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
import info.itsthesky.SkImage.skript.tools.Utils;
import org.bukkit.event.Event;

import java.awt.image.BufferedImage;

@Name("Darker Image")
@Description("Make any image with darker pixel and return it")
@Examples("set {_darker} to darker {_image} with force 1")
@Since("1.5")
public class ExprDarkerImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprDarkerImage.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] darker [image] %image% with [the] force %number%");
	}

	private Expression<BufferedImage> exprImage;
	private Expression<Integer> exprForce;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprForce = (Expression<Integer>) exprs[1];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		Integer force = exprForce.getSingle(e);
		if (image == null || force == null) return new BufferedImage[0];
		return new BufferedImage[] {Utils.darkerImage(Utils.copiedImage(image), force)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "darker version of image " + exprImage.toString(e, debug) + " with the force " + exprForce.toString(e, debug);
	}

}