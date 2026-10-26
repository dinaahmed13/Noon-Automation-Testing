package org.example.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ProductListingPage extends BasePage {
    private static final Logger log = LogManager.getLogger(ProductListingPage.class);
    private final By electronicsMenu = By.xpath("//li[@data-qa='btn_main_menu_الإلكترونيات']");
    private final By samsungLink = By.cssSelector("a[href='/egypt-ar/electronics-and-mobiles/samsung/']");
    private final By priceFilter = By.xpath("//div[@data-qa='السعر']");
    private final By minPriceInput = By.xpath("//input[@data-qa='filter-min-input']");
    private final By maxPriceInput = By.xpath("//input[@data-qa='filter-max-input']");
    private final By productsLocator = By.cssSelector("div[data-qa='plp-product-box']");
    private final By priceLocator = By.cssSelector("div[class*='sellingPrice'] strong[class*='amount']");
    private final By applyButton = By.xpath("//button[@type='submit']");
    private final By ratingSliderLocator = By.xpath(".//input[contains(@class,'slider')]");
    private final By ratingLocator = By.cssSelector("div[class*='textCtr']");
    private final By productRatingLocator = By.cssSelector("div[class*='rating']");
    private final By ratingTitle = By.xpath("//div[@data-qa='تقيم المنتج']");
    By productNameLocator = By.xpath(
            "//h2[@class='_title_i1yaq_19' and @data-qa='plp-product-box-name']"
    );
    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public void openElectronics() {
        findElement(electronicsMenu).click();
    }

    public void openSamsungCategory() {
        clickWhenClickable(samsungLink);
        wait.until(ExpectedConditions.urlContains("/electronics-and-mobiles/samsung/"));
    }


    public void openPriceFilter() {
        findElement(priceFilter).click();
    }

    public void enterMinPrice(int minPrice) {
        WebElement minInput = findElement(minPriceInput);
        minInput.clear();
        minInput.sendKeys(String.valueOf(minPrice));
    }

    public void enterMaxPrice(int maxPrice) {
        WebElement maxInput = findElement(maxPriceInput);
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(maxPrice));
    }

    public void clickApplyPriceFilter() {
        findElement(applyButton).click();
    }

    public int getProductCount() {
        return driver.findElements(productsLocator).size();
    }


    public List<Double> getProductPrices() {

        List<Double> prices = new ArrayList<>();

        int productCount = driver.findElements(productsLocator).size();

        for (int i = 0; i < productCount; i++) {
            try {
                List<WebElement> currentProducts =
                        driver.findElements(productsLocator);

                if (i >= currentProducts.size()) {
                    break;
                }

                WebElement priceElement =
                        currentProducts.get(i).findElement(priceLocator);

                String priceText = priceElement.getText()
                        .replace(",", "")
                        .trim();

                prices.add(Double.parseDouble(priceText));

            } catch (StaleElementReferenceException e) {
                System.out.println(
                        "Product at index " + i + " changed; skipping it."
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid product price at index " + i
                );
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println(
                        "Price element not found for product at index " + i
                );
            }
        }

        return prices;
    }


    public boolean verifyPricesInRange(
            int minPrice, int maxPrice) {

        List<Double> prices = getProductPrices();

        if (prices.isEmpty()) {
            return false;
        }

        for (double price : prices) {
            if (price < minPrice || price > maxPrice) {
                return false;
            }
        }

        return true;
    }

    public void openRatingFilter() {
        clickWhenClickable(ratingTitle);
    }

    public WebElement getRatingSlider() {
        return findElement(ratingSliderLocator);
    }

    public List<Double> getProductRatings() {
        List<Double> ratings = new ArrayList<>();

        List<WebElement> products =
                driver.findElements(productsLocator);

        for (WebElement product : products) {
            List<WebElement> ratingElements =
                    product.findElements(productRatingLocator);

            if (ratingElements.isEmpty()) {
                continue;
            }

            String ratingText =
                    ratingElements.get(0).getText().trim();

            try {
                ratings.add(Double.parseDouble(ratingText));
            } catch (NumberFormatException e) {
                System.out.println(
                        "Unable to parse rating: " + ratingText
                );
            }
        }

        return ratings;
    }

    public void adjustRatingSlider() throws InterruptedException {
        WebElement slider = findElement(ratingSliderLocator);

        new Actions(driver)
                .moveToElement(slider)
                .click()
                .sendKeys(
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT,
                        Keys.ARROW_LEFT
                )
                .perform();
          Thread.sleep(1000);
    }


    public List<WebElement> getProductRatingsElements() {

        List<WebElement> ratings = new ArrayList<>();

        List<WebElement> products =
                driver.findElements(productsLocator);

        for (int i = 0; i < products.size(); i++) {
            try {
                List<WebElement> currentProducts =
                        driver.findElements(productsLocator);

                if (i >= currentProducts.size()) {
                    break;
                }

                List<WebElement> productRatings =
                        currentProducts.get(i).findElements(ratingLocator);

                if (productRatings.isEmpty()) {
                    System.out.println(
                            "Rating not found for product at index " + i
                    );
                    continue;
                }

                ratings.add(productRatings.get(0));

            } catch (StaleElementReferenceException e) {
                System.out.println(
                        "Product at index " + i + " changed; skipping it."
                );
            }
        }

        return ratings;
    }


}

