/**
 *
 */
package cn.citycraft.SimpleEssential.teleport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.config.Config;

/**
 * @author 蒋天蓓 2015年8月12日下午2:26:10 传送控制类
 */
public class TeleportControl {
	/**
	 * 粒子发生器
	 *
	 * @param loc
	 *            - 粒子产生的地点
	 * @param range
	 *            - 粒子的数量
	 */
	static void pEffect(Location loc, long range) {
		int i;
		if (range < 2) {
			range = 2;
		}
		for (i = 0; i < range; i++) {
			loc.getWorld().playEffect(loc, Effect.LAVA_POP, 10, 100);
			loc.getWorld().playEffect(loc, Effect.PORTAL, 10, 100);
		}
	}

	protected HashMap<Player, TeleportInfo> teleportList = new HashMap<Player, TeleportInfo>();
	protected HashMap<Player, Location> lastlocList = new HashMap<Player, Location>();
	private SimpleEssential plugin;

	private int TpDelay = Config.getInstance().getInt("Teleport.delay", 3);

	public TeleportControl(SimpleEssential plugin) {
		this.plugin = plugin;
	}

	/**
	 * 玩家接受传送请求
	 *
	 * @param player
	 *            - 执行的玩家
	 */
	public void accept(Player player) {
		TeleportInfo ti = teleportList.remove(player);
		if (ti != null) {
			Player target = ti.getTarget();
			Location loc = null;
			if (!target.isOnline()) {
				player.sendMessage("§c目标玩家已离线 传送失败!");
				return;
			}
			target.sendMessage("§a玩家: " + player.getDisplayName() + "§a受了您的请求!");
			if (ti.getTptype() == TeleportType.TPA) {
				target = ti.getTarget();
				loc = player.getLocation();
			} else {
				target = player;
				loc = ti.getTarget().getLocation();
			}
			magicTeleport(target, loc, TpDelay);
			return;
		}
		player.sendMessage("§c未找到需要处理的队列!");
	}

	/**
	 * 添加传送请求到传送列表
	 *
	 * @param player
	 *            - 发出请求的玩家
	 * @param target
	 *            - 目标玩家
	 * @param tptype
	 *            - 传送类型
	 */
	public void addtp(Player player, Player target, TeleportType tptype) {
		teleportList.put(target, new TeleportInfo(player, tptype));
	}

	/**
	 * 玩家返回上次传送的地点
	 *
	 * @param player
	 *            - 被传送的玩家
	 */
	public void back(Player player) {
		Location loc = lastlocList.get(player);
		if (loc != null) {
			magicTeleport(player, loc, 3);
		} else {
			player.sendMessage("§c未找到可以Back的地点!");
		}
	}

	/**
	 * 玩家拒绝传送请求
	 *
	 * @param player
	 *            - 执行的玩家
	 */
	public void deny(Player player) {
		TeleportInfo ti = teleportList.remove(player);
		if (ti != null) {
			player.sendMessage("§c已拒绝玩家的传送请求!");
			Player target = ti.getTarget();
			if (target.isOnline()) {
				target.sendMessage("§c玩家: " + player.getDisplayName() + "§c拒绝了您的请求!");
			}
			return;
		}
		player.sendMessage("§c未找到需要处理的队列!");
	}

	/**
	 * 魔法传送
	 *
	 * @param player
	 *            - 被传送的玩家
	 * @param loc
	 *            - 传送的地点
	 */
	public void magicTeleport(final Player player, final Location loc) {
		magicTeleport(player, loc, TpDelay);
	}

	/**
	 * 魔法传送
	 *
	 * @param player
	 *            - 被传送的玩家
	 * @param loc
	 *            - 传送的地点
	 * @param delay
	 *            - 传送延时
	 */
	public void magicTeleport(final Player player, final Location loc, final int delay) {
		int petime = delay * 20 + 10;
		setLastloc(player, player.getLocation());
		player.sendMessage("§a传送开始 " + delay + "秒 后到达目的地 §d世界: " + loc.getWorld().getName()
				+ " §3X: " + loc.getBlockX() + " Z: " + loc.getBlockZ() + "!");
		List<PotionEffect> pe = new ArrayList<PotionEffect>();
		pe.add(new PotionEffect(PotionEffectType.SLOW, petime, 255));
		pe.add(new PotionEffect(PotionEffectType.CONFUSION, petime, 255));
		player.addPotionEffects(pe);
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			long timeoutmark = System.currentTimeMillis() + delay * 1000;
			long lrng = 0;

			@Override
			public void run() {
				while (System.currentTimeMillis() < timeoutmark) {
					if (player.isOnline()) {
						pEffect(player.getLocation(), lrng);
					}
					lrng++;
					try {
						Thread.sleep(128);
					} catch (Exception e) {
					}
				}
			}
		});
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (player.isOnline()) {
					player.teleport(loc);
				}
			}
		}, delay * 20);
	}

	/**
	 * 设置最后的地址数据
	 *
	 * @param player
	 *            - 玩家
	 * @param loc
	 *            - 地点
	 */
	public void setLastloc(Player player, Location loc) {
		lastlocList.put(player, loc);
	}
}
