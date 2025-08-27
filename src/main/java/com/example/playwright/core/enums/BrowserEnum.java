package com.example.playwright.core.enums;

public enum BrowserEnum {
    CHROME,
    CHROMIUM,
    FIREFOX,
    WEBKIT;

    public static BrowserEnum fromString(String value){
        return BrowserEnum.valueOf(value.toUpperCase());
    }
}
