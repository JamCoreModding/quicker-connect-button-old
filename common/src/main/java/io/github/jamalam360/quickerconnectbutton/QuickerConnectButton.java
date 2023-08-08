package io.github.jamalam360.quickerconnectbutton;

import io.github.jamalam360.quickerconnectbutton.platform.Services;
import io.github.jamalam360.quickerconnectbutton.platform.services.PlatformHelper;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class QuickerConnectButton {
	@Nullable
	public static Config.Data config;

	public static void init() {
		config = Config.load(Services.PLATFORM.getConfigDir());
		Constants.LOGGER.info("Loaded Quicker Connect Button on " + Services.PLATFORM.getPlatformName());
	}

	public static boolean enabled() {
		return QuickerConnectButton.config.ip() != null && !QuickerConnectButton.config.ip().equals("");
	}

	public static Component getButtonText() {
		if (QuickerConnectButton.config.text() != null && !QuickerConnectButton.config.text().equals("")) {
			return Component.literal(QuickerConnectButton.config.text());
		} else {
			return Component.translatable("quickerconnectbutton.menu.connect");
		}
	}

	public static ServerData createServerData() {
		return new ServerData(I18n.get("selectServer.defaultName"), QuickerConnectButton.config.ip(), false);
	}
}
