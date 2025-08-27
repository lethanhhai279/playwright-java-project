package com.example.playwright.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.imageio.ImageIO;
import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.*;


public class Utils {
    public static void takeWindowsScreenshot() {
        String stepId = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(stepId, new StepResult().setName("Windows Screenshot"));

        try {
            // === Định nghĩa path lưu file screenshot ===
            Path screenshotDir = Paths.get("target", "screenshots");
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            String imageName = "screenshot-" + System.currentTimeMillis() + ".png";
            Path screenshotPath = screenshotDir.resolve(imageName);

            // === Capture full screen ===
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Robot robot = new Robot();
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            // === Save ra file ===
            File outputFile = screenshotPath.toFile();
            ImageIO.write(screenFullImage, "png", outputFile);

            // === Attach vào Allure ===
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(screenFullImage, "png", outputStream);
            Allure.addAttachment(
                    imageName.replace(".png", ""),
                    new ByteArrayInputStream(outputStream.toByteArray())
            );

            // Allure.description(String.format("Windows Screenshot was taken - [%s]", screenshotPath));

        } catch (AWTException | IOException ex) {
            Allure.description("Take Windows Screenshot failed: " + ex.getMessage());
        } finally {
            Allure.getLifecycle().stopStep(stepId);
        }
    }
}
