package tests;


import base.BaseTest;
import data.BrowserDataProvider;
import org.example.pages.ProductListingPage;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import utilities.CSVFileManager;

import java.util.List;

public class BrowseAndFilterProductsTest extends BaseTest {
    ProductListingPage productListingPage;
    CSVFileManager csvManager;

    @Test(dataProvider = "browsers", dataProviderClass = BrowserDataProvider.class)
    public void verifySamsungProductsWithinPriceRangeAndRating(String browser) throws InterruptedException {
        createDriver(browser);
        productListingPage = new ProductListingPage(driver);
        String csvPath = "src/test/resources/filterPrice.csv";
        csvManager = new CSVFileManager(csvPath);

        int[] priceRange = csvManager.readPriceRangeFromCSV();

        int minPrice = priceRange[0];
        int maxPrice = priceRange[1];


        productListingPage.openElectronics();
        productListingPage.openSamsungCategory();
        productListingPage.openPriceFilter();
        productListingPage.enterMinPrice(minPrice);
        productListingPage.enterMaxPrice(maxPrice);
        productListingPage.clickApplyPriceFilter();
        int productCount = productListingPage.getProductCount();
        System.out.println("Number of products: " + productCount);
        List<Double> prices =
                productListingPage.getProductPrices();

        softAssert.assertFalse(
                prices.isEmpty(), "No product prices were found.");

        softAssert.assertTrue(
                productListingPage.verifyPricesInRange(minPrice, maxPrice),
                "Some products are outside the selected price range."
        );
        productListingPage.openRatingFilter();
        productListingPage.adjustRatingSlider();

        List<WebElement> ratings =
                productListingPage.getProductRatingsElements();

        softAssert.assertFalse(
                ratings.isEmpty(),
                "No product ratings were found."
        );

        for (WebElement ratingElement : ratings) {
            String ratingText = ratingElement.getText().trim();

            try {
                double rating = Double.parseDouble(ratingText);

                softAssert.assertTrue(
                        rating > 4.0,
                        "Product rating is not above 4: " + rating
                );
            } catch (NumberFormatException e) {
                softAssert.fail(
                        "Could not parse product rating: " + ratingText
                );
            }
        }

        softAssert.assertAll();

    }
}