/**
 * 
 */
package cn.citycraft.SimpleEssential;

import org.bukkit.plugin.java.JavaPlugin;

import cn.citycraft.SimpleEssential.config.Config;
import cn.citycraft.SimpleEssential.handler.SimpleEssentialCommandHandler;
import cn.citycraft.SimpleEssential.handler.teleport.TeleportControl;
import cn.citycraft.SimpleEssential.listen.PlayerLocationListen;

/**
 * @author 蒋天蓓
 *         2015年8月11日下午3:29:32
 *         TODO
 */
public class SimpleEssential extends JavaPlugin {
	public TeleportControl tpcontrol;

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
		new SimpleEssentialCommandHandler();
		tpcontrol = new TeleportControl(this);
		getServer().getPluginManager().registerEvents(new PlayerLocationListen(this), this);
	}

}
