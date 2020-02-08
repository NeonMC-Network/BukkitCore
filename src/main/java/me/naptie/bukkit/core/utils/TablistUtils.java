package me.naptie.bukkit.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class TablistUtils {

    public static void sendTablist(Player player, String header, String footer) {

        try {
            Object headerObj = getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(CU.t(header));
            Object footerObj = getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(CU.t(footer));
            Object ppoplhf = getNmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor().newInstance();
            Field f = ppoplhf.getClass().getDeclaredField("header");
            f.setAccessible(true);
            f.set(ppoplhf, headerObj);
            Field fb = ppoplhf.getClass().getDeclaredField("footer");
            fb.setAccessible(true);
            fb.set(ppoplhf, footerObj);
            Object pcon = player.getClass().getMethod("getHandle").invoke(player);
            pcon = pcon.getClass().getField("playerConnection").get(pcon);
            pcon.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(pcon, ppoplhf);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }

}
