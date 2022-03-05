package info.itsthesky.SkImage;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import info.itsthesky.SkImage.skript.Types;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class SkImage extends JavaPlugin {

	public static SkImage INSTANCE;
	private static PluginManager pluginManager;

	public static PluginManager getPluginManager() {
		return pluginManager;
	}

	public static SkImage getInstance() {
		return INSTANCE;
	}

	@Override
	public void onEnable() {

		INSTANCE = this;
		getLogger().info("SkImage v" + getDescription().getVersion() + " is loading ...");

		getLogger().info("Saving default configuration ...");
		saveResource("config.yml", true);

		getLogger().info("Checking for Skript ...");
		pluginManager = Bukkit.getPluginManager();
		if ((pluginManager.getPlugin("Skript") != null) && Skript.isAcceptRegistrations()) {
			getLogger().info("Skript found! registering syntaxes ...");
			SkriptAddon addon = Skript.registerAddon(this);
			new Types().skImageRegisterTypes();
			try {
				addon.loadClasses("info.itsthesky.SkImage.skript");
			} catch (IOException e) {
				getLogger().severe("Wait, this is anormal. Please report this error on GitHub.");
				e.printStackTrace();
			}
		} else {
			getLogger().severe("Skript is not found (or not enabled), cannot load SkImage.");
			pluginManager.disablePlugin(this);
			return;
		}
		getLogger().info("SkImage has been enabled correctly!");
	}
}
