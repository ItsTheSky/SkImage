package info.itsthesky.SkImage.skript.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.SkImage.skript.tools.GifSequenceWriter;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Name("Close Gif Manager")
@Description({"Once you've added all your images to your gif manager, you must close it in order to save the actual gif file.",
"If not, it could cause memory leaks and you'll have to restart your server."})
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
public class EffCloseGifManager extends Effect {

    static {
        Skript.registerEffect(
                EffCloseGifManager.class,
                "close [the] gif [manager] %gifmanager%"
        );
    }

    private Expression<GifSequenceWriter> exprGif;

    @Override
    protected void execute(@NotNull Event e) {
        final GifSequenceWriter writer = EasyElement.parseSingle(exprGif, e, null);
        if (writer == null)
            return;
        try {
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "close the gif manager " + exprGif.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        exprGif = (Expression<GifSequenceWriter>) exprs[0];
        return true;
    }
}
