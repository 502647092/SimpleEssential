/**
 *
 */
package cn.citycraft.SimpleEssential.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.teleport.TeleportType;

/**
 * @author 蒋天蓓 2015年8月12日下午2:04:05 TODO
 */
public class CommandTphere extends SimpleEssentialCommand {
	SimpleEssential plugin;

	public CommandTphere(SimpleEssential main) {
		super("tphere", "tph");
		this.plugin = main;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage("§c玩家 " + args[0] + " 不存在或不在线!");
			return;
		}
		plugin.tpcontrol.addtp((Player) sender, Bukkit.getPlayer(args[0]), TeleportType.TPH);
		sender.sendMessage("§a已经向玩家 " + target.getDisplayName() + " §a发送传送请求!");
		target.sendMessage(new String[] {
				"§b玩家: " + sender.getName() + "§b请求你传送到他那里!",
				"§a输入命令/tpaccept 或 /tpok 接受传送",
				"§c输入命令/tpdeny 或 /tpno 拒绝传送"
		});
	}
	
	@Override
	public boolean isOnlyPlayerExecutable() {return true;};

	@Override
	public int getMinimumArguments() {
		return 1;
	}

	@Override
	public String getPossibleArguments() {
		return "<目标玩家>";
	}
}
