package me.naptie.bukkit.core.utils;

import me.naptie.bukkit.core.Main;

import java.util.*;

public class CoreStorage {

	public static List<UUID> hasInventoryOpen = new ArrayList<>();

	public static String getServerName(int port) {
		String portRange = "000";
		String additionalNumber = "000";
		if (port >= 30000 && port < 40000) {
			portRange = String.valueOf(port).substring(0, 3);
			additionalNumber = String.valueOf(port).substring(3);
		}
		if (port >= 40000) {
			portRange = String.valueOf(port).substring(0, 2);
			additionalNumber = String.valueOf(port).substring(2);
		}
		if (!portRange.equals("000") && !additionalNumber.equals("000")) {
			return Main.getInstance().getConfig().getString("servers." + portRange) + additionalNumber;
		} else {
			return "Unnamed";
		}
	}

}
