package com.example.playwright.core.logger;

import org.slf4j.Logger;

/**
 * Centralized Logger Factory for the project.
 */
public class LogManager {

    private LogManager() {
        // Prevent instantiation
    }

    public static Logger getLogger(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }
}
