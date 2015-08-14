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

/**
 * @author 蒋天蓓 2015年8月12日下午2:04:05 TODO
 */
public class CommandSetHome extends SimpleEssentialCommand {
	SimpleEssential plugin;

	/**
	 * @param name
	 */
	public CommandSetHome(SimpleEssential main) {
		super("sethome", "essethome");
		this.plugin = main;
	}
	
	@Override
	public boolean isOnlyPlayerExecutable() {return true;};

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		Player p = (Player) sender;
		Block b = p.getLocation().getBlock();
		if (b.getType() == Material.BED_BLOCK) {
			p.setBedSpawnLocation(b.getLocation(), true);
			p.sendMessage("§a家设置成功!");
		} else {
			p.sendMessage("§c请站在床上设置家!");
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
