package info.itsthesky.SkImage.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import info.itsthesky.SkImage.skript.tools.GifSequenceWriter;
import info.itsthesky.SkImage.skript.tools.skript.EasyElement;
import info.itsthesky.SkImage.skript.tools.skript.MultiplePropertyExpression;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Name("Gif Manager's Images")
@Description({"Represent images appended to a specific gif manager.",
"You can only get and add images to this property."})
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
public class PropGifImages extends MultiplePropertyExpression<GifSequenceWriter, BufferedImage> {

    static {
        register(
                PropGifImages.class,
                BufferedImage.class,
                "[gif] images",
                "gifmanager"
        );
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD)
            return new Class[] {BufferedImage.class};
        return new Class[0];
    }

    @Override
    public void change(@NotNull Event e, @Nullable Object[] delta, Changer.@NotNull ChangeMode mode) {
        if (!EasyElement.isValid(delta))
            return;
        final GifSequenceWriter writer = EasyElement.parseSingle(getExpr(), e, null);
        final BufferedImage image = (BufferedImage) delta[0];
        if (writer == null)
            return;
        try {
            writer.writeToSequence(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "images";
    }

    @Nullable
    @Override
    public BufferedImage[] convert(GifSequenceWriter sequenceWriter) {
        return sequenceWriter.getLoadedImages().toArray(new BufferedImage[0]);
    }

    @Override
    public @NotNull Class<? extends BufferedImage> getReturnType() {
        return BufferedImage.class;
    }
}
