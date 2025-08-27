package com.example.playwright.core.constants;

public final class FrameworkConstants {

    private FrameworkConstants() {
        // Private constructor to prevent instantiation
    }

    // Path to the config folder
    public static final String CONFIG_FOLDER_PATH = "src/main/resources/config/";

    // Path to the logs folder
    public static final String LOGS_FOLDER_PATH = "target/logs/";

    // Path to the reports folder
    public static final String REPORTS_FOLDER_PATH = "target/reports/";

    // Path to the screenshots folder
    public static final String SCREENSHOTS_FOLDER_PATH = "target/screenshots/";

    // Default timeout (in seconds)
    public static final int DEFAULT_TIMEOUT = 30;

    // Browser names
    public static final String CHROME = "CHROME";
    public static final String FIREFOX = "FIREFOX";
    public static final String EDGE = "EDGE";
}
