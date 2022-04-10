package info.itsthesky.SkImage.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import info.itsthesky.SkImage.skript.tools.GifSequenceWriter;
import info.itsthesky.SkImage.skript.tools.TextInfo;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Types {
	public void skImageRegisterTypes() {
		Classes.registerClass(new ClassInfo<>(BufferedImage.class, "image")
				.user("image")
				.name("Image")
				.description("Represents an Image")
				.since("1.0")
				.parser(new Parser<BufferedImage>() {

					@Override
					public @NotNull String toString(BufferedImage o, int flags) {
						return o + " image";
					}

					@Override
					public @NotNull String toVariableNameString(BufferedImage o) {
						return "";
					}

					@Override
					public @NotNull String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public BufferedImage parse(@NotNull String s, @NotNull ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(TextInfo.class, "textinfo")
				.user("textinfo")
				.name("Text Information")
				.description("Represents a text that contains further information about its style, such as color, font and center values.")
				.since("1.5"));
		Classes.registerClass(new ClassInfo<>(GifSequenceWriter.class, "gifmanager")
				.user("gifmanager")
				.name("Gif Manager")
				.description("Represents a gif manager that hold a delay and multiples images to be written into the gif.")
				.since("1.9"));
		Classes.registerClass(new ClassInfo<>(Font.class, "font")
				.user("font")
				.name("font")
				.description("Represents an Image Font")
				.since("1.0")
				.parser(new Parser<Font>() {

					@Override
					public @NotNull String toString(Font o, int flags) {
						return toVariableNameString(o);
					}

					@Override
					public @NotNull String toVariableNameString(Font o) {
						return "font with name " + o.getFontName() + " and size " + o.getSize() +  + o.getStyle();
					}

					@Override
					public @NotNull String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Font parse(@NotNull String s, @NotNull ParseContext context) {
						return null;
					}
				})
		);
	}
}