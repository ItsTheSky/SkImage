package info.itsthesky.SkImage;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import org.bukkit.event.Event;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

	public BufferedImage copiedImage(BufferedImage image) {
		return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
	}

	public void setSkriptVariable(Variable variable, Object value, Event event) {
		String name = variable.getName().toString(event);
		Variables.setVariable(name, value, event, variable.isLocal());
	}

}
