/**
 *
 */
package cn.citycraft.SimpleEssential.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.config.Language;

/**
 * 设置家的命令
 * 
 * @author 蒋天蓓 2015年8月12日下午2:04:05
 */
public class CommandSetHome extends SimpleEssentialCommand {
	SimpleEssential plugin;

	public CommandSetHome(SimpleEssential main) {
		super("sethome", "essethome");
		this.plugin = main;
	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	};

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		Player p = (Player) sender;
		Block b = p.getLocation().getBlock();
		if (b.getType() == Material.BED_BLOCK) {
			p.setBedSpawnLocation(b.getLocation(), true);
			p.sendMessage(Language.getMessage("Teleport.sethomesuccess"));
		} else {
			p.sendMessage(Language.getMessage("Teleport.sethomeerror"));
		}
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
