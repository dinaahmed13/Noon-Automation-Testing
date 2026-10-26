package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public SoftAssert softAssert;

    protected WebDriver createDriver(String browser) {

        if (browser == null) {
            throw new IllegalArgumentException("Browser cannot be null");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions =new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                driver=new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }
        driver.manage().window().maximize();
        driver.navigate().to("https://www.noon.com/");
        wait=new WebDriverWait(driver, Duration.ofSeconds(100));
        softAssert = new SoftAssert();

        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
