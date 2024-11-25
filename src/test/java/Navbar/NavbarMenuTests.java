package Navbar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NavbarMenuTests {

    private WebDriver driver;
    private Page page;
    private NavbarMenu navbarMenu;

    @BeforeEach
    public void setUp() {
        System.setProperty("chromeDriver", "drivers/chromedriver");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        page = new Page(driver);
        navbarMenu = new NavbarMenu(driver);

        // Test edilecek siteye git
        page.navigateTo("https://2nhaber.com/");
    }

    @Test
    public void testNavbarLinks() {
        List<String> linkHrefs = navbarMenu.getLinkHrefs();

        for (String hrefValue : linkHrefs) {
            // Href null veya boş değilse işlem yap
            assertNotNull(hrefValue, "Link href değeri null olamaz.");
            assertFalse(hrefValue.isEmpty(), "Link href değeri boş olamaz.");

            // Linke tıklayın
            WebElement linkElement = navbarMenu.getLinkElement(hrefValue);
            linkElement.click();

            // URL doğrulaması
            page.assertUrl(hrefValue);

            // Geri dön
            page.goBack();
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
