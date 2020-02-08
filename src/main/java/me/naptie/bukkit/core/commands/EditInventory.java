package me.naptie.bukkit.core.commands;

import me.naptie.bukkit.core.Messages;
import me.naptie.bukkit.core.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditInventory implements CommandExecutor {

    private static Map<UUID, Boolean> playerToEditableInventoryMap = new HashMap<>();

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission(Permissions.EDIT_INVENTORY)) {
                UUID uuid = player.getUniqueId();
                if (playerToEditableInventoryMap.containsKey(uuid)) {
                    if (getEditable(uuid)) {
                        setUneditable(uuid);
                        player.sendMessage(Messages.getMessage(player, "UNABLE_TO_EDIT_INV"));
                        return true;
                    } else {
                        setEditable(uuid);
                        player.sendMessage(Messages.getMessage(player, "ABLE_TO_EDIT_INV"));
                        return true;
                    }
                } else {
                    setEditable(uuid);
                    player.sendMessage(Messages.getMessage(player, "ABLE_TO_EDIT_INV"));
                    return true;
                }
            } else {
                player.sendMessage(Messages.getMessage(player, "PERMISSION_DENIED"));
                return true;
            }
        } else {
            commandSender.sendMessage(Messages.getMessage("zh-CN", "NOT_A_PLAYER"));
            return true;
        }

    }

    private void setEditable(UUID uuid) {
        playerToEditableInventoryMap.put(uuid, true);
    }

    private void setUneditable(UUID uuid) {
        playerToEditableInventoryMap.put(uuid, false);
    }

    public static boolean getEditable(UUID uuid) {
        if (playerToEditableInventoryMap.containsKey(uuid)) {
            return playerToEditableInventoryMap.get(uuid);
        } else {
            playerToEditableInventoryMap.put(uuid, false);
            return false;
        }
    }

}
