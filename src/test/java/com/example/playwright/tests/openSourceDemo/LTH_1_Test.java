package com.example.playwright.tests.openSourceDemo;
import com.example.playwright.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import org.testng.annotations.Test;

public class LTH_1_Test extends BaseTest{
    @Test(description = "Login test with screenshot and logs")
    @Description("Verify login functionality")
    @Epic("Login Feature")
    @Feature("Login Page")
    void verifyLoginPageSuccessfully(){
        LoginPage.signIn();
    }

}
