package io.github.jamalam360.quickerconnectbutton;

import io.github.jamalam360.quickerconnectbutton.platform.Services;
import org.jetbrains.annotations.Nullable;

public class QuickerConnectButton {
	@Nullable
	public static Config.Data config;

	public static void init() {
		config = Config.load(Services.PLATFORM.getConfigDir());
	}
}
