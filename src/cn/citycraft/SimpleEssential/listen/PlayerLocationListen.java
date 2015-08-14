/**
 * 
 */
package cn.citycraft.SimpleEssential.listen;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import cn.citycraft.SimpleEssential.SimpleEssential;

/**
 * @author 蒋天蓓
 *         2015年8月12日下午8:19:33
 *         TODO
 */
public class PlayerLocationListen implements Listener {
	SimpleEssential plugin;

	public PlayerLocationListen(SimpleEssential main) {
		this.plugin = main;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		Location loc = player.getLocation();
		plugin.tpcontrol.setLastloc(player, loc);
	}
}
