package cn.citycraft.SimpleEssential.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
// import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import cn.citycraft.SimpleEssential.SimpleEssential;
import cn.citycraft.SimpleEssential.config.Language;
// import cn.citycraft.SimpleEssential.config.Language;
import cn.citycraft.SimpleEssential.utils.EffectUtil;

// import cn.citycraft.SimpleEssential.utils.Randomer;

/**
 * 自杀命令
 *
 * @Author 代小呆 created in 2015年8月14日下午4:24:19
 */

public class CommandSuicide extends BaseCommand {
	private SimpleEssential plugin;

	public CommandSuicide(SimpleEssential main) {
		super("suicide", "sesuicide", "sd");
		this.plugin = main;

	}

	@Override
	public String getPossibleArguments() {
		return "";
	}

	@Override
	public int getMinimumArguments() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		Player p = (Player) sender;
		List<ItemStack> drops = Arrays.asList(p.getInventory().getContents());
		int deoppedexp = (int) Math.floor(p.getExp());
		String deathMessage = Language.getMessage("Suicide.msg");
		PlayerDeathEvent pd = new PlayerDeathEvent(p, drops, deoppedexp, deathMessage);
		plugin.getServer().getPluginManager().callEvent(pd);
		Bukkit.broadcastMessage(pd.getDeathMessage());
		EffectUtil.run(p.getLocation(), 10, Effect.POTION_BREAK);
		p.setHealth(0);
	}

	@Override
	public boolean isOnlyPlayerExecutable() {
		return true;
	}

}
