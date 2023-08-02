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

			try {
				properties.store(Files.newOutputStream(configPath), null);
			} catch (Exception e2) {
				Constants.LOGGER.warn("Failed to write default config, bailing out and giving up.");
				return null;
			}
		}

		int port = properties.contains("port") ? Integer.parseInt((String) properties.get("port")) : 25565;

		return new Data((String) properties.get("ip"), port);
	}

	public record Data(@Nullable String ip, int port) {

	}
}
