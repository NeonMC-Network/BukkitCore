package me.naptie.bukkit.core.commands;

import me.naptie.bukkit.core.Main;
import me.naptie.bukkit.core.Messages;
import me.naptie.bukkit.core.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class LoadWorld implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission(Permissions.LOAD_WORLD)) {
                if (strings.length < 1) {
                    player.sendMessage(Messages.getMessage(player, "USAGE").replace("%usage%", "/load <world>"));
                    return true;
                }
                World world = Bukkit.createWorld(new WorldCreator(strings[0]));
                if (world == null) {
                    File worldDir = new File(Main.getInstance().getServer().getWorldContainer().getAbsolutePath(), strings[0]);
                    File uid = new File(worldDir, "uid.dat");
                    boolean success = uid.delete();
                    if (!success) {
                        Main.logger.info(Messages.getMessage("zh-CN", "UIDDAT_ALREADY_DELETED").replace("%path%", strings[0]));
                    }
                    world = Bukkit.createWorld(new WorldCreator(strings[0]));
                }
                if (world != null) {
                    player.sendMessage(Messages.getMessage(player, "WORLD_LOADED").replace("%world%", world.getName()));
                } else {
                    player.sendMessage(Messages.getMessage(player, "FAILED_TO_LOAD"));
                }
                return true;
            } else {
                player.sendMessage(Messages.getMessage(player, "PERMISSION_DENIED"));
                return true;
            }
        } else {
            if (strings.length < 1) {
                commandSender.sendMessage(Messages.getMessage("zh-CN", "USAGE").replace("%usage%", "load <world>"));
                return true;
            }
            World world = Bukkit.createWorld(new WorldCreator(strings[0]));
            if (world != null) {
                commandSender.sendMessage(Messages.getMessage("zh-CN", "WORLD_LOADED").replace("%world%", world.getName()));
            } else {
                commandSender.sendMessage(Messages.getMessage("zh-CN", "FAILED_TO_LOAD"));
            }
            return true;
        }
    }

}
