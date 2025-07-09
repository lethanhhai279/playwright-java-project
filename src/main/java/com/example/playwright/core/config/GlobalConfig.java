package com.example.playwright.core.config;
import java.io.InputStream;
import java.util.Properties;

public class GlobalConfig {
    private final Properties properties = new Properties();

    public GlobalConfig(String env){
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("config/" + env + ".properties")){
            if(input == null){
                throw new RuntimeException("Config file not found: " + env);
            }
            properties.load(input);
        } catch(Exception e){
            throw new RuntimeException("Failed to load config: " + e.getMessage());
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

}
