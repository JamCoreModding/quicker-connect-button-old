package io.github.jamalam360.quickerconnectbutton;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
	public static Data load(Path configDir) {
		Path configPath = configDir.resolve("quickerconnectbutton.properties");
		Properties properties = new Properties();

		try {
			properties.load(Files.newInputStream(configPath));
		} catch (Exception e1) {
			Constants.LOGGER.warn("Encountered an exception while loading config, writing a default");
			properties.put("ip", "");
			properties.put("port", Integer.toString(25565));
			properties.put("replaceMultiplayerButton", Boolean.toString(false));
			properties.put("text", "Connect");

			try {
				properties.store(Files.newOutputStream(configPath), null);
			} catch (Exception e2) {
				Constants.LOGGER.warn("Failed to write default config, bailing out and giving up.");
				return null;
			}
		}

		String ip = (String) properties.get("ip");
		int port = properties.containsKey("port") ? Integer.parseInt((String) properties.get("port")) : 25565;
		boolean replaceMultiplayerButton = properties.containsKey("replaceMultiplayerButton") && Boolean.parseBoolean((String) properties.get("replaceMultiplayerButton"));
		String text = properties.containsKey("text") ? (String) properties.get("text") : "Connect";

		return new Data(ip, port, replaceMultiplayerButton, text);
	}

	public record Data(@Nullable String ip, int port, boolean replaceMultiplayerButton, String text) {
	}
}
