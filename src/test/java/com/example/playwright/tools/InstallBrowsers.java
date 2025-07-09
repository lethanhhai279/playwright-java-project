package com.example.playwright.tools;
import com.microsoft.playwright.Playwright;

public class InstallBrowsers {
    public static void main(String[] args){
        try(Playwright playwright = Playwright.create()){
            System.out.println("Playwright browsers installed successfully!");
        } catch(Exception e){
            System.out.println("Failed to install browsers: " + e.getMessage());
        }
    }
}
