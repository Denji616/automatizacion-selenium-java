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

import com.ejemplo.testng.utils.ConfigReader; // Importar la clase ConfigReader

import java.time.Duration;

public class PruebaLoginFuncional {

    private WebDriver driver;
    private LoginPageFuncional loginPage;

    @BeforeMethod
    public void setUp() {
        // System.setProperty("webdriver.gecko.driver", "C:\\\\Program Files\\\\Mozilla Firefox\\\\geckodriver.exe"); // Esto no es necesario si usas WebDriverManager o en CI
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPageFuncional(driver); 

        // Obtener la URL del archivo de propiedades
        driver.get(ConfigReader.getProperty("app.url")); // Asegúrate de que el env para esta URL esté configurado (ej. -Denv=example)
    }

    @Test
    public void testLoginFuncional() {
        // Obtener credenciales del archivo de propiedades
        loginPage.enterUsername(ConfigReader.getProperty("app.username"));
        loginPage.enterPassword(ConfigReader.getProperty("app.password"));

        String expectedWelcomeMessage = ConfigReader.getProperty("app.welcomeMessage");

        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensajeBienvenidaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeBienvenida")));
        Assert.assertTrue(mensajeBienvenidaElement.getText().contains(expectedWelcomeMessage), "El mensaje de bienvenida no contiene el texto esperado.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}