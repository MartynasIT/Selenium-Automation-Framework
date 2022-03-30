package com.framework.utils;

import java.util.Locale;

public class SystemUtil {
    public enum OSType {
        WINDOWS, MACOS, LINUX
    }

    protected static OSType detectedOS;

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac")) || (os.contains("darwin"))) {
                detectedOS = OSType.MACOS;
            } else if (os.contains("win")) {
                detectedOS = OSType.WINDOWS;
            } else if (os.contains("nux")) {
                detectedOS = OSType.LINUX;
            }
        }
        return detectedOS;
    }
}
