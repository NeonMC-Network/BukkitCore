package me.naptie.bukkit.core.listeners;

import me.naptie.bukkit.core.Main;
import me.naptie.bukkit.core.Messages;
import me.naptie.bukkit.core.Permissions;
import me.naptie.bukkit.core.commands.EditInventory;
import me.naptie.bukkit.core.utils.CoreStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class EventListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		CoreStorage.hasInventoryOpen.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerClick(InventoryClickEvent event) {
		if (event.getWhoClicked().getType() == EntityType.PLAYER) {
			Player player = (Player) event.getWhoClicked();
			if (!EditInventory.getEditable(player.getUniqueId())) {
				if (!(Main.getInstance().getServerName().toLowerCase().contains("mini") || Main.getInstance().getServerName().toLowerCase().contains("mega") || Main.getInstance().getServerName().toLowerCase().contains("integration"))) {
					if (player.getWorld().getName().toLowerCase().contains("lobby")) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (!EditInventory.getEditable(player.getUniqueId())) {
			if (!(Main.getInstance().getServerName().toLowerCase().contains("mini") || Main.getInstance().getServerName().toLowerCase().contains("mega") || Main.getInstance().getServerName().toLowerCase().contains("integration"))) {
				if (player.getWorld().getName().toLowerCase().contains("lobby")) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (!EditInventory.getEditable(player.getUniqueId())) {
			if (!(Main.getInstance().getServerName().toLowerCase().contains("mini") || Main.getInstance().getServerName().toLowerCase().contains("mega") || Main.getInstance().getServerName().toLowerCase().contains("integration"))) {
				if (player.getWorld().getName().toLowerCase().contains("lobby")) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setFormat("%s" + ": " + "%s");
		Player player = event.getPlayer();
		if (!player.hasPermission(Permissions.CHAT_DETECTION_BYPASS)) {
			String msg = event.getMessage().toLowerCase();
			for (String badWord : Messages.ENGLISH_BAD_WORDS) {
				if (msg.contains(badWord)) {
					event.setCancelled(true);
					player.sendMessage(Messages.getMessage(player, "NO_SWEARING"));
				}
			}
			for (String badWord : Messages.CHINESE_BAD_WORDS) {
				if (msg.contains(badWord)) {
					event.setCancelled(true);
					player.sendMessage(Messages.getMessage(player, "NO_SWEARING"));
				}
			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (!CoreStorage.hasInventoryOpen.contains(event.getPlayer().getUniqueId()))
			CoreStorage.hasInventoryOpen.add(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		CoreStorage.hasInventoryOpen.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location location = player.getLocation();

		if (location.getY() >= 130) {
			player.setPlayerWeather(WeatherType.CLEAR);
		} else {
			player.resetPlayerWeather();
		}
	}

}
