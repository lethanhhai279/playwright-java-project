package com.example.playwright.core.hooks;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import io.qameta.allure.Allure;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.example.playwright.core.report.AllureListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * TestListener integrates Allure reporting and Retry logic.
 */
public class TestListener implements ITestListener, IAnnotationTransformer {

    private final AllureListener allureListener = new AllureListener();

    // ========== Forward events to AllureListener ==========
    @Override
    public void onTestStart(ITestResult result) {
        allureListener.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        allureListener.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        allureListener.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        allureListener.onTestSkipped(result);
    }

    @Override
    public void onStart(ITestContext context) {
        allureListener.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        allureListener.onFinish(context);
        attachLogsToAllure();
    }

    // ========== Apply RetryAnalyzer dynamically ==========
    @Override
    @SuppressWarnings({"rawtypes"}) // Suppress raw type warnings from TestNG interface
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        if (annotation.getRetryAnalyzerClass() == null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }

    public void attachLogsToAllure() {
        try {
            File logFile = new File("logs/test-execution.log");
            if (logFile.exists()) {
                Allure.addAttachment("Execution Logs", new FileInputStream(logFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
