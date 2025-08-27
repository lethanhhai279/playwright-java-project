package com.example.playwright.locators;

public class LoginLocators {
    public String inputUserName(){
        return "//input[@name='username']";
    }

    public String inputPassword(){
        return "//input[@name='password']";
    }

    public String loginButton(){
        return "//button[@type='submit']";
    }
    
}
