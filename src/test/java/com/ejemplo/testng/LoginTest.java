package com.ejemplo.testng;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ejemplo.testng.utils.ConfigReader; // Importar la clase ConfigReader

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize(); 

        loginPage = new LoginPage(driver); 
        driver.get(ConfigReader.getProperty("app.url")); // Usa ConfigReader
    }

    @Test
    public void testLoginExitoso() {
        loginPage.enterUsername(ConfigReader.getProperty("app.username")); // Usa ConfigReader
        loginPage.enterPassword(ConfigReader.getProperty("app.password")); // Usa ConfigReader
        loginPage.clickLoginButton();

        String urlEsperada = ConfigReader.getProperty("app.url") + "inventory.html"; 
        Assert.assertEquals(driver.getCurrentUrl(), urlEsperada, "La URL después del login no es la esperada.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}