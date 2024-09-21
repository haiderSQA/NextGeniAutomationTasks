package org.example;

import io.cucumber.java.PendingException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static <CaptchaSolver> void main(String[] args) {

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();

            driver.get("https://www.amazon.com");
            driver.manage().deleteAllCookies();

            // Locate the image element
            WebElement imageElement = driver.findElement(By.xpath("//div[@class='a-row a-text-center']//img"));

            // Get the 'alt' attribute of the image
            String imageAltText = imageElement.getAttribute("alt");

            // Print the alt text
            System.out.println("Image Alt Text: " + imageAltText);

            // Close the browser
            driver.quit();
        }
    }
