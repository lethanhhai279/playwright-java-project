package com.example.playwright.core.base;

import com.example.playwright.core.config.ConfigFactory;
import com.example.playwright.core.drivers.DriverManager;
import com.microsoft.playwright.Page;
import org.testng.annotations.BeforeMethod;


import org.testng.annotations.AfterMethod;

public class BaseTest {
    protected Page page;

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        page = DriverManager.getPage();
        String url = ConfigFactory.getConfig().getProperty("baseUrl");
        page.navigate(url);
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.closeDriver();
    }
}
