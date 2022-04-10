package info.itsthesky.SkImage.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.SkImage;
import info.itsthesky.SkImage.skript.tools.GifSequenceWriter;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Name("New Gif Manager")
@Description({"Initialise a new gif manager, where images will be written into.",
"You can specific a delay between images, and if the gif is infinit (will restart once it reached the end) or not."})
@Examples("set {_img::1} to new image with size 100, 100\n" +
        "set {_img::2} to new image with size 100, 100\n" +
        "set {_img::3} to new image with size 100, 100\n" +
        "set {_img::4} to new image with size 100, 100\n" +
        "set {_img::5} to new image with size 100, 100\n" +
        "set {_gif} to new infinite gif manager with new image with size 100, 100 with delay 1 and store it in file \"test.gif\"\n" +
        "loop 5 times:\n" +
        "\tset {_r} to random integer between 0 and 255\n" +
        "\tset {_g} to random integer between 0 and 255\n" +
        "\tset {_b} to random integer between 0 and 255\n" +
        "\tchange background of {_img::%loop-number%} to color from rgb {_r}, {_g}, {_b}\n" +
        "\tadd {_img::%loop-number%} to images of {_gif}\n" +
        "\n" +
        "close gif {_gif}")
public class ExprNewGifManager extends SimpleExpression<GifSequenceWriter> {

    static {
        Skript.registerExpression(
                ExprNewGifManager.class,
                GifSequenceWriter.class,
                ExpressionType.COMBINED,
                "[a] [new] [(1¦infinite)] gif [(sequence|writer)] manager with [the] [image] %image% with [the] delay %number% and store (it|the gif) in [the] [file] %string%"
        );
    }

    private boolean isInfinite;
    private Expression<BufferedImage> exprImage;
    private Expression<String> exprFile;
    private Expression<Number> exprDelay;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprImage = (Expression<BufferedImage>) exprs[0];
        exprDelay = (Expression<Number>) exprs[1];
        exprFile = (Expression<String>) exprs[2];

        isInfinite = (parseResult.mark & 1) != 0;
        return true;
    }

    @Nullable
    @Override
    protected GifSequenceWriter[] get(@NotNull Event e) {
        final BufferedImage image = EasyElement.parseSingle(exprImage, e, null);
        final String raw = EasyElement.parseSingle(exprFile, e, null);
        final Number delay = EasyElement.parseSingle(exprDelay, e, null);
        if (EasyElement.anyNull(image, raw, delay))
            return new GifSequenceWriter[0];

        final File file = new File(raw);
        if (file.getParentFile() != null)
            file.getParentFile().mkdirs();
        if (file.exists()) {
            File configFile = new File(SkImage.getInstance().getDataFolder(), "config.yml");
            FileConfiguration configConfig = YamlConfiguration.loadConfiguration(configFile);
            final boolean replaceImage = configConfig.getBoolean("ImageReplacement");
            if (!replaceImage) {
                Skript.error("The file called " + raw + " already exist! Disable this non-replacement in the node `ImageReplacement` from `plugins/SkImage/config.yml`");
                return new GifSequenceWriter[0];
            }
            file.delete();
        }

        final FileImageOutputStream stream;
        try {
            stream = new FileImageOutputStream(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            SkImage.getInstance().getLogger().severe("Unable to write the output gif in the specified file.");
            return new GifSequenceWriter[0];
        }

        try {
            return new GifSequenceWriter[] {new GifSequenceWriter(
                    stream, image.getType(), delay.intValue(), isInfinite
            )};
        } catch (IOException ex) {
            ex.printStackTrace();
            SkImage.getInstance().getLogger().severe("Unable to write the output gif in the specified file.");
            return new GifSequenceWriter[0];
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends GifSequenceWriter> getReturnType() {
        return GifSequenceWriter.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "new " + (isInfinite ? "infinite " : ":") + "gif manager with delay" + exprDelay.toString(e, debug);
    }
}
