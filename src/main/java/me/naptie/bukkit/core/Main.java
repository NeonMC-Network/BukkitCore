package me.naptie.bukkit.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.naptie.bukkit.core.commands.*;
import me.naptie.bukkit.core.listeners.EventListener;
import me.naptie.bukkit.core.listeners.PluginListener;
import me.naptie.bukkit.core.utils.CU;
import me.naptie.bukkit.core.utils.CoreStorage;
import me.naptie.bukkit.core.utils.TablistUtils;
import me.naptie.bukkit.player.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Main extends JavaPlugin {

	public static Logger logger;
	private static Main instance;
	private PluginListener listener;
	private PluginDescriptionFile descriptionFile;
	private String serverName;

	public static Main getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		logger = getLogger();
		descriptionFile = getDescription();
		registerConfig();
		registerCommands();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		listener = new PluginListener();
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", listener);
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		runTabList();
		logger.info("Enabled " + descriptionFile.getName() + " v" + descriptionFile.getVersion());
	}

	public void onDisable() {
		instance = null;
		logger.info("Disabled " + descriptionFile.getName() + " v" + descriptionFile.getVersion());
		logger = null;
	}

	@SuppressWarnings("ConstantConditions")
	private void registerCommands() {
		getCommand("load").setExecutor(new LoadWorld());
		getCommand("unload").setExecutor(new UnloadWorld());
		getCommand("ping").setExecutor(new Ping());
		getCommand("teleporttolocation").setExecutor(new Teleport());
		getCommand("editinventory").setExecutor(new EditInventory());
		getCommand("connect").setExecutor(new Connect());
	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		getConfig().options().copyHeader(true);
		saveDefaultConfig();
		serverName = CoreStorage.getServerName(getServer().getPort());
		for (String language : getConfig().getStringList("languages")) {
			File localeFile = new File(getDataFolder(), language + ".yml");
			if (localeFile.exists()) {
				if (getConfig().getBoolean("update-language-files")) {
					saveResource(language + ".yml", true);
				}
			} else {
				saveResource(language + ".yml", false);
			}
		}
	}

	@SuppressWarnings("UnstableApiUsage")
	public void connect(final Player p, final String target, final boolean display) {
		if (target.equalsIgnoreCase(getServerName())) {
			if (display) {
				p.sendMessage(Messages.getMessage(p, "ALREADY_IN"));
			}
			return;
		}
		ByteArrayDataOutput getServers = ByteStreams.newDataOutput();
		getServers.writeUTF("GetServers");
		p.sendPluginMessage(this, "BungeeCord", getServers.toByteArray());
		ExecutorService thread = Executors.newSingleThreadExecutor();
		thread.execute(() -> {
			Main.this.waitForServerList();
			for (String server : Main.getInstance().listener.serverList) {
				if (server.equalsIgnoreCase(target)) {
					ByteArrayDataOutput connect = ByteStreams.newDataOutput();
					connect.writeUTF("Connect");
					connect.writeUTF(server);
					if (display) {
						p.sendMessage(Messages.getMessage(p, "SENDING").replace("%server%", server));
					}
					p.sendPluginMessage(Main.getInstance(), "BungeeCord", connect.toByteArray());
					return;
				}
			}
			if (display) {
				p.sendMessage(Messages.getMessage(p, "SERVER_NOT_FOUND").replace("%server%", target));
			}
		});

		thread.shutdown();
	}

	@SuppressWarnings("UnstableApiUsage")
	public void connectWithoutChecking(final Player p, final String target, final boolean display) {
		if (target.equalsIgnoreCase(getServerName())) {
			if (display) {
				p.sendMessage(Messages.getMessage(p, "ALREADY_IN"));
			}
			return;
		}
		ByteArrayDataOutput connect = ByteStreams.newDataOutput();
		connect.writeUTF("Connect");
		connect.writeUTF(target);
		if (display) {
			p.sendMessage(Messages.getMessage(p, "SENDING").replace("%server%", target));
		}
		p.sendPluginMessage(Main.getInstance(), "BungeeCord", connect.toByteArray());
	}

	private void waitForServerList() {
		if (Main.getInstance().listener.serverList == null) {
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitForServerList();
		}
	}

	private void runTabList() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() == 0) {
					return;
				}
				for (Player player : Bukkit.getOnlinePlayers()) {

					List<String> headerList = getConfig().getStringList("tab-list.header." + ConfigManager.getLanguageName(player));
					StringBuilder headerString = new StringBuilder();
					for (int i = 0; i < headerList.size(); i++) {
						if (i == 0) {
							headerString = new StringBuilder(headerList.get(0));
						} else {
							headerString.append("\n").append(headerList.get(i));
						}
					}
					if (headerString.toString().contains("%server%")) {
						headerString = new StringBuilder(headerString.toString().replace("%server%", getServerName()));
					}
					if (headerString.toString().contains("%time%")) {
						headerString = new StringBuilder(headerString.toString().replace("%time%", getCurrentFormattedDate(getConfig().getString("tab-list.time-format." + ConfigManager.getLanguageName(player)))));
					}
					String header = CU.t(headerString.toString());
					List<String> footerList = getConfig().getStringList("tab-list.footer." + ConfigManager.getLanguageName(player));
					StringBuilder footerString = new StringBuilder();
					for (int i = 0; i < footerList.size(); i++) {
						if (i == 0) {
							footerString = new StringBuilder(footerList.get(0));
						} else {
							footerString.append("\n").append(footerList.get(i));
						}
					}
					if (footerString.toString().contains("%server%")) {
						footerString = new StringBuilder(footerString.toString().replace("%server%", getServerName()));
					}
					if (footerString.toString().contains("%time%")) {
						footerString = new StringBuilder(footerString.toString().replace("%time%", getCurrentFormattedDate(getConfig().getString("tab-list.time-format." + ConfigManager.getLanguageName(player)))));
					}
					String footer = CU.t(footerString.toString());

					TablistUtils.sendTablist(player, header, footer);
				}
			}

		}.runTaskTimer(this, 0, 20);
	}

	private String getCurrentFormattedDate(String format) {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(date);
	}

	public String getServerName() {
		return serverName;
	}
}
