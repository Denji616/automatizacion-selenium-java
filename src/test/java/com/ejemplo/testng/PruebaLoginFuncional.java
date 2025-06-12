package com.ejemplo.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class PruebaLoginFuncional {

    private WebDriver driver;
    private LoginPageFuncional loginPage;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\\\Program Files\\\\Mozilla Firefox\\\\geckodriver.exe");
        driver = new FirefoxDriver();
        loginPage = new LoginPageFuncional(driver); // Inicializamos LoginPageFuncional
        driver.get("https://example.com/login");
    }

    @Test
    public void testLoginFuncional() {
        loginPage.enterUsername("admin");
        loginPage.enterPassword("1234");
        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensajeBienvenidaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeBienvenida")));
        Assert.assertTrue(mensajeBienvenidaElement.getText().contains("Bienvenido"), "El mensaje de bienvenida no contiene el texto esperado.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}