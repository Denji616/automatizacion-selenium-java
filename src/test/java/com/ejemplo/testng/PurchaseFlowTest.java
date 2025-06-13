package com.ejemplo.testng;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ejemplo.testng.utils.ConfigReader; // Importar la clase ConfigReader

public class PurchaseFlowTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize(); 
        
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        // Realizar el login antes de cada test de compra usando propiedades
        driver.get(ConfigReader.getProperty("app.url"));
        loginPage.enterUsername(ConfigReader.getProperty("app.username"));
        loginPage.enterPassword(ConfigReader.getProperty("app.password"));
        loginPage.clickLoginButton();

        // Asegurarse de que el login fue exitoso y estamos en la página de inventario
        String expectedInventoryUrl = ConfigReader.getProperty("app.url") + "inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedInventoryUrl, "No se pudo iniciar sesión correctamente.");
    }

    @Test
    public void testAddSauceLabsBackpackToCart() {
        // Añadir el Sauce Labs Backpack al carrito
        inventoryPage.clickSauceLabsBackpackAddToCart();

        // Verificar que el badge del carrito muestre "1"
        Assert.assertTrue(inventoryPage.isShoppingCartBadgeDisplayed(), "El badge del carrito no es visible.");
        Assert.assertEquals(inventoryPage.getShoppingCartBadgeText(), "1", "El número de artículos en el carrito no es '1'.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}