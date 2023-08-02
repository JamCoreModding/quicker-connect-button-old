package io.github.jamalam360.quickerconnectbutton.platform;

import io.github.jamalam360.quickerconnectbutton.platform.services.PlatformHelper;
import net.minecraft.client.Minecraft;

import java.nio.file.Path;

public class ForgePlatformHelper implements PlatformHelper {

	@Override
	public String getPlatformName() {

		return "Forge";
	}

	@Override
	public Path getConfigDir() {
		return Minecraft.getInstance().gameDirectory.toPath().resolve("config");
	}
}
