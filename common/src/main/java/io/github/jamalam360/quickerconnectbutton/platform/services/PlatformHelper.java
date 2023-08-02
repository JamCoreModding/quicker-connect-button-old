package io.github.jamalam360.quickerconnectbutton.platform.services;

import java.nio.file.Path;

public interface PlatformHelper {
    String getPlatformName();
    Path getConfigDir();
}
