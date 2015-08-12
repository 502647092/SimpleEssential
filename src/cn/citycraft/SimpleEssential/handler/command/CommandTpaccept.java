/**
 * 
 */
package cn.citycraft.SimpleEssential.handler.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.handler.SimpleEssentialCommand;

/**
 * @author 蒋天蓓
 *         2015年8月12日下午2:04:05
 *         TODO
 */
public class CommandTpaccept extends SimpleEssentialCommand {
	SimpleEssential plugin;

	/**
	 * @param name
	 */
	public CommandTpaccept(SimpleEssential main) {
		super("tpaccept", "tpok");
		this.plugin = main;
	}

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