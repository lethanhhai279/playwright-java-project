package com.example.playwright.tests;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;

public class FirstTest {
    @Test
    public void runGoogleSearch(){
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            page.navigate("https://www.google.com");
            page.fill("//textarea[@name='q']", "Playwright Java");
            page.keyboard().press("Enter");
            page.waitForTimeout(3000);
            browser.close();
        }
    }
}
