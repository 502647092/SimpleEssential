package cn.citycraft.SimpleEssential.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.Plugin;

public class ConfigLoader extends FileConfig {
	protected static FileConfig config;
	protected static boolean tip = true;
	protected static Plugin plugin;

	public ConfigLoader(Plugin p, File file) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, file, null, true);
	}

	public ConfigLoader(Plugin p, File file, boolean res) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, file, null, res);
	}

	public ConfigLoader(Plugin p, File file, String ver) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, file, ver, true);
	}

	public ConfigLoader(Plugin p, File file, String ver, boolean res) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, file, ver, res);
	}

	public ConfigLoader(Plugin p, String filename) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, new File(p.getDataFolder(), filename), null, true);
	}

	public ConfigLoader(Plugin p, String filename, boolean res) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, new File(p.getDataFolder(), filename), null, res);
	}

	public ConfigLoader(Plugin p, String filename, String ver) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, new File(p.getDataFolder(), filename), ver, true);
	}

	public ConfigLoader(Plugin p, String filename, String ver, boolean res) {
		ConfigLoader.plugin = p;
		config = loadConfig(p, new File(p.getDataFolder(), filename), ver, true);
	}

	public static FileConfig getInstance() {
		return config;
	}

	public FileConfig loadConfig(Plugin p, File file, String ver, boolean res) {
		tip = res;
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
			p.getLogger().info("创建新的文件夹" + file.getParentFile().getAbsolutePath() + "...");
		}
		if (!file.exists()) {
			fileCreate(p, file, res);
		} else {
			if (ver != null) {
				FileConfig configcheck = init(file);
				String version = configcheck.getString("version");
				if (version == null || !version.equals(ver)) {
					p.getLogger().warning("配置文件: " + file.getName() + " 版本过低 正在升级...");
					try {
						configcheck.save(new File(file.getParent(), file.getName() + ".backup"));
						p.getLogger()
								.warning(
										"配置文件: " + file.getName() + " 已备份为 " + file.getName()
												+ ".backup !");
					} catch (IOException e) {
						p.getLogger().warning("配置文件: " + file.getName() + "备份失败!");
					}
					p.saveResource(file.getName(), true);
					p.getLogger().info("配置文件: " + file.getName() + "升级成功!");
				}
			}
		}
		if (tip)
			p.getLogger().info("载入配置文件: " + file.getName() + (ver != null ? " 版本: " + ver : ""));
		return init(file);
	}

	private void fileCreate(Plugin p, File file, boolean res) {
		if (res) {
			p.saveResource(file.getName(), false);
		} else {
			try {
				p.getLogger().info("创建新的配置文件" + file.getAbsolutePath() + "...");
				file.createNewFile();
			} catch (IOException e) {
				p.getLogger().info("配置文件" + file.getName() + "创建失败...");
				e.printStackTrace();
			}
		}
	}

	public static void saveError(File file) {
		plugin.getLogger().info("配置文件" + file.getName() + "保存错误...");
	}

}
