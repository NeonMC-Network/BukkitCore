package me.naptie.bukkit.core.commands;

import me.naptie.bukkit.core.Messages;
import me.naptie.bukkit.core.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permissions.TELEPORT)) {
                player.sendMessage(Messages.getMessage(player, "PERMISSION_DENIED"));
                return true;
            }
            if (strings.length == 0) {
                player.sendMessage(Messages.getMessage(player, "USAGE").replace("%usage%", "/teleporttolocation <world> [x] [y] [z]"));
            } else if (strings.length == 1) {
                World w = Bukkit.getServer().getWorld(strings[0]);
                if (w == null) {
                    player.sendMessage(Messages.getMessage(player, "WORLD_NOT_FOUND").replace("%world%", strings[0]));
                    return true;
                }
                Location target = w.getSpawnLocation();
                player.teleport(target);
            } else if (strings.length == 4) {
                World w = Bukkit.getServer().getWorld(strings[0]);
                if (w == null) {
                    player.sendMessage(Messages.getMessage(player, "WORLD_NOT_FOUND").replace("%world%", strings[0]));
                    return true;
                }
                double x = Integer.parseInt(strings[1]);
                double y = Integer.parseInt(strings[2]);
                double z = Integer.parseInt(strings[3]);
                Location target = new Location(w, x, y, z);
                player.teleport(target);
            }
            return true;
        } else {
            commandSender.sendMessage(Messages.getMessage("zh-CN", "NOT_A_PLAYER"));
            return true;
        }
    }
}
