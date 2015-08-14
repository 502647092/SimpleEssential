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

public class VersionChecker implements Listener {
	Plugin plugin;
	public String checkurl = "https://coding.net/u/502647092/p/%s/git/raw/%s/src/plugin.yml";
	public String branch = "master";

	public VersionChecker(Plugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.VersionCheck(null);
	}

	// https://coding.net/u/502647092/p/SimpleEssential/git/raw/master/src/plugin.yml
	public VersionChecker(Plugin plugin, String branch) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.checkurl = branch;
		this.VersionCheck(null);
	}

	public String getCheckUrl(String pluginName, String branch) {
		return String.format(checkurl, pluginName, branch);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().isOp()) {
			this.VersionCheck(e.getPlayer());
		}
	}

	public void VersionCheck(final Player player) {
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
