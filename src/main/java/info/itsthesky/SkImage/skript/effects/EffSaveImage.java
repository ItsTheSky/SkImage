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
import info.itsthesky.SkImage.SkImage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Name("Save Image to File")
@Description("Save an image to the directory specified")
@Examples("save image {_image} to file \"plugins/Images/image.png\"")
@Since("1.0")
public class EffSaveImage extends Effect {

	static {
		Skript.registerEffect(EffSaveImage.class,
				"[skimage] save [the] image %image% to [the] [file] %string%");
	}

	private Expression<BufferedImage> exprImage;
	private Expression<String> exprPath;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprImage = (Expression<BufferedImage>) exprs[0];
		exprPath = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	protected void execute(Event e) {
		String path = exprPath.getSingle(e);
		BufferedImage image = exprImage.getSingle(e);
		if (path == null || image == null)
			return;
		File file = new File(path);
		if (file.exists()) {
			File configFile = new File(SkImage.getInstance().getDataFolder(), "config.yml");
			FileConfiguration configConfig = YamlConfiguration.loadConfiguration(configFile);
			Boolean replaceImage = configConfig.getBoolean("ImageReplacement");
			if (!replaceImage) {
				Skript.error("The file called " + path + " already exist! Disable this non-replacement in the node `ImageReplacement` from `plugins/SkImage/config.yml`");
				return;
			}
			file.delete();
			file = new File(path);
		}
		List<String> alls = Arrays.asList(path.split("\\."));
		String ext = alls.get(alls.size()-1);
		try {
			ImageIO.write(image, ext, file);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "save image " + exprImage.toString(e, debug) + " to path " + exprPath.toString(e, debug);
	}

}