package info.itsthesky.SkImage;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkImage extends JavaPlugin {

	private static PluginManager pluginManager;

	public static PluginManager getPluginManager() {
		return pluginManager;
	}

	@Override
	public void onEnable() {

		getLogger().info("SkImage version " + getDescription().getVersion() + " is loading ...");

		pluginManager = Bukkit.getPluginManager();
		if ((pluginManager.getPlugin("Skript") != null) && Skript.isAcceptRegistrations()) {
			SkriptAddon addon = Skript.registerAddon(this);
			try {
				addon.loadClasses("info.itsthesky.SkImage.skript");
			} catch (IOException e) {
				Skript.error("Wait, this is anormal. Please report this error on GitHub.");
				e.printStackTrace();
			}
		} else {
			Skript.error("Skript isn't installed or doesn't accept registrations.");
			pluginManager.disablePlugin(this);
		}
	}
}
