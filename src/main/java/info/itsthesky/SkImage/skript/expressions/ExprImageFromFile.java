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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

@Name("Image from File")
@Description("Get and return an image from locale File")
@Examples("set {_image2} to image from file \"plugins/temp/image.png\"")
@Since("1.0")
public class ExprImageFromFile extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprImageFromFile.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] [the] image from [the] [file] %string%");
	}

	private Expression<String> exprFile;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprFile = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		String file = exprFile.getSingle(e);
		if (file == null) return new BufferedImage[0];

		File image = new File(file);
		if (!image.exists()) {
			return new BufferedImage[0];
		}
		Path path = Paths.get(file);
		try {
			return new BufferedImage[] {ImageIO.read(path.toFile())};
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return new BufferedImage[0];
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
		return "image from the file " + exprFile.toString(e, debug);
	}

}