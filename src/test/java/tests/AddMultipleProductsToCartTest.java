package tests;

import base.BaseTest;
import org.example.pages.CartPage;
import org.example.pages.SearchResultsPage;
import data.SearchDataProvider;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.CSVFileManager;
import utilities.JSONFileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddMultipleProductsToCartTest extends BaseTest {
    CSVFileManager csvManager;
    JSONFileManager json;
    CartPage cartPage;
    SearchResultsPage searchPage;


    @Test(dataProvider = "searchAndBrowserScenarios", dataProviderClass = SearchDataProvider.class)
    public void verifyMultipleProductsAreAddedToCartAsGuest(String searchKeyword, String browser) {
        createDriver(browser);
        csvManager = new CSVFileManager("src/test/resources/products.csv");
        csvManager.writeProductsHeader();
        json = new JSONFileManager("src/test/resources/budgetData.json");
        double budget = json.getDouble("budget");
        double minTotalBudget = budget * json.getDouble("minTotalPercentage");
        double maxTotalBudget = budget * json.getDouble("maxTotalPercentage");
        int minProducts = json.getInt("minProducts");
        searchPage = new SearchResultsPage(driver);
        cartPage = new CartPage(driver);

        searchPage.enterSearchKeyword(searchKeyword);
        searchPage.submitSearch();
        searchPage.waitForSearchResults();
        List<WebElement> products = searchPage.getProducts();
        double selectedTotal = 0.0;
        int addedProductsCount = 0;
        List<String> selectedProductNames = new ArrayList<>();

        System.out.println("Products found: " + products.size());

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

            String productName = searchPage
                    .getProductName(product).trim();

            String rawPrice = String.valueOf(searchPage
                    .getProductPrice(product));

            if (productName.isBlank() || rawPrice.isBlank()) {
                continue;
            }
            String cleanedPrice = rawPrice
                    .replace(",", "")
                    .replaceAll("[^0-9.]", "");

            if (!cleanedPrice.matches("[0-9]+(?:\\.[0-9]+)?")) {
                System.out.println(
                        "Skipping invalid price: " + rawPrice
                );
                continue;
            }

            double price = Double.parseDouble(cleanedPrice);
            if (selectedTotal + price > maxTotalBudget) {
                continue;
            }
            if (selectedTotal >= minTotalBudget
                    && addedProductsCount >= minProducts) {
                break;
            }

            searchPage.addProductToCart(product);

            selectedTotal += price;
            addedProductsCount++;
            selectedProductNames.add(productName);

            csvManager.writeProduct(productName, price, 1);

            System.out.println("Clicked Add to Cart: " + productName);
            System.out.println("Product price: " + price);
            System.out.println("Selected total: " + selectedTotal);
        }

        softAssert.assertTrue(
                addedProductsCount >= minProducts,
                "Expected at least " + minProducts
                        + " products, but selected " + addedProductsCount
        );

        softAssert.assertTrue(
                selectedTotal >= minTotalBudget && selectedTotal <= maxTotalBudget,
                String.format(
                        Locale.US,
                        "Selected total %.2f is outside the range %.2f-%.2f",
                        selectedTotal,
                        minTotalBudget,
                        maxTotalBudget
                )
        );


        cartPage.openCart();

        String pageText = cartPage.getPageText();

        softAssert.assertFalse(
                pageText.contains("Something went wrong!"),
                "Noon displayed 'Something went wrong!' on the cart page."
        );

        List<WebElement> cartItems = cartPage.getCartItems();

        softAssert.assertFalse(
                cartItems.isEmpty(),
                "Cart items were not found. Check the cart page and locators."
        );

        List<String> cartProductNames = new ArrayList<>();

        for (WebElement cartItem : cartItems) {

            String cartName = cartPage.getProductName(cartItem);
            String cartPriceText = cartPage.getProductPrice(cartItem);
            String quantityText = cartPage.getProductQuantity(cartItem);

            cartProductNames.add(normalizeName(cartName));

            String cleanedCartPrice = cartPriceText
                    .replace(",", "")
                    .replaceAll("[^0-9.]", "");

            softAssert.assertTrue(
                    cleanedCartPrice.matches("[0-9]+(?:\\.[0-9]+)?"),
                    "Invalid cart price for " + cartName
                            + ": " + cartPriceText
            );

            softAssert.assertTrue(
                    quantityText != null && quantityText.matches("\\d+"),
                    "Invalid quantity for " + cartName
            );

            int quantity = Integer.parseInt(quantityText);

            softAssert.assertTrue(
                    quantity > 0,
                    "Quantity must be greater than zero for " + cartName
            );

            System.out.println(
                    "Cart item: " + cartName
                            + " | Price: " + cartPriceText
                            + " | Quantity: " + quantity
            );
        }

        for (String selectedName : selectedProductNames) {

            String normalizedSelectedName = normalizeName(selectedName);

            boolean found = cartProductNames.stream()
                    .anyMatch(cartName ->
                            cartName.equals(normalizedSelectedName)
                                    || cartName.contains(normalizedSelectedName)
                                    || normalizedSelectedName.contains(cartName)
                    );

            softAssert.assertTrue(
                    found,
                    "Selected product was not found in cart: " + selectedName
            );
        }

        System.out.println("Search scenario: " + searchKeyword);
        System.out.println("Products selected: " + addedProductsCount);
        System.out.println("Selected total: " + selectedTotal);
        System.out.println("Cart verification completed.");

        softAssert.assertAll();
    }

    private String normalizeName(String name) {
        return name.toLowerCase(Locale.ROOT)
                .replaceAll("[\\p{P}\\p{S}]", " ")
                .replaceAll("\\s+", " ")
                .trim();


    }

}
