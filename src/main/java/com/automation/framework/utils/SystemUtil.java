package com.automation.framework.utils;

import java.io.*;
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

    public static void makeDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static FileReader readFile(String path) {
        try {
            return new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(String path, String content) {
        FileWriter file;
        try {
            file = new FileWriter(path);
            file.write(content);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}