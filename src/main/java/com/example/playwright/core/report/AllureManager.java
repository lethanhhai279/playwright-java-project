package com.example.playwright.core.report;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class AllureManager {

    private AllureManager() {
        // Utility class
    }

    public static void logText(String message) {
        Allure.addAttachment("Log", new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8)));
    }

    public static void attachScreenshot(String name, Page page) {
        if (page != null) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        }
    }

    public static void attachLogFile(String name, String content) {
        if (content != null && !content.isEmpty()) {
            Allure.addAttachment(name, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        }
    }
}
