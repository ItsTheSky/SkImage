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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Name("Image from URL")
@Description("Get and return an image from web URL")
@Examples("set {_image2} to image from url \"https://d1fmx1rbmqrxrr.cloudfront.net/cnet/optim/i/edit/2019/04/eso1644bsmall__w770.jpg\"")
@Since("1.0")
public class ExprImageFromURL extends SimpleExpression<BufferedImage> {

	static {
		Skript.registerExpression(ExprImageFromURL.class, BufferedImage.class, ExpressionType.SIMPLE,
				"[skimage] [the] image from [the] [url] %string%");
	}

	private Expression<String> exprURL;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprURL = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected BufferedImage[] get(Event e) {
		String url = exprURL.getSingle(e);
		if (url == null) return new BufferedImage[0];
		BufferedImage newImage = null;
		try {
			URLConnection co = new URL(url).openConnection();
			co.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36 OPR/55.0.2994.61");
			newImage = ImageIO.read(co.getInputStream());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		if (newImage == null) {
			return new BufferedImage[0];
		}
		return new BufferedImage[] {newImage};
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
		return "image from the url " + exprURL.getSingle(e);
	}

}