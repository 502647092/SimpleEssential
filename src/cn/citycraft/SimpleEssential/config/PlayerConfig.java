package cn.citycraft.SimpleEssential.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerConfig extends ConfigLoader {
	private static String CONFIG_FOLDER = "userdate";
	private static FileConfig instance;
	private static File file;

	public PlayerConfig(Plugin p, String player) {
		super(p, CONFIG_FOLDER + File.separator + player + ".yml", false);
		file = new File(p.getDataFolder(), CONFIG_FOLDER + File.separator
				+ player + ".yml");
		instance = super.getInstance();
	}

	public static FileConfig getInstance(Plugin p, Player player) {
		new PlayerConfig(p, player.getName());
		return instance;
	}

	public static FileConfig getInstance(Plugin p, String player) {
		new PlayerConfig(p, player);
		return instance;
	}

	public static String getMessage(String path) {
		String message = instance.getString(path).replaceAll("&", "§");
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
