package cn.citycraft.SimpleEssential.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.Plugin;

public class Language extends ConfigLoader {
	private static String CONFIG_NAME = "language.yml";
	private static FileConfig instance;
	private static File file;

	public Language(Plugin p) {
		super(p, CONFIG_NAME);
		file = new File(p.getDataFolder(), CONFIG_NAME);
		instance = super.getInstance();
	}

	public Language(Plugin p, String ver) {
		super(p, CONFIG_NAME, ver);
		instance = super.getInstance();
	}

	public static void load(Plugin p) {
		new Language(p);
	}

	public static void load(Plugin p, String ver) {
		new Language(p, ver);
	}

	public static FileConfig getInstance() {
		return instance;
	}

	public static String getMessage(String path, Object... paramVarArgs) {
		String message = instance.getString(path);
		try {
			if (message != null)
				message = String.format(message.replaceAll("&", "§"), paramVarArgs);
		} catch (Exception e) {
		}
		return message;
	}

	public static String getMessage(String path) {
		String message = instance.getString(path);
		if (message != null)
			message = message.replaceAll("&", "§");
		return message;
	}

	public static String[] getStringArray(String path) {
		return instance.getStringList(path).toArray(new String[0]);
	}

	public static void save() {
		try {
			instance.save(file);
		} catch (IOException e) {
			saveError(file);
			e.printStackTrace();
		}
	}
}
