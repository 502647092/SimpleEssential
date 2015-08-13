/**
 * 
 */
package cn.citycraft.SimpleEssential.teleport;

import org.bukkit.entity.Player;

/**
 * @author 蒋天蓓
 *         2015年8月12日下午2:56:25
 *         TODO
 */
public class TeleportInfo {
	protected TeleportType tptype;
	protected Player target;

	public TeleportInfo(Player target, TeleportType tptype) {
		this.target = target;
		this.tptype = tptype;
	}

	public TeleportType getTptype() {
		return tptype;
	}

	public Player getTarget() {
		return target;
	}
}
