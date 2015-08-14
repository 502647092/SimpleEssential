/**
 *
 */
package cn.citycraft.SimpleEssential;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import cn.citycraft.SimpleEssential.command.CommandBack;
import cn.citycraft.SimpleEssential.command.CommandHome;
import cn.citycraft.SimpleEssential.command.CommandSetHome;
import cn.citycraft.SimpleEssential.command.CommandTop;
import cn.citycraft.SimpleEssential.command.CommandTpa;
import cn.citycraft.SimpleEssential.command.CommandTpaccept;
import cn.citycraft.SimpleEssential.command.CommandTpdeny;
import cn.citycraft.SimpleEssential.command.CommandTphere;
import cn.citycraft.SimpleEssential.command.SimpleEssentialCommand;
import cn.citycraft.SimpleEssential.config.Config;
import cn.citycraft.SimpleEssential.config.Language;
import cn.citycraft.SimpleEssential.listen.PlayerLocationListen;
import cn.citycraft.SimpleEssential.teleport.TeleportControl;
import cn.citycraft.SimpleEssential.utils.VersionChecker;

/**
 * @author 蒋天蓓 2015年8月11日下午3:29:32 TODO
 */
public class SimpleEssential extends JavaPlugin {

	/**
	 * 传送控制
	 */
	public TeleportControl tpcontrol;
	/**
	 * 命令监听列表
	 */
	private List<SimpleEssentialCommand> commandlist;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for (SimpleEssentialCommand command : commandlist) {
			if (command.isValidTrigger(label)) {
				if (!command.hasPermission(sender)) {
					sender.sendMessage(Language.getMessage("Base.no-permission"));
					return true;
				}
				if (command.isOnlyPlayerExecutable() && !(sender instanceof Player)) {
					sender.sendMessage(Language.getMessage("Base.playercommand"));
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

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		tpcontrol = new TeleportControl(this);
		this.registerCommands();
		this.registerEvents();
		new VersionChecker(this);
	}

	/**
	 * 注册事件
	 */
	private void registerEvents() {
		registerEvent(new PlayerLocationListen(this));
	}

	@Override
	public void onLoad() {
		Config.load(this, "1.0");
		Language.load(this, "1.0");
	}

	/**
	 * 注册命令
	 */
	public void registerCommands() {
		commandlist = new ArrayList<SimpleEssentialCommand>();
		registerCommand(new CommandTpa(this));
		registerCommand(new CommandTop(this));
		registerCommand(new CommandTpaccept(this));
		registerCommand(new CommandTpdeny(this));
		registerCommand(new CommandTphere(this));
		registerCommand(new CommandBack(this));
		registerCommand(new CommandSetHome(this));
		registerCommand(new CommandHome(this));

	}

	/**
	 * 注册命令
	 *
	 * @param command
	 *            - 被注册的命令类
	 */
	public void registerCommand(SimpleEssentialCommand command) {
		commandlist.add(command);
	}

	/**
	 * 注册事件
	 * 
	 * @param listener
	 *            - 被注册的事件类
	 */
	public void registerEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
}
