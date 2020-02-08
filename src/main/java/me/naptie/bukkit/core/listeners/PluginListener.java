package me.naptie.bukkit.core.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginListener implements PluginMessageListener {

	public String[] serverList;

	@SuppressWarnings("NullableProblems")
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		@SuppressWarnings("UnstableApiUsage")
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String command = in.readUTF();
		if (command.equals("GetServers")) {
			serverList = in.readUTF().split(", ");
		}
	}

}
