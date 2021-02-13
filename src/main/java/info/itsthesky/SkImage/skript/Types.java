package info.itsthesky.SkImage.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

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
					public String toString(BufferedImage o, int flags) {
						return o + " image";
					}

					@Override
					public String toVariableNameString(BufferedImage o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public BufferedImage parse(String s, ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Color.class, "imagecolor")
				.user("imagecolor")
				.name("imagecolor")
				.description("Represents an Image Color")
				.since("1.0")
				.parser(new Parser<Color>() {

					@Override
					public String toString(Color o, int flags) {
						return "color with red " + o.getRed() + ", green " + o.getGreen() + " and blue " + o.getBlue() + " ("+o.getRGB()+")";
					}

					@Override
					public String toVariableNameString(Color o) {
						return "color with red " + o.getRed() + ", green " + o.getGreen() + " and blue " + o.getBlue() + " ("+o.getRGB()+")";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Color parse(String s, ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Font.class, "font")
				.user("font")
				.name("font")
				.description("Represents an Image Font")
				.since("1.0")
				.parser(new Parser<Font>() {

					@Override
					public String toString(Font o, int flags) {
						return "font with name " + o.getFontName() + " and size " + o.getSize() +  + o.getStyle();
					}

					@Override
					public String toVariableNameString(Font o) {
						return "font with name " + o.getFontName() + " and size " + o.getSize() +  + o.getStyle();
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Font parse(String s, ParseContext context) {
						return null;
					}
				})
		);
	}
}