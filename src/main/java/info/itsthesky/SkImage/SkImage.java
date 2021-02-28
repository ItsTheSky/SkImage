package info.itsthesky.SkImage;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import info.itsthesky.SkImage.skript.Test;
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
		Bukkit.getConsoleSender().sendMessage("§3==========================================");
		Bukkit.getConsoleSender().sendMessage("§6SkImage version §e" + getDescription().getVersion() + "§6 is loading ...");

		saveResourceAs("config.yml", "config.yml");

		new Test();

		pluginManager = Bukkit.getPluginManager();
		if ((pluginManager.getPlugin("Skript") != null) && Skript.isAcceptRegistrations()) {
			SkriptAddon addon = Skript.registerAddon(this);
			new Types().skImageRegisterTypes();
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
		Bukkit.getConsoleSender().sendMessage("§aSkImage doesn't seems to get error while loading!");
		Bukkit.getConsoleSender().sendMessage("§3==========================================");
	}

	public void saveResourceAs(String inPath, String outPath) {
		if (inPath == null || inPath.isEmpty()) {
			throw new IllegalArgumentException("The input path cannot be null or empty");
		}
		InputStream in = getResource(inPath);
		if (in == null) {
			throw new IllegalArgumentException("The file "+inPath+" cannot be found in plugin's resources");
		}
		if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
			getLogger().severe("Failed to make the main directory !");
		}
		File outFile = new File(getDataFolder(), outPath);
		try {
			if (!outFile.exists()) {
				getLogger().info("The file "+outFile.getName()+" cannot be found, create one for you ...");
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int n;

				while ((n = in.read(buf)) >= 0) {
					out.write(buf, 0, n);
				}

				out.close();
				in.close();

				if (!outFile.exists()) {
					getLogger().severe("Unable to copy file !");
				} else {
					getLogger().info("The file "+outFile.getName()+" has been created!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
