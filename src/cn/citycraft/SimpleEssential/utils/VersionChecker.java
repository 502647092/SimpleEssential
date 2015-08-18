package cn.citycraft.SimpleEssential.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Charsets;

/**
 * 自动更新类
 * 
 * @author 蒋天蓓
 *         2015年8月14日下午4:01:15
 */
public class VersionChecker implements Listener {
	Plugin plugin;
	public String checkurl = "https://coding.net/u/502647092/p/%s/git/raw/%s/src/plugin.yml";
	public String branch = "master";

	/**
	 * @param plugin
	 *            - 插件
	 */
	public VersionChecker(Plugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.versioncheck(null);
	}

	/**
	 * @param plugin
	 *            - 插件
	 * @param branch
	 *            - 分支名称
	 */
	public VersionChecker(Plugin plugin, String branch) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.checkurl = branch;
		this.versioncheck(null);
	}

	/**
	 * 获取插件更新链接
	 * 
	 * @param pluginName
	 *            - 插件名称
	 * @param branch
	 *            - 插件分支
	 * @return 更新链接
	 */
	public String getCheckUrl(String pluginName, String branch) {
		return String.format(checkurl, pluginName, branch);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().isOp()) {
			this.versioncheck(e.getPlayer());
		}
	}

	/**
	 * 开始更新
	 * 
	 * @param player
	 *            - 获取更新的玩家(null则默认为控制台)
	 */
	public void versioncheck(final Player player) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				String readURL = getCheckUrl(plugin.getName(), branch);
				FileConfiguration config;
				String currentVersion = plugin.getDescription().getVersion();
				try {
					URL url = new URL(readURL);
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), Charsets.UTF_8));
					config = YamlConfiguration.loadConfiguration(br);
					String newVersion = config.getString("version");
					br.close();
					if (!newVersion.equals(currentVersion)) {
						String[] msg = new String[] {
								ChatColor.GREEN + plugin.getName() + " 插件最新版本 v" + newVersion,
								ChatColor.RED + "服务器运行版本: v" + currentVersion,
								ChatColor.GOLD + "插件更新网站: " + ChatColor.BLUE + plugin.getDescription().getWebsite()
						};
						if (player != null) {
							player.sendMessage(msg);
						} else {
							plugin.getServer().getConsoleSender().sendMessage(msg);
						}
					}
				} catch (IOException e) {
					plugin.getLogger().warning("版本更新检查失败!");
				}
			}
		});
	}

}
