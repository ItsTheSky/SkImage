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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Name("Image from Base64")
@Description("Get and return an image from Base64 encoding")
@Examples("set {_image2} to image from base64 \"<base64>\"")
@Since("1.4")
public class ExprImageFromBase64 extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprImageFromBase64.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] [the] image from [the] [base64] [value] %string%");
	}

	private Expression<String> exprBase64;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprBase64 = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		String base64 = exprBase64.getSingle(e);
		if (base64 == null) return new BufferedImage[0];
		InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
		BufferedImage bImage2 = null;
		try {
			bImage2 = ImageIO.read(stream);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return new BufferedImage[] {bImage2};
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
		return "image from the base64 string " + exprBase64.toString(e, debug);
	}

}