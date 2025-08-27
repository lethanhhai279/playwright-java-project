package com.example.playwright.core.hooks;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer:
 * - Defines how many times a failed test should be retried before marking as FAILED.
 * - Useful for handling flaky tests (e.g., network delay, async loading).
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0; // số lần đã retry
    private static final int maxRetryCount = 2; // số lần retry tối đa (config theo nhu cầu)

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() +
                    " | Attempt " + (retryCount + 1));
            return true; // cho chạy lại test
        }
        return false; // hết số lần retry
    }
}
