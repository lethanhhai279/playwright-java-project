package com.example.playwright.core.utils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import io.qameta.allure.Allure;

import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.BrowserContext;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PlaywrightActions {
    private Page page = null;
    private Page previousPage = null;
    private BrowserContext context;
    private Map<Integer, Integer> numberOfPages = new HashMap<>();
    DecimalFormat timeFormat = new DecimalFormat("#.##");
    
    public PlaywrightActions(Page page){
        this.page = page;
    }

    public PlaywrightActions(BrowserContext context, Page page){
        this.context = context;
        this.page = page;
        numberOfPages.put(context.hashCode(), 1);
    }

    public void switchToSpecificContextWithPageIndex(BrowserContext context, int pageIndex){
        this.page = context.pages().get(0);
        this.context = context;
        page.bringToFront();
        if(!numberOfPages.keySet().contains(page.context().hashCode())){
            numberOfPages.put(page.context().hashCode(), 1);
        }
    }

    public void switchToSpecificContextWithFirstPage(BrowserContext context){
        switchToSpecificContextWithPageIndex(context, 0);
    }

    public void switchToSpecificContextWithLastPage(BrowserContext context){
        switchToSpecificContextWithPageIndex(context, context.pages().size() - 1);
    }

    public void setText(String locator, String textValue){
        page.fill(locator, textValue);
    }

    public void click(String locator){
        page.click(locator);
    }

    /**
     * Wait until element is visible on UI
     *
     * @param locator - CSS/XPath selector
     * @param timeout - timeout in ms
     */
    public void waitForElementVisible(String locator, int timeout){
        long start = System.currentTimeMillis();
        try {
            page.waitForSelector(locator, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));
            long end = System.currentTimeMillis();
            System.out.println("Element visible: " + locator
                                + " | Waited: " + timeFormat.format((end - start) / 1000.0) + "s");
        } catch (Exception e) {
            throw new RuntimeException("Element NOT visible after " + timeout + " ms: " + locator, e);
        }
    }

    // Overload: use default timeout = 5s
    public void waitForElementVisible(String locator){
        waitForElementVisible(locator, 5000);
    }

        /**
     * Wait until element is hidden (not visible but still in DOM)
     */
    public void waitForElementNotVisible(String locator, int timeout){
        waitFor(locator, WaitForSelectorState.HIDDEN, timeout,
                "Element hidden: ", "Element still visible after " + timeout + " ms: ");
    }

    public void waitForElementNotVisible(String locator){
        waitForElementNotVisible(locator, 5000);
    }

    /**
     * Wait until element is present in DOM (attached)
     */
    public void waitForElementPresent(String locator, int timeout){
        waitFor(locator, WaitForSelectorState.ATTACHED, timeout,
                "Element present in DOM: ", "Element NOT present after " + timeout + " ms: ");
    }

    public void waitForElementPresent(String locator){
        waitForElementPresent(locator, 5000);
    }

    /**
     * Wait until element is detached (removed from DOM)
     */
    public void waitForElementNotPresent(String locator, int timeout){
        waitFor(locator, WaitForSelectorState.DETACHED, timeout,
                "Element removed from DOM: ", "Element still present after " + timeout + " ms: ");
    }

    public void waitForElementNotPresent(String locator){
        waitForElementNotPresent(locator, 5000);
    }

    /**
     * Wait until element is clickable (visible + enabled)
     * Note: Playwright does not have "CLICKABLE" state, so we check by isEnabled after visible.
     */
    public void waitForElementClickable(String locator, int timeout){
        long start = System.currentTimeMillis();
        try {
            page.waitForSelector(locator, new WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            if (!page.isEnabled(locator)) {
                throw new RuntimeException("Element is visible but disabled: " + locator);
            }

            long end = System.currentTimeMillis();
            System.out.println("Element clickable: " + locator
                    + " | Waited: " + timeFormat.format((end - start) / 1000.0) + "s");

        } catch (Exception e) {
            throw new RuntimeException("Element NOT clickable after " + timeout + " ms: " + locator, e);
        }
    }

    public void waitForElementClickable(String locator){
        waitForElementClickable(locator, 5000);
    }

    // ===================== PRIVATE SUPPORT =====================

    private void waitFor(String locator, WaitForSelectorState state, int timeout,
                         String successMsg, String failMsg) {
        long start = System.currentTimeMillis();
        try {
            page.waitForSelector(locator, new WaitForSelectorOptions()
                    .setState(state)
                    .setTimeout(timeout));
            long end = System.currentTimeMillis();
            System.out.println(successMsg + locator
                    + " | Waited: " + timeFormat.format((end - start) / 1000.0) + "s");
        } catch (Exception e) {
            throw new RuntimeException(failMsg + locator, e);
        }
    }

    // ===================== VERIFY METHODS (return boolean like Katalon) =====================

    /**
     * Verify element is visible
     */
    public boolean verifyElementVisible(String locator, int timeout){
        return verify(locator, WaitForSelectorState.VISIBLE, timeout);
    }

    public boolean verifyElementVisible(String locator){
        return verifyElementVisible(locator, 5000);
    }

    /**
     * Verify element is hidden
     */
    public boolean verifyElementNotVisible(String locator, int timeout){
        return verify(locator, WaitForSelectorState.HIDDEN, timeout);
    }

    public boolean verifyElementNotVisible(String locator){
        return verifyElementNotVisible(locator, 5000);
    }

    /**
     * Verify element present in DOM
     */
    public boolean verifyElementPresent(String locator, int timeout){
        return verify(locator, WaitForSelectorState.ATTACHED, timeout);
    }

    public boolean verifyElementPresent(String locator){
        return verifyElementPresent(locator, 5000);
    }

    /**
     * Verify element removed from DOM
     */
    public boolean verifyElementNotPresent(String locator, int timeout){
        return verify(locator, WaitForSelectorState.DETACHED, timeout);
    }

    public boolean verifyElementNotPresent(String locator){
        return verifyElementNotPresent(locator, 5000);
    }

    /**
     * Verify element is clickable (visible + enabled)
     */
    public boolean verifyElementClickable(String locator, int timeout){
        try {
            page.waitForSelector(locator, new WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            return page.isEnabled(locator);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyElementClickable(String locator){
        return verifyElementClickable(locator, 5000);
    }

    // ===================== PRIVATE SUPPORT FOR VERIFY =====================
    private boolean verify(String locator, WaitForSelectorState state, int timeout){
        try {
            page.waitForSelector(locator, new WaitForSelectorOptions()
                    .setState(state)
                    .setTimeout(timeout));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

        // ===================== CHECKBOX / RADIO ACTIONS =====================

    /**
     * Check a checkbox
     * Only works with <input type="checkbox"> or [role=checkbox]
     */
    public void check(String locator){
        try {
            page.locator(locator).setChecked(true);
            System.out.println("Checked element: " + locator);
        } catch (Exception e){
            throw new RuntimeException("Cannot check element: " + locator, e);
        }
    }

    /**
     * Uncheck a checkbox
     * Only works with <input type="checkbox"> or [role=checkbox]
     */
    public void uncheck(String locator){
        try {
            page.locator(locator).setChecked(false);
            System.out.println("Unchecked element: " + locator);
        } catch (Exception e){
            throw new RuntimeException("❌ Cannot uncheck element: " + locator, e);
        }
    }

    /**
     * Select a radio button
     * Only works with <input type="radio">
     */
    public void selectRadio(String locator){
        try {
            page.locator(locator).setChecked(true);
            System.out.println("✅ Selected radio button: " + locator);
        } catch (Exception e){
            throw new RuntimeException("❌ Cannot select radio button: " + locator, e);
        }
    }

/**
     * Take full page screenshot and attach to Allure report
     * @param screenshotName name shown in allure report
     */
    public void takeFullPageScreenshot(String screenshotName) {
        try {
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );
            Allure.addAttachment(
                    screenshotName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
