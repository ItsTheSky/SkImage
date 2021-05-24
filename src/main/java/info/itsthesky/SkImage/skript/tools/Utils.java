package info.itsthesky.SkImage.skript.tools;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.util.Arrays;

public class Utils {

	public static BufferedImage copiedImage(BufferedImage image) {
		return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
	}

	public static void setSkriptVariable(Variable variable, Object value, Event event) {
		String name = variable.getName().toString(event);
		Variables.setVariable(name, value, event, variable.isLocal());
	}

	public static Integer round(Object number) {
		String t = number.toString().split("\\.")[0];
		return Integer.valueOf(t);
	}

	public static BufferedImage resizedImage(final BufferedImage image, final int sizeX, final int sizeY, Integer algo) {
		if (algo == null) algo = 1;
		final Image temp = image.getScaledInstance(sizeX, sizeY, algo);
		final BufferedImage resized = new BufferedImage(sizeX, sizeY, getDefaultType());
		final Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	public static BufferedImage blur(BufferedImage image, int force) {
		int size = force * 2 + 1;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];
		Arrays.fill(data, weight);
		Kernel kernel = new Kernel(size, size, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
		BufferedImage finalimage = op.filter(image, null);
		return finalimage;
	}

	public static BufferedImage blackAndWhite(BufferedImage image) {
		BufferedImage bawImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = bawImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return bawImage;
	}

	public static int getDefaultType() {
		return 2;
	}

	/* Thanks to Anarchick <3 */
	public static double colorDistance(Color c1, Color c2) {
		int red1 = c1.getRed();
		int red2 = c2.getRed();
		int rmean = (red1 + red2) >> 1;
		int r = red1 - red2;
		int g = c1.getGreen() - c2.getGreen();
		int b = c1.getBlue() - c2.getBlue();
		return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));
	}

	public static BufferedImage lighterImage(BufferedImage image, float force) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color color = new Color(image.getRGB(x, y));
				Color brighter = color.brighter();
				for (int i = 0; i <= force; i++) {
					brighter = brighter.brighter();
				}
				image.setRGB(x, y, brighter.getRGB());
			}
		}
		return image;
	}

	public static BufferedImage darkerImage(BufferedImage image, float force) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color color = new Color(image.getRGB(x, y));
				Color darker = color.darker();
				for (int i = 0; i <= force; i++) {
					darker = darker.darker();
				}
				image.setRGB(x, y, darker.getRGB());
			}
		}
		return image;
	}

	public static BufferedImage invertedImage(BufferedImage image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int rgba = image.getRGB(x, y);
				Color col = new Color(rgba, true);
				col = new Color(255 - col.getRed(),
						255 - col.getGreen(),
						255 - col.getBlue());
				image.setRGB(x, y, col.getRGB()); } }
		return image;
	}

	public static BufferedImage tinted(BufferedImage img, Color color) {
		return tinted(img, color.getRed(), color.getGreen(), color.getBlue());
	}
	public static BufferedImage tinted(BufferedImage img, int red, int green, int blue) {
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {

				Color rgb = new Color(img.getRGB(x, y));
				Color color = new Color(
						Math.min(rgb.getRed() + red, 255),
						Math.min(rgb.getGreen() + green, 255),
						Math.min(rgb.getBlue() + blue, 255)
				);

				// do something with the color :) (change the hue, saturation and/or brightness)
				// float[] hsb = new float[3];
				// Color.RGBtoHSB(color.getRed(), old.getGreen(), old.getBlue(), hsb);

				// or just call brighter to just tint it
				//Color brighter = color.brighter();

				img.setRGB(x, y, color.getRGB());
			}
		}
		return img;
	}
}
