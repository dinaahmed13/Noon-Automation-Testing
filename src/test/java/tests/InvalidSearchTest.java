package tests;


import base.BaseTest;
import org.example.pages.InvalidSearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.JSONFileManager;

public class InvalidSearchTest extends BaseTest {
    InvalidSearchPage invalidSearchPage;
    JSONFileManager json;
    @Test
    public void testInvalidSearch() {
        invalidSearchPage = new InvalidSearchPage(driver);
        json = new JSONFileManager("src/test/resources/invalidSearchKeywords.json");
        String invalidKeyword = json.getString("invalidKeyword");
        invalidSearchPage.enterInvalidSearchKeyword(invalidKeyword);

        Assert.assertTrue(invalidSearchPage.getErrorImage().isDisplayed());
        Assert.assertEquals(invalidSearchPage.getErrorTitle().getText(),
                "حصل خطأ!");
    }
}
