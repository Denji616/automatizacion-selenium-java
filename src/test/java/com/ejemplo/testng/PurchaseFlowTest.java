package com.ejemplo.testng;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PurchaseFlowTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize(); // Maximizar la ventana para asegurar la visibilidad
        
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        // Realizar el login antes de cada test de compra
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        // Asegurarse de que el login fue exitoso y estamos en la página de inventario
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "No se pudo iniciar sesión correctamente.");
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
