package com.example.playwright.core.drivers;

import com.example.playwright.core.config.ConfigFactory;
import com.example.playwright.core.config.ConfigReader;
import com.example.playwright.core.enums.BrowserEnum;
import com.microsoft.playwright.*;

public class DriverManager {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    public static void initDriver(){
        if(playwright == null){
            playwright = Playwright.create();
            System.out.println("Playwright created successfully");
        }

        String browserName = ConfigFactory.getConfig().getProperty("browser");
        BrowserEnum browserType = BrowserEnum.fromString(browserName);

        System.out.println("Launching browser: " + browserType);

        switch (browserType) {
            case CHROME:
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setChannel("chrome")
                                .setHeadless(ConfigReader.getBoolean("headless"))
                );
                break;

            case CHROMIUM:
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(ConfigReader.getBoolean("headless"))
                );
                break;

            case FIREFOX:
                browser = playwright.firefox().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(ConfigReader.getBoolean("headless"))
                );
                break;

            case WEBKIT:
                browser = playwright.webkit().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(ConfigReader.getBoolean("headless"))
                );
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browserType);
        }

        context = browser.newContext();
        page = context.newPage();
    }

    public static Page getPage(){
        return page;
    }

    public static void closeDriver(){
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("Playwright closed successfully");
    }
}
