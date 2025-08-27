package com.example.playwright.core.config;

public class ConfigReader {

    private ConfigReader() {
        // Private constructor to prevent instantiation
    }

    // Get config value as String
    public static String get(String key) {
        String value = ConfigFactory.getConfig().getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Config key not found: " + key);
        }
        return value.trim();
    }

    // Get config value as int
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    // Get config value as boolean
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
