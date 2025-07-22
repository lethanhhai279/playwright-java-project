package com.example.playwright.tests;
import org.testng.annotations.Test;

import com.example.playwright.base.BaseTest;
import com.microsoft.playwright.*;

public class PracticeTest extends BaseTest{
    @Test
    public void testReadDataTable(){
        page.click("//div[@id='footerPanel']//a[normalize-space()='Services']");
        page.waitForSelector("//table[1]/tbody/tr");
        Locator rows = page.locator("//table[1]/tbody/tr");
        int rowCount = rows.count();
        System.out.println("Total rows: " + rowCount);
        for (int i = 0; i < rowCount; i++) {
            String nameTitle = rows.nth(i).locator("td").nth(0).innerText();
            String content = rows.nth(i).locator("td").nth(1).innerText();
            System.out.println("Row " + (i + 1) + " Title is: " + nameTitle + "\nAnd content: \n" + content);
        }
        page.waitForTimeout(3000);
    }
}
