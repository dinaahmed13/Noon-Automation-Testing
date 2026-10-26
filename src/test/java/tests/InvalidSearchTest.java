package tests;


import base.BaseTest;
import data.BrowserDataProvider;
import org.example.pages.InvalidSearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.JSONFileManager;

public class InvalidSearchTest extends BaseTest {
    InvalidSearchPage invalidSearchPage;
    JSONFileManager json;
    @Test(dataProvider = "browsers", dataProviderClass = BrowserDataProvider.class)
    public void testInvalidSearch(String browser) {
        createDriver(browser);
        invalidSearchPage = new InvalidSearchPage(driver);
        json = new JSONFileManager("src/test/resources/invalidSearchKeywords.json");
        String invalidKeyword = json.getString("invalidKeyword");
        invalidSearchPage.enterInvalidSearchKeyword(invalidKeyword);

        Assert.assertTrue(invalidSearchPage.getErrorImage().isDisplayed());
        Assert.assertEquals(invalidSearchPage.getErrorTitle().getText(),
                "حصل خطأ!");
    }
}
