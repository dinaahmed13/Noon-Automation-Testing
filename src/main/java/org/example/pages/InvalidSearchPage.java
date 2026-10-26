package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class InvalidSearchPage extends BasePage {
    private static final Logger log = LogManager.getLogger(InvalidSearchPage.class);
    private final By searchInputField = By.id("search-input");
    private  final  By errorImage=By.xpath("//img[@alt='حصل خطأ!']");
    private  final  By errorTitle=By.xpath("//h3[text()='حصل خطأ!']");

    public InvalidSearchPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getSearchInputField() {
        return findElement(searchInputField);
    }
    public WebElement getErrorImage() {
        return findElement(errorImage);
    }
    public WebElement getErrorTitle() {
        return findElement(errorTitle);
    }
    public void enterInvalidSearchKeyword(String keyword) {
        getSearchInputField().click();
        getSearchInputField().sendKeys(keyword);
        getSearchInputField().sendKeys(Keys.ENTER);
    }




}
