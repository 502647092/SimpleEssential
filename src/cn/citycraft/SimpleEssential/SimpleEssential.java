/**
 * 
 */
package cn.citycraft.SimpleEssential;

import org.bukkit.plugin.java.JavaPlugin;

import cn.citycraft.SimpleEssential.config.Config;

/**
 * @author 蒋天蓓
 *         2015年8月11日下午3:29:32
 *         TODO
 */
public class SimpleEssential extends JavaPlugin {

	@Override
	public void onLoad() {
		Config.load(this, "1.0");
	}

	@Override
	public void onDisable() {
		this.getLogger().info("");
	}

	@Override
	public void onEnable() {
		this.getLogger().info("");
	}

}
