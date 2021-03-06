/**
 * 
 */
package cn.citycraft.SimpleEssential.command;

import org.bukkit.Location;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.config.Language;

/**
 * 传送到顶部命令
 * 
 * @author 蒋天蓓
 *         2015年8月12日下午2:04:05
 * 
 */
public class CommandTop extends BaseCommand {
	SimpleEssential plugin;

	/**
	 * @param name
	 */
	public CommandTop(SimpleEssential main) {
		super("top", "estop");
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
		Player p = (Player) sender;
		Location loc = p.getLocation();
		int top = loc.getWorld().getHighestBlockYAt(loc);
		loc.setY(top);
		p.teleport(loc);
		p.sendMessage(Language.getMessage("Teleport.top"));
	}
}
