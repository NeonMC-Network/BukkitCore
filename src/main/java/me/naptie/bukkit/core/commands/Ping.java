package me.naptie.bukkit.core.commands;

import me.naptie.bukkit.core.Messages;
import me.naptie.bukkit.player.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                int ping = ((CraftPlayer) player).getHandle().ping;

                if (ping <= 200) {
                    player.sendMessage(getPingStatus(player, ChatColor.GREEN, ping));
                } else if (ping <= 400) {
                    player.sendMessage(getPingStatus(player, ChatColor.YELLOW, ping));
                } else if (ping <= 800) {
                    player.sendMessage(getPingStatus(player, ChatColor.RED, ping));
                } else {
                    player.sendMessage(getPingStatus(player, ChatColor.BLACK, ping));
                }
                return true;

            } else {
                commandSender.sendMessage(Messages.getMessage("zh-CN", "USAGE").replace("%usage%", "ping <player>"));
                return true;
            }
        } else {
            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                commandSender.sendMessage(Messages.getMessage("zh-CN", "PLAYER_NOT_FOUND").replace("%player%", strings[0]));
                return true;
            }
            int ping = ((CraftPlayer) target).getHandle().ping;

            if (ping <= 200) {
                commandSender.sendMessage(getPingStatus((commandSender instanceof Player) ? ConfigManager.getLanguageName((Player) commandSender) : "zh-CN", target, ChatColor.GREEN, ping));
            } else if (ping <= 400) {
                commandSender.sendMessage(getPingStatus((commandSender instanceof Player) ? ConfigManager.getLanguageName((Player) commandSender) : "zh-CN", target, ChatColor.YELLOW, ping));
            } else if (ping <= 800) {
                commandSender.sendMessage(getPingStatus((commandSender instanceof Player) ? ConfigManager.getLanguageName((Player) commandSender) : "zh-CN", target, ChatColor.RED, ping));
            } else {
                commandSender.sendMessage(getPingStatus((commandSender instanceof Player) ? ConfigManager.getLanguageName((Player) commandSender) : "zh-CN", target, ChatColor.BLACK, ping));
            }
            return true;
        }
    }

    private String getPingStatus(Player player, ChatColor c, int ping) {
        return Messages.getMessage(player, "SELF_PING_STATUS").replace("%ping%", c + "" + ping);
    }

    private String getPingStatus(String language, Player target, ChatColor c, int ping) {
        return Messages.getMessage(language, "PING_STATUS").replace("%player%", target.getDisplayName()).replace("%ping%", c + "" + ping);
    }

}
