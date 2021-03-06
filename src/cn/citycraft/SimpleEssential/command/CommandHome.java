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
 * 传送回家的命令
 * 
 * @author 蒋天蓓 2015年8月12日下午2:04:05
 */
public class CommandHome extends BaseCommand {
	SimpleEssential plugin;

	/**
	 * @param name
	 */
	public CommandHome(SimpleEssential main) {
		super("home", "eshome");
		this.plugin = main;
	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	};

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		Player p = (Player) sender;
		Location loc = p.getBedSpawnLocation();
		if (loc == null) {
			p.sendMessage(Language.getMessage("Teleport.homelose"));
			return;
		}
		plugin.tpcontrol.magicTeleport(p, loc);
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
