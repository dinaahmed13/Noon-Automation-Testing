package tests;


import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BrowseAndFilterProductsTest extends BaseTest {

    @Test
    public void verifyNoonHomePageOpens() {
        Assert.assertTrue(driver.getCurrentUrl().contains("noon.com"), "Noon website did not open successfully");
    }

}
