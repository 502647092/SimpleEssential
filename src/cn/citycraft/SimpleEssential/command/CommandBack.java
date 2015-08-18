/**
 *
 */
package cn.citycraft.SimpleEssential.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;

/**
 * @author 蒋天蓓 2015年8月12日下午2:04:05
 */
public class CommandBack extends BaseCommand {
	SimpleEssential plugin;

	/**
	 * @param name
	 */
	public CommandBack(SimpleEssential main) {
		super("back", "seback");
		this.plugin = main;
	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	};

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		plugin.tpcontrol.back((Player) sender);
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public String getPossibleArguments() {
		return "";
	}
}
