package com.example.playwright.core.drivers;
import com.example.playwright.core.config.*;
import com.microsoft.playwright.*;
import com.example.playwright.core.enums.BrowserEnum;

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

        switch (browserType) {
            case CHROMIUM:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case FIREFOX:
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case WEBKIT:
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
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
    }
}
