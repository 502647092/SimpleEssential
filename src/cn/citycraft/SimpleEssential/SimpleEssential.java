/**
 *
 */
package cn.citycraft.SimpleEssential;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import cn.citycraft.SimpleEssential.command.CommandBack;
import cn.citycraft.SimpleEssential.command.CommandTpa;
import cn.citycraft.SimpleEssential.command.CommandTpaccept;
import cn.citycraft.SimpleEssential.command.CommandTpdeny;
import cn.citycraft.SimpleEssential.command.CommandTphere;
import cn.citycraft.SimpleEssential.command.SimpleEssentialCommand;
import cn.citycraft.SimpleEssential.config.Config;
import cn.citycraft.SimpleEssential.listen.PlayerLocationListen;
import cn.citycraft.SimpleEssential.teleport.TeleportControl;

/**
 * @author 蒋天蓓 2015年8月11日下午3:29:32 TODO
 */
public class SimpleEssential extends JavaPlugin {
	public TeleportControl tpcontrol;
	private List<SimpleEssentialCommand> commandlist;

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
				sender.sendMessage("命令参数错误!");
			}
		}
		sender.sendMessage("循环结束!");
		return true;
	}

	@Override
	public void onDisable() {
		this.getLogger().info("");
	}

	@Override
	public void onEnable() {
		commandlist = new ArrayList<SimpleEssentialCommand>();
		// registerSubCommand(new AddlineCommand());
		registerSubCommand(new CommandTpa(this));
		registerSubCommand(new CommandTpaccept(this));
		registerSubCommand(new CommandTpdeny(this));
		registerSubCommand(new CommandTphere(this));
		registerSubCommand(new CommandBack(this));

		tpcontrol = new TeleportControl(this);
		getServer().getPluginManager().registerEvents(new PlayerLocationListen(this), this);
	}

	@Override
	public void onLoad() {
		Config.load(this, "1.0");
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
}
