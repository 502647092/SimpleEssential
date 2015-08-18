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
import cn.citycraft.SimpleEssential.config.Language;
import cn.citycraft.SimpleEssential.utils.EffectUtil;

/**
 * @author 蒋天蓓 2015年8月12日下午2:26:10 传送控制类
 */
public class TeleportControl {
	protected HashMap<Player, TeleportInfo> teleportList = new HashMap<Player, TeleportInfo>();
	protected HashMap<Player, Location> lastlocList = new HashMap<Player, Location>();
	private SimpleEssential plugin;

	private int TpDelay = 0;
	private String TpControlName = "";

	public TeleportControl(SimpleEssential plugin, String tpcontrolname, int tpdelay) {
		this.plugin = plugin;
		this.TpDelay = tpdelay;
		this.TpControlName = tpcontrolname;
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
				player.sendMessage(TpControlName + Language.getMessage("Teleport.offline"));
				return;
			}
			if (ti.getTptype() == TeleportType.TPA) {
				target = ti.getTarget();
				loc = player.getLocation();
			} else {
				target = player;
				loc = ti.getTarget().getLocation();
			}
			player.sendMessage(TpControlName + Language.getMessage("Teleport.accept"));
			target.sendMessage(TpControlName + Language.getMessage("Teleport.acceptfrom"));
			magicTeleport(target, loc, TpDelay);
			return;
		}
		player.sendMessage(TpControlName + Language.getMessage("Teleport.none"));
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
			player.sendMessage(TpControlName + Language.getMessage("Teleport.nobackloc"));
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
			Player target = ti.getTarget();
			if (target.isOnline()) {
				player.sendMessage(TpControlName + Language.getMessage("Teleport.deny"));
				target.sendMessage(TpControlName + Language.getMessage("Teleport.denyfrom"));
			}
			return;
		}
		player.sendMessage(TpControlName + Language.getMessage("Teleport.none"));
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
		player.sendMessage(TpControlName + Language.getMessage("Teleport.tp", delay, loc.getWorld().getName(), loc.getBlockX(), loc.getBlockZ()));
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
						EffectUtil.run(player.getLocation(), lrng, Effect.MOBSPAWNER_FLAMES, Effect.PORTAL);
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
