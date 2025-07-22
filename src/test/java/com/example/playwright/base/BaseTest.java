package com.example.playwright.base;

import com.example.playwright.core.config.ConfigFactory;
import com.example.playwright.core.drivers.DriverManager;
import com.example.playwright.core.utils.PlaywrightActions;
import com.example.playwright.pages.LoginPage;
import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;

import org.testng.annotations.BeforeMethod;


import org.testng.annotations.AfterMethod;

public class BaseTest {
    protected Page page;
    protected LoginPage LoginPage;
    protected PlaywrightActions WebUI;

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        page = DriverManager.getPage();
        String url = ConfigFactory.getConfig().getProperty("baseUrl");
        Allure.step(String.format("Navigate to %s login page sdsdsdsdASASDASDASD", url));
        page.navigate(url);
        WebUI = new PlaywrightActions(page);
        LoginPage = new LoginPage(WebUI);

    }

    @AfterMethod
    public void tearDown() {
        DriverManager.closeDriver();
    }
}
