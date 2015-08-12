/**
 * 
 */
package cn.citycraft.SimpleEssential.handler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.handler.command.CommandBack;
import cn.citycraft.SimpleEssential.handler.command.CommandTpa;
import cn.citycraft.SimpleEssential.handler.command.CommandTpaccept;
import cn.citycraft.SimpleEssential.handler.command.CommandTpdeny;

/**
 * @author 蒋天蓓
 *         2015年8月11日下午4:00:35
 *         TODO
 */
public class SimpleEssentialCommandHandler extends SimpleEssential {
	private List<SimpleEssentialCommand> commandlist;

	public SimpleEssentialCommandHandler() {
		commandlist = new ArrayList<SimpleEssentialCommand>();

		// registerSubCommand(new AddlineCommand());
		registerSubCommand(new CommandTpa(this));
		registerSubCommand(new CommandTpaccept(this));
		registerSubCommand(new CommandTpdeny(this));
		registerSubCommand(new CommandBack(this));
	}

	/**
	 * 注册命令
	 * 
	 * @param subCommand
	 *            - 被注册的命令类
	 */
	public void registerSubCommand(SimpleEssentialCommand subCommand) {
		commandlist.add(subCommand);
	}

	/**
	 * 获取命令列表
	 * 
	 * @return 命令列表
	 */
	public List<SimpleEssentialCommand> getSubCommands() {
		return new ArrayList<SimpleEssentialCommand>(commandlist);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for (SimpleEssentialCommand command : commandlist) {
			if (command.isValidTrigger(label)) {
				if (!command.hasPermission(sender)) {
					sender.sendMessage(ChatColor.RED + "你没有此命令的权限.");
					return true;
				}
				if (args.length >= command.getMinimumArguments()) {
					try {
						command.execute(sender, label, args);
						return true;
					} catch (CommandException e) {
						sender.sendMessage(e.getMessage());
					}
				}
			}
		}
		return false;
	}
}
