package com.example.playwright.core.report;

import com.microsoft.playwright.Page;
import org.testng.*;

public class AllureListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        AllureManager.logText("START TEST: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        AllureManager.logText("PASSED TEST: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        AllureManager.logText("FAILED TEST: " + result.getMethod().getMethodName());

        // Attach screenshot nếu có Page object trong parameters
        Object pageObj = getPageFromParameters(result);
        if (pageObj instanceof Page) {
            AllureManager.attachScreenshot("Failure Screenshot", (Page) pageObj);
        }

        if (result.getThrowable() != null) {
            AllureManager.attachLogFile("Exception", result.getThrowable().toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        AllureManager.logText("SKIPPED TEST: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        AllureManager.logText("TEST SUITE FINISHED: " + context.getName());
    }

    private Object getPageFromParameters(ITestResult result) {
        for (Object param : result.getParameters()) {
            if (param instanceof Page) {
                return param;
            }
        }
        return null;
    }
}
