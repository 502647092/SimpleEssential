/**
 * 
 */
package cn.citycraft.SimpleEssential.utils;

import org.bukkit.Effect;
import org.bukkit.Location;

/**
 * @author 蒋天蓓
 *         2015年8月14日下午2:07:02
 *         TODO
 */
public class EffectUtil {
	/**
	 * 粒子发生器
	 *
	 * @param loc
	 *            - 粒子产生的地点
	 * @param range
	 *            - 粒子的数量
	 */
	public static void run(Location loc, long range) {
		try {
			int i;
			if (range < 2) {
				range = 2;
			}
			for (i = 0; i < range; i++) {
				loc.getWorld().playEffect(loc, Effect.LAVA_POP, 10, 100);
				loc.getWorld().playEffect(loc, Effect.PORTAL, 10, 100);
			}
		} catch (Exception e) {
		}
	}
}
