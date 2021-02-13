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
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bukkit.event.Event;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Name("Base64 from Image")
@Description("Get and return the Base64 string of an Image")
@Examples("set {_base64} to base64 string from image {_image}")
@Since("1.4")
public class ExprBase64FromImage extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprBase64FromImage.class, String.class, ExpressionType.SIMPLE,
				"[skimage] [the] base64 [string] from [the] [image] %image%");
	}

	private Expression<BufferedImage> exprImage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(Event e) {
		BufferedImage image = exprImage.getSingle(e);
		if (image == null) return new String[0];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		return new String[] {Base64.encode(bytes)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "base64 string from the image " + exprImage.toString(e, debug);
	}

}