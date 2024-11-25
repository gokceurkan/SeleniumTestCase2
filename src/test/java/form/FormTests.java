package form;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class FormTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private FormPage formPage;

    @BeforeEach
    public void setUp() {
        System.setProperty("chromeDriver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.2ntech.com.tr/hr");

        formPage = new FormPage(driver, wait);
    }

    @Test
    public void testFormSubmission() {
        String filePath = new File("src/test/resources/test-resume.pdf").getAbsolutePath();

        formPage.fillForm(
                "Test Namee",
                LocalDate.of(1996, 9, 7),
                "11111111112",
                "05394486655",
                "test@test1.com",
                filePath
        );

        formPage.clickUndergraduate();
        formPage.clickNext();
        formPage.selectTestEngineer();
        formPage.submitForm();

        String targetUrl = "https://www.2ntech.com.tr/success";
        assertDoesNotThrow(() -> wait.until(ExpectedConditions.urlToBe(targetUrl)));

        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.equals(targetUrl)) {
            WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@role='status' and @aria-live='assertive']")
            ));
            assertTrue(notification.isDisplayed(), "Touch notification not found.");
            assertEquals("Notification Bir hata oluştu.Daha Önce Bu Programa Başvurunuz Bulunmaktadır.", notification.getText());
        } else {
            assertEquals(targetUrl, currentUrl, "The navigation did not reach the expected URL.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}