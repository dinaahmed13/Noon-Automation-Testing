package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage  extends BasePage {
    private static final Logger log = LogManager.getLogger(CartPage.class);
    private final By cartLink = By.xpath("//span[normalize-space()='عربة التسوق']");
    private final By cartItemsLocator = By.cssSelector("div[class*='itemSection']");
    private final By cartNameLocator = By.cssSelector("a[class*='titleLink']");
    private final By cartPriceLocator = By.cssSelector("[data-qa='item-unit-price']");
    private final By quantityLocator = By.cssSelector("input[data-qa='item-quantity-input']");
    private final By freeShippingLocator = By.xpath(".//span[.//small[normalize-space()='توصيل مجاني']]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();

        wait.until(ExpectedConditions.urlContains("/cart"));
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItemsLocator);
    }

    public String getProductName(WebElement item) {
        return item.findElement(cartNameLocator)
                .getText().trim();
    }
    public String getProductPrice(WebElement cartItem) {
        return cartItem.findElement(cartPriceLocator).getText().trim();
    }

    public String getProductQuantity(WebElement cartItem) {
        return cartItem.findElement(quantityLocator).getAttribute("value");
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body"))
                .getText();
    }
    public boolean isFreeShipping(WebElement cartItem) {
        return !cartItem.findElements(freeShippingLocator).isEmpty();
    }
}