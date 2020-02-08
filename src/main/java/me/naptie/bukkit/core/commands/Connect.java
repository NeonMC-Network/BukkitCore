package me.naptie.bukkit.core.commands;

import me.naptie.bukkit.core.Main;
import me.naptie.bukkit.core.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Connect implements CommandExecutor {

	@SuppressWarnings("NullableProblems")
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (strings.length == 0) {
				player.sendMessage(Messages.getMessage(player, "USAGE").replace("%usage%", "/connect <server>"));
				return true;
			}
			Main.getInstance().connect(player, strings[0], true);
			return true;
		} else {
			if (strings.length <= 1) {
				commandSender.sendMessage(Messages.getMessage("zh-CN", "USAGE").replace("%usage%", "connect <player> <server>"));
				return true;
			}
			if (strings[1].equalsIgnoreCase(Main.getInstance().getServerName())) {
				commandSender.sendMessage(Messages.getMessage("zh-CN", "SAME_SERVER"));
				return true;
			}
			if (strings[0].equalsIgnoreCase("all")) {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						Main.getInstance().connect(player, strings[1], false);
						commandSender.sendMessage(Messages.getMessage("zh-CN", "SENDING_SB").replace("%player%", player.getName()).replace("%server%", strings[1]));
					}
					return true;
				} else {
                    commandSender.sendMessage(Messages.getMessage("zh-CN", "NO_ONE_ONLINE"));
                    return true;
				}
			} else {
				if (Bukkit.getPlayer(strings[0]) == null) {
					commandSender.sendMessage(Messages.getMessage("zh-CN", "PLAYER_NOT_FOUND").replace("%player%", strings[0]));
					return true;
				}
				Player player = Bukkit.getPlayer(strings[0]);
				Main.getInstance().connect(player, strings[1], false);
				commandSender.sendMessage(Messages.getMessage("zh-CN", "SENDING_SB").replace("%player%", player.getName()).replace("%server%", strings[1]));
				return true;
			}
		}
	}
}
