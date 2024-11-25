package Navbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Page {

    private WebDriver driver;
    private WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void goBack() {
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(driver.getCurrentUrl()));
    }

    public void assertUrl(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL eşleşmiyor.");
    }
}
