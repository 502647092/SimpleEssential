/**
 * 
 */
package cn.citycraft.SimpleEssential.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;

/**
 * 拒绝传送命令
 * 
 * @author 蒋天蓓
 *         2015年8月12日下午2:04:05
 * 
 */
public class CommandTpdeny extends BaseCommand {
	SimpleEssential plugin;

	public CommandTpdeny(SimpleEssential main) {
		super("tpdeny", "tpno");
		this.plugin = main;
	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	};

	@Override
	public String getPossibleArguments() {
		return "";
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		plugin.tpcontrol.accept((Player) sender);

	}
}
