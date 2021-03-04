package info.itsthesky.SkImage.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.skript.AsyncEffect;
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Name("Register Text Font")
@Description("Register new font from file or whole folder")
@Examples("register text font from \"font/\"")
@Since("1.3")
public class EffRegisterFont extends Effect {

	static {
		Skript.registerEffect(EffRegisterFont.class,
				"[skimage] register [the] [new] [text] font from [the] [(file|folder)] %string%");
	}

	private Expression<String> exprPath;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprPath = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected void execute(Event e) {
		String path = exprPath.getSingle(e);
		if (path == null) return;
		File file = new File(path);
		new Thread(() -> {
			if (file.isFile()) {
				try {
					GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
				} catch (FontFormatException | IOException fontFormatException) {
					fontFormatException.printStackTrace();
				}
			} else if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					try {
						GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
					} catch (FontFormatException | IOException fontFormatException) {
						fontFormatException.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "register new text font from path " + exprPath.toString(e, debug);
	}

}
