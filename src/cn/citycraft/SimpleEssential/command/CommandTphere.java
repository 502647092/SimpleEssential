/**
 *
 */
package cn.citycraft.SimpleEssential.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.config.Language;
import cn.citycraft.SimpleEssential.teleport.TeleportType;

/**
 * 传送到玩家命令
 * 
 * @author 蒋天蓓 2015年8月12日下午2:04:05
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
			sender.sendMessage(Language.getMessage("Base.offline", args[0]));
			return;
		}
		plugin.tpcontrol.addtp((Player) sender, Bukkit.getPlayer(args[0]), TeleportType.TPH);
		sender.sendMessage(Language.getMessage("Teleport.tpsend"));
		target.sendMessage(new String[] {
				Language.getMessage("Teleport.tphere", sender.getName()),
				Language.getMessage("Teleport.tpaccept"),
				Language.getMessage("Teleport.tpdeny")
		});
	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	};

	@Override
	public int getMinimumArguments() {
		return 1;
	}

	@Override
	public String getPossibleArguments() {
		return "<目标玩家>";
	}
}
