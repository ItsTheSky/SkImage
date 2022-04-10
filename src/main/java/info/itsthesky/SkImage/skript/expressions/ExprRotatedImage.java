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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;

import static info.itsthesky.SkImage.skript.tools.skript.EasyElement.*;

@Name("Rotated Image")
@Description("Rotate an image with a specific angle, and returns it.")
@Since("1.9")
@Examples({"set {_image} to rotated {_image} by 50 degrees",
"set {_image} to rotated {_image} by 30°"})
public class ExprRotatedImage extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(
				ExprRotatedImage.class,
				BufferedImage.class,
				ExpressionType.COMBINED,
				"rotated [image] %image% (by|of|to) %number%[ ][(degree[s]|°)]"
		);
	}

	private Expression<BufferedImage> exprImage;
	private Expression<Number> exprAngle;

	@Nullable
	@Override
	protected BufferedImage[] get(@NotNull Event e) {
		final BufferedImage image = parseSingle(exprImage, e, null);
		final Number angle = parseSingle(exprAngle, e, null);
		if (anyNull(image, angle))
			return new BufferedImage[0];
		return new BufferedImage[] {Utils.rotate(image, angle.doubleValue())};
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "rotated " + exprImage.toString(e, debug) + " by " + exprAngle.toString(e, debug) + " degrees";
	}

	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprAngle = (Expression<Number>) exprs[1];
		return true;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public @NotNull Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}
}
