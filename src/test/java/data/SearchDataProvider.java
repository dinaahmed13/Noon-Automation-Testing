package data;

import org.testng.annotations.DataProvider;

public class SearchDataProvider {
    @DataProvider(name = "searchAndBrowserScenarios")
    public static Object[][] searchAndBrowserScenarios() {

        return new Object[][] {
                {"Headphones", "chrome"}
        };
    }
    @DataProvider(name = "freeShippingSearchAndBrowserScenarios")
    public static Object[][] freeShippingSearchAndBrowserScenarios() {
        return new Object[][] {
                {"Video Games", "chrome"}
        };
    }
}
