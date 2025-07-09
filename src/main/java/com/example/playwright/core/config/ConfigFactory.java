package com.example.playwright.core.config;

public class ConfigFactory {
    private static final String ENV = System.getProperty("env", "dev");
    private static GlobalConfig config = new GlobalConfig(ENV);

    public static GlobalConfig getConfig(){
        return config;
    }
}
