package com.example.playwright.pages;

import com.example.playwright.core.config.ConfigFactory;

public class LoginPage {
    public void signIn(){
        String url = ConfigFactory.getConfig().getProperty("baseUrl");
        String userName = ConfigFactory.getConfig().getProperty("userName");
        String passWord = ConfigFactory.getConfig().getProperty("passWord");
        
    }
}
