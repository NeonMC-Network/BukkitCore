package me.naptie.bukkit.core;

import org.bukkit.permissions.Permission;

public class Permissions {

    public static final Permission LOAD_WORLD = new Permission("neonmc.world.load");
    public static final Permission UNLOAD_WORLD = new Permission("neonmc.world.unload");
    public static final Permission TELEPORT = new Permission("neonmc.player.teleport");
    public static final Permission EDIT_INVENTORY = new Permission("neonmc.player.inventory.edit");
    public static final Permission CHAT_DETECTION_BYPASS = new Permission("neonmc.detection.chat.bypass");

}
