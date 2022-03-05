package info.itsthesky.SkImage.skript.tools;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Store info about a specific text like gradient colors, centering or not, etc...
 */
public class TextInfo {

	private final String text;
	private @NotNull Font font;
	private @NotNull Color uniqueColor;

	private boolean centerVertically;
	private boolean centerHorizontally;

	private boolean hasGradient;
	private Color startColor;
	private Color endColor;

	public TextInfo(String text, @NotNull Font font, @NotNull Color uniqueColor, boolean centerVertically, boolean centerHorizontally, boolean hasGradient, Color startColor, Color endColor) {
		this.text = text;
		this.font = font;
		this.uniqueColor = uniqueColor;
		this.centerVertically = centerVertically;
		this.centerHorizontally = centerHorizontally;
		this.hasGradient = hasGradient;
		this.startColor = startColor;
		this.endColor = endColor;
	}

	public void apply(BufferedImage image, boolean antiAliases, int x, int y) {
		Graphics2D graphics = image.createGraphics();

		if (antiAliases) {
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.setRenderingHints(rh);
		}
		graphics.setColor(getUniqueColor());
		graphics.setFont(getFont());

		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
		final int textWidth = (int)(getFont().getStringBounds(getText(), frc).getWidth());
		final int textHeight = (int)(getFont().getStringBounds(getText(), frc).getHeight());

		if (isCenterVertically())
			y = image.getHeight() / 2 - textHeight / 2;
		if (isCenterHorizontally())
			x = image.getWidth() / 2 - textWidth / 2;

		graphics.drawString(getText(), x, y);
		graphics.dispose();
	}

	public String getText() {
		return text;
	}

	public boolean isCenterVertically() {
		return centerVertically;
	}

	public void setCenterVertically(boolean centerVertically) {
		this.centerVertically = centerVertically;
	}

	public boolean isCenterHorizontally() {
		return centerHorizontally;
	}

	public void setCenterHorizontally(boolean centerHorizontally) {
		this.centerHorizontally = centerHorizontally;
	}

	public @NotNull Font getFont() {
		return font;
	}

	public void setFont(@NotNull Font font) {
		this.font = font;
	}

	public @NotNull Color getUniqueColor() {
		return uniqueColor;
	}

	public void setUniqueColor(@NotNull Color uniqueColor) {
		this.uniqueColor = uniqueColor;
	}

	public boolean isHasGradient() {
		return hasGradient;
	}

	public void setHasGradient(boolean hasGradient) {
		this.hasGradient = hasGradient;
	}

	public Color getStartColor() {
		return startColor;
	}

	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

	public Color getEndColor() {
		return endColor;
	}

	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}
}
