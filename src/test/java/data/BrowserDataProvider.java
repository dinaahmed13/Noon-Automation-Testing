package data;

import org.testng.annotations.DataProvider;

public class BrowserDataProvider {

    @DataProvider(name = "browsers", parallel = false)
    public static Object[][] browsers() {

        return new Object[][] {
                {"chrome"},
                {"firefox"},
                {"edge"}
        };
    }
}
