package tests;

import base.BaseTest;
import data.SearchDataProvider;
import org.example.pages.CartPage;
import org.example.pages.SearchResultsPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.CSVFileManager;
import utilities.JSONFileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddMultipleFreeShippingProductsTest extends BaseTest {
    CSVFileManager csvManager;
    JSONFileManager json;
    CartPage cartPage;
    SearchResultsPage searchPage;

    @Test(dataProvider = "freeShippingSearchAndBrowserScenarios", dataProviderClass = SearchDataProvider.class)
    public void verifyFreeShippingProductsInCartAsGuest(String antherSearchKeyword, String browser) {
        createDriver(browser);
        csvManager = new CSVFileManager("src/test/resources/freeShippingProducts.csv");
        csvManager.writeProductsHeader();
        json = new JSONFileManager("src/test/resources/budgetData.json");

        double budget = json.getDouble("budget");
        double minTotalBudget = budget * json.getDouble("minTotalPercentage");
        double maxTotalBudget = budget * json.getDouble("maxTotalPercentage");
        int minProducts = Math.max(4, json.getInt("minProducts"));

        searchPage = new SearchResultsPage(driver);
        cartPage = new CartPage(driver);

        SoftAssert softAssert = new SoftAssert();


        searchPage.enterSearchKeyword(antherSearchKeyword);
        searchPage.submitSearch();
        searchPage.waitForSearchResults();

        List<WebElement> products = searchPage.getProducts();

        double selectedTotal = 0.0;
        int addedProductsCount = 0;

        List<String> selectedProductNames = new ArrayList<>();
        Map<String, Double> selectedProductPrices = new HashMap<>();

        for (int i = 0; i < products.size(); i++) {

            if (selectedTotal >= minTotalBudget
                    && addedProductsCount >= minProducts) {
                break;
            }

            products = searchPage.getProducts();

            if (i >= products.size()) {
                break;
            }

            WebElement product = products.get(i);

            String productName =
                    searchPage.getProductName(product).trim();

            double price =
                    searchPage.getProductPrice(product);

            if (productName.isBlank() || price <= 0) {
                continue;
            }
            if (!searchPage.isFreeShipping(product)) {
                System.out.println(
                        "Skipped - no free shipping: " + productName
                );
                continue;
            }


            if (selectedTotal + price > maxTotalBudget) {
                continue;
            }


            if (selectedProductPrices.containsKey(productName)) {
                continue;
            }


            searchPage.addProductToCart(product);

            selectedTotal += price;
            addedProductsCount++;

            selectedProductNames.add(productName);
            selectedProductPrices.put(productName, price);

            csvManager.writeProduct(productName, price, 1);

            System.out.println(
                    "Added free-shipping product: " + productName
            );
            System.out.println("Price: " + price);
            System.out.println("Selected total: " + selectedTotal);
        }


        softAssert.assertTrue(
                addedProductsCount >= minProducts,
                "Expected at least " + minProducts
                        + " free-shipping products, but selected "
                        + addedProductsCount
        );

        softAssert.assertTrue(
                selectedTotal >= minTotalBudget
                        && selectedTotal <= maxTotalBudget,
                String.format(
                        Locale.US,
                        "Selected total %.2f is outside budget range %.2f-%.2f",
                        selectedTotal,
                        minTotalBudget,
                        maxTotalBudget
                )
        );


        cartPage.openCart();

        String pageText = cartPage.getPageText();

        softAssert.assertFalse(
                pageText.contains("Something went wrong!"),
                "Noon displayed an error on the cart page."
        );


        List<WebElement> cartItems = cartPage.getCartItems();

        softAssert.assertFalse(
                cartItems.isEmpty(),
                "No cart items were found."
        );

        List<String> cartProductNames = new ArrayList<>();
        Map<String, Double> cartProductPrices = new HashMap<>();

        for (WebElement cartItem : cartItems) {

            String cartName =
                    cartPage.getProductName(cartItem).trim();

            String cartPriceText =
                    cartPage.getProductPrice(cartItem);

            String quantityText =
                    cartPage.getProductQuantity(cartItem);

            String normalizedCartName = normalizeName(cartName);
            cartProductNames.add(normalizedCartName);

            String cleanedPrice = cartPriceText
                    .replace(",", "")
                    .replaceAll("[^0-9.]", "");

            boolean validPrice =
                    cleanedPrice.matches("[0-9]+(?:\\.[0-9]+)?");

            softAssert.assertTrue(
                    validPrice,
                    "Invalid cart price for " + cartName
                            + ": " + cartPriceText
            );

            if (validPrice) {
                double cartPrice = Double.parseDouble(cleanedPrice);
                cartProductPrices.put(normalizedCartName, cartPrice);
            }

            boolean validQuantity =
                    quantityText != null
                            && quantityText.trim().matches("\\d+");

            softAssert.assertTrue(
                    validQuantity,
                    "Invalid quantity for " + cartName
            );

            if (validQuantity) {
                int quantity = Integer.parseInt(quantityText.trim());

                softAssert.assertEquals(
                        quantity,
                        1,
                        "Quantity should be 1 for " + cartName
                );
            }


            softAssert.assertTrue(
                    cartPage.isFreeShipping(cartItem),
                    "Free shipping was not confirmed for " + cartName
            );

            System.out.println(
                    "Cart product: " + cartName
                            + " | Price: " + cartPriceText
                            + " | Quantity: " + quantityText
            );
        }

        for (String selectedName : selectedProductNames) {

            String normalizedSelectedName = normalizeName(selectedName);

            String matchingCartName = cartProductNames.stream()
                    .filter(cartName ->
                            cartName.equals(normalizedSelectedName)
                                    || cartName.contains(normalizedSelectedName)
                                    || normalizedSelectedName.contains(cartName))
                    .findFirst()
                    .orElse(null);

            softAssert.assertNotNull(
                    matchingCartName,
                    "Selected product was not found in cart: "
                            + selectedName
            );

            if (matchingCartName != null) {
                Double expectedPrice =
                        selectedProductPrices.get(selectedName);

                Double actualPrice =
                        cartProductPrices.get(matchingCartName);

                if (expectedPrice != null && actualPrice != null) {
                    softAssert.assertEquals(
                            actualPrice,
                            expectedPrice,
                            0.01,
                            "Price mismatch for " + selectedName
                    );
                }
            }
        }

        System.out.println("Selected products: " + addedProductsCount);
        System.out.println("Selected total: " + selectedTotal);
        System.out.println("Free-shipping cart verification completed.");

        softAssert.assertAll();
    }

    private String normalizeName(String name) {
        return name.toLowerCase(Locale.ROOT)
                .replaceAll("[\\p{P}\\p{S}]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}