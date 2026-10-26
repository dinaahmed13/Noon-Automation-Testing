package data;

import org.testng.annotations.DataProvider;

public class SearchDataProvider {
    @DataProvider(name = "searchScenarios")
    public static Object[][] searchScenarios() {
        return new Object[][] {
                {"Headphones"}
        };
    }
    @DataProvider(name = "antherSearchScenarios")
    public static Object[][] antherSearchScenarios() {
        return new Object[][] {
                {"Video Games"}
        };
    }
}
