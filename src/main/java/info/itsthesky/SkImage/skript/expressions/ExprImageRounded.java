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
import info.itsthesky.SkImage.skript.tools.skript.EasyEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

@Name("Rounded Image")
@Description("Make an return the rounded version of the input Image")
@Examples({"set {_image} to image rounded {_image}",
"set {_image} to rounded {_image} with size 100 # Since SkImage v1.8"})
@Since("1.2, 1.8 (Way to change the angle)")
public class ExprImageRounded extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprImageRounded.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] [image] (oval|roud[ed]) [of] %image% [with [the] size %-number%]",
				"[skimage] rounded [image] %image% [with [the] size %-number%]");
	}

	private Expression<BufferedImage> exprImage;
	private Expression<Number> exprAngle;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprAngle = (Expression<Number>) exprs[1];
		return true;
	}

	@Override
	protected BufferedImage[] get(@NotNull Event e) {
		final BufferedImage image = exprImage.getSingle(e);
		final Number angle = EasyEffect.parseSingle(exprAngle, e, null);
		if (image == null) return new BufferedImage[0];
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		final float x = angle == null ? Math.round(w) : angle.floatValue();
		final float y = angle == null ? Math.round(h) : angle.floatValue();

		Graphics2D g2 = output.createGraphics();

		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, x, y));
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		return new BufferedImage[] {output};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public @NotNull Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "rounded version of image " + exprImage.toString(e, debug);
	}

}