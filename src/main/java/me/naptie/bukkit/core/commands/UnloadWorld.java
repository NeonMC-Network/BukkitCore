package me.naptie.bukkit.core.commands;

import me.naptie.bukkit.core.Messages;
import me.naptie.bukkit.core.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnloadWorld implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission(Permissions.UNLOAD_WORLD)) {
                if (strings.length < 1) {
                    player.sendMessage(Messages.getMessage(player, "USAGE").replace("%usage%", "/unload <world>"));
                    return true;
                }
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.getWorld().equals(Bukkit.getWorld(strings[0]))) {
                        for (World world : Bukkit.getWorlds()) {
                            if (!world.equals(online.getWorld())) {
                                online.sendMessage(Messages.getMessage(online, "UNLOAD_TELEPORT"));
                                online.teleport(world.getSpawnLocation());
                                break;
                            }
                        }
                    }
                }
                Bukkit.unloadWorld(strings[0], true);
                player.sendMessage(Messages.getMessage(player, "WORLD_UNLOADED").replace("%world%", strings[0]));
                return true;
            } else {
                player.sendMessage(Messages.getMessage(player, "PERMISSION_DENIED"));
                return true;
            }
        } else {
            if (strings.length < 1) {
                commandSender.sendMessage(Messages.getMessage("zh-CN", "USAGE").replace("%usage%", "unload <world>"));
                return true;
            }
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.getWorld().equals(Bukkit.getWorld(strings[0]))) {
                    for (World world : Bukkit.getWorlds()) {
                        if (!world.equals(online.getWorld())) {
                            online.sendMessage(Messages.getMessage(online, "UNLOAD_TELEPORT"));
                            online.teleport(world.getSpawnLocation());
                            break;
                        }
                    }
                }
            }
            Bukkit.unloadWorld(strings[0], true);
            commandSender.sendMessage(Messages.getMessage("zh-CN", "WORLD_UNLOADED").replace("%world%", strings[0]));
            return true;
        }
    }

}
