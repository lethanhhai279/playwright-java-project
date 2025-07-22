package com.example.playwright.pages;

import com.example.playwright.core.config.ConfigFactory;
import com.example.playwright.core.utils.PlaywrightActions;
import com.example.playwright.locators.LoginLocators;

import io.qameta.allure.Allure;


public class LoginPage {
    protected PlaywrightActions WebUI;
    protected LoginLocators LoginLocator;

    public LoginPage(PlaywrightActions WebUI){
        this.WebUI = WebUI;
        this.LoginLocator = new LoginLocators();
    }

    public void signIn(){
        String userName = ConfigFactory.getConfig().getProperty("userName");
        String passWord = ConfigFactory.getConfig().getProperty("passWord");
        try{
            Allure.step("Enter username and password");
            WebUI.setText(LoginLocator.inputUserName(), userName);
            WebUI.setText(LoginLocator.inputPassword(), passWord);
            WebUI.takeFullPageScreenshot("Check signIn");
            Allure.step("Enter username and password");
            WebUI.click(LoginLocator.loginButton());
            Allure.step("Verify login success");
            
            WebUI.takeFullPageScreenshot("Check signIn");
            

        } catch(Exception e){
            Allure.step(String.format("Verify login failed! with %s", e));
        }
    }
}
