package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class SearchResultsPage extends BasePage {
    private static final Logger log = LogManager.getLogger(SearchResultsPage.class);
    private final By searchBox = By.id("search-input");
    private final By productsLocator = By.cssSelector("div[data-qa='plp-product-box']");
    //private final By nameLocator = By.cssSelector("h2[data-qa='plp-product-box-name']");
    private final By priceLocator = By.cssSelector("div[class*='sellingPrice'] strong[class*='amount']");
    private final By addToCartLocator = By.cssSelector("button[class*='atcCta']");
    private final By freeShippingLocator = By.xpath(".//div[contains(@class,'nudgeText') and normalize-space()='توصيل مجاني']");
    private final By nameLocator = By.xpath("//h2[@class='title' and @data-qa='plp-product-box-name']");

    public SearchResultsPage(WebDriver driver) {
       super(driver);
    }

    public void enterSearchKeyword(String keyword) {
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        search.click();
        search.clear();
        search.sendKeys(keyword);
    }
    public void submitSearch() {
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        search.sendKeys(Keys.ENTER);
    }
    public void waitForSearchResults() {
        wait.until(
                ExpectedConditions.urlContains("search")
        );

        wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(productsLocator));
    }

    public List<WebElement> getProducts() {
        return findElements(productsLocator);
    }
    public String getProductName(WebElement product) {
        return product.findElement(nameLocator)
                .getAttribute("title")
                .trim();
    }

    public double getProductPrice(WebElement product) {
        String priceText = product.findElement(priceLocator)
                .getText()
                .replace(",", "")
                .trim();

        return Double.parseDouble(priceText);
    }

    public void addProductToCart(WebElement product) {
        WebElement button = product.findElement(addToCartLocator);
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    public boolean isFreeShipping(WebElement product) {
        return !product.findElements(freeShippingLocator ).isEmpty();
    }
}