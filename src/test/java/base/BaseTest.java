package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void setUp(){
        ChromeOptions chromeOptions =new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        driver=new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.noon.com/");
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }
/*
    @AfterClass
    public void tearDown() {
        driver.quit();
    }*/
}

