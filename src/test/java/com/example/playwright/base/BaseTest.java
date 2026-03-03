package com.example.playwright.base;

import com.example.playwright.core.config.ConfigFactory;
import com.example.playwright.core.drivers.DriverManager;
import com.example.playwright.core.utils.PlaywrightActions;
import com.example.playwright.pages.LoginPage;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected Page page;
    protected LoginPage loginPage;
    protected PlaywrightActions WebUI;

    // === CLEAN REPORTS & LOGS TRƯỚC KHI CHẠY ===
    @BeforeSuite
    public void cleanAndPrepareReport() {
        try {
            Path resultsDir = Paths.get("target/allure-results");
            Path reportsDir = Paths.get("target/allure-report");
            Path logsDir = Paths.get("target/logs");

            deleteIfExists(resultsDir);
            deleteIfExists(reportsDir);
            deleteIfExists(logsDir);

            Files.createDirectories(resultsDir);
            Files.createDirectories(logsDir);

            System.out.println("Cleaned old reports & logs!");
        } catch (IOException e) {
            System.err.println("Failed to clean reports/logs: " + e.getMessage());
        }
    }

    // === INIT DRIVER & OPEN BASE URL ===
    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        page = DriverManager.getPage();

        String url = ConfigFactory.getConfig().getProperty("baseUrl");
        Allure.step("Navigate to: " + url);
        page.navigate(url);

        WebUI = new PlaywrightActions(page);
        loginPage = new LoginPage(WebUI);
        
    }

    // === CLOSE DRIVER ===
    @AfterMethod
    public void tearDown() {
        DriverManager.closeDriver();
    }

    // === GENERATE + SAVE HISTORY REPORT ===
    @AfterSuite
    public void generateReport() {
        try {
            String resultsDir = "target/allure-results";
            String reportDir = "target/allure-report";

            // === Generate Allure report ===
            String allureCmd = "C:\\Program Files\\Allure\\allure-2.24.0\\allure-2.24.0\\bin\\allure.bat"; // hoặc thay = "C:\\allure\\bin\\allure.bat" nếu không có trong PATH
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd", "/c", allureCmd, "generate", resultsDir, "-o", reportDir, "--clean"
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Allure report generated at: " + reportDir);
            } else {
                System.err.println("Allure report generation failed! Exit code: " + exitCode);
                return;
            }

            // === Copy report sang history với timestamp ===
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            Path sourceDir = Paths.get(reportDir);
            Path historyDir = Paths.get("reports/history/allure-report-" + timestamp);

            Files.createDirectories(historyDir);

            Files.walk(sourceDir).forEach(source -> {
                try {
                    Path destination = historyDir.resolve(sourceDir.relativize(source));
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(destination);
                    } else {
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Historical report saved at: " + historyDir);

        } catch (Exception e) {
            System.err.println("Failed to generate Allure report: " + e.getMessage());
        }
    }


    // === Helper: delete folder nếu tồn tại ===
    private void deleteIfExists(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted((a, b) -> b.compareTo(a)) // delete files trước, folder sau
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
