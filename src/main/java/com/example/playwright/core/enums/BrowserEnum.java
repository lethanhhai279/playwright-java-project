package com.example.playwright.core.enums;

public enum BrowserEnum {
    CHROMIUM,
    FIREFOX,
    WEBKIT;

    public static BrowserEnum fromString(String value){
        return BrowserEnum.valueOf(value.toUpperCase());
    }
}
