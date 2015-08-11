/**
 * 
 */
package cn.citycraft.SimpleEssential.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cn.citycraft.SimpleEssential.SimpleEssential;

/**
 * @author 蒋天蓓
 *         2015年8月11日下午4:00:35
 *         TODO
 */
public class SimpleEssentialCommand extends SimpleEssential {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO 自动生成的方法存根
		return super.onCommand(sender, command, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias,
			String[] args) {
		// TODO 自动生成的方法存根
		return super.onTabComplete(sender, command, alias, args);
	}

}
