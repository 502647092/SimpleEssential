package cn.citycraft.SimpleEssential.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.config.Language;

/**
 * @Author 代小呆 created in 2015年8月16日下午1:44:22
 */

public class CommandHat extends BaseCommand {

	@SuppressWarnings("unused")
	private SimpleEssential plugin;

	public CommandHat(SimpleEssential main) {
		super("hat", "sehat");
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
		Player p = (Player) sender;
		if (p.getItemInHand() == null) {
			sender.sendMessage(Language.getMessage("Hat.empty"));
			return;
		} else {
			ItemStack hand = p.getItemInHand();
			p.setItemInHand(null);
			ItemStack helmet = p.getInventory().getHelmet();
			if (!(helmet == null)) {
				p.getInventory().addItem(helmet);
			}
			p.getInventory().setHelmet(hand);
			sender.sendMessage(Language.getMessage("Hat.enjoy"));
		}

	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	}

}
