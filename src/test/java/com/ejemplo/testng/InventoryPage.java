package com.ejemplo.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class InventoryPage {

    private WebDriver driver;

    // Localizadores
    private By sauceLabsBackpackAddToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private By shoppingCartLink = By.id("shopping_cart_container"); // Usamos el contenedor que tiene el ID
    // Opcional, si quieres ser más específico con el contador dentro del carrito
    private By shoppingCartBadge = By.cssSelector(".shopping_cart_badge");


    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // Métodos para interactuar con los elementos
    public void clickSauceLabsBackpackAddToCart() {
        driver.findElement(sauceLabsBackpackAddToCartButton).click();
    }

    public void clickShoppingCart() {
        driver.findElement(shoppingCartLink).click();
    }

    public String getShoppingCartBadgeText() {
        // Usamos WebDriverWait para esperar que el contador del carrito aparezca/se actualice
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));
        return badge.getText();
    }

    public boolean isShoppingCartBadgeDisplayed() {
        try {
            // Espera explícita para que el badge sea visible antes de comprobar su presencia
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));
            return true;
        } catch (Exception e) {
            return false; // El badge no está visible
        }
    }
}