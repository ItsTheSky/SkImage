package info.itsthesky.SkImage.skript.tools;

import ch.njol.skript.util.chat.ChatMessages;
import ch.njol.skript.util.chat.MessageComponent;
import ch.njol.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Store info about a specific text like gradient colors, centering or not, etc...
 */
public class TextInfo {

	private final String text;
	private @NotNull Font font;
	private @NotNull Color uniqueColor;

	private boolean centerVertically;
	private boolean centerHorizontally;

	public TextInfo(String text, @NotNull Font font, @NotNull Color uniqueColor, boolean centerVertically, boolean centerHorizontally) {
		this.text = text;
		this.font = font;
		this.uniqueColor = uniqueColor;
		this.centerVertically = centerVertically;
		this.centerHorizontally = centerHorizontally;
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

		final int textWidth = Utils.getFontSize(getFont(), ChatMessages.stripStyles(getText())).getFirst();
		final int textHeight = Utils.getFontSize(getFont(), ChatMessages.stripStyles(getText())).getSecond();

		if (isCenterVertically())
			y = (image.getHeight() / 2 - textHeight / 2) + y;
		if (isCenterHorizontally())
			x = (image.getWidth() / 2 - textWidth / 2) + x;

		drawText(graphics, x, y);
		graphics.dispose();
	}

	public LinkedList<Object> parse(String input) {
		final List<MessageComponent> components = ChatMessages.parse(getText());
		final LinkedList<Object> arguments = new LinkedList<>();
		for (MessageComponent component : components) {
			if (component.text.isEmpty())
				continue;
			arguments.add(component.color == null ? getUniqueColor() : component.color.getColor());
			arguments.add(component.text.replace("§x", ""));
		}
		return arguments;
	}

	public void drawText(Graphics2D graphics, int x, int y) {
		Utils.drawString(graphics, x, y, parse(getText()).toArray());
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

	@Override
	public String toString() {
		return text + " with font " + font.getFontName();
	}
}
