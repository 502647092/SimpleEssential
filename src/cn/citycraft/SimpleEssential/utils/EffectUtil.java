/**
 * 
 */
package cn.citycraft.SimpleEssential.utils;

import org.bukkit.Effect;
import org.bukkit.Location;

/**
 * 粒子发生器
 * 
 * @author 蒋天蓓
 *         2015年8月14日下午2:07:02
 * 
 */
public class EffectUtil {

	/**
	 * 粒子发生器
	 * 
	 * @param loc
	 *            - 粒子产生地点
	 * @param range
	 *            - 粒子产生数量
	 * @param effects
	 *            - 粒子类型
	 */
	public static void run(Location loc, long range, Effect... effects) {
		try {
			int i;
			if (range < 2) {
				range = 2;
			}
			for (i = 0; i < range; i++) {
				for (Effect effect : effects) {
					loc.getWorld().playEffect(loc, effect, 10, 100);
				}
			}
		} catch (Exception e) {
		}
	}
}
