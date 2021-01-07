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
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

@Name("Rounded Image")
@Description("Make an return the rounded version of the input Image")
@Examples("set {_image} to image rounded {_image}")
@Since("1.2")
public class ExprImageRounded extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprImageRounded.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] image (oval|roud[ed]) [of] %image%",
				"[skimage] rounded image %image%");
	}

	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		if (image == null) return new BufferedImage[0];
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = output.createGraphics();

		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, Math.round(image.getWidth()), Math.round(image.getHeight())));
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
	public Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "rounded version of image " + exprImage.toString(e, debug);
	}

}