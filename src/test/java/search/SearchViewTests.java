package search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchViewTests {
    private WebDriver driver;
    private SearchPage searchPage;

    @BeforeEach
    public void setUp() {
        System.setProperty("chromeDriver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Sayfa nesnesini başlat
        searchPage = new SearchPage(driver);

        // Test edilecek siteye git
        driver.get("https://2nhaber.com/");
    }

    @ParameterizedTest
    @ValueSource(ints = {3}) // Örneğin, 8. makale için
    public void testSearchBarAndArticleNavigation(int articleIndex) {
        searchPage.openSearchBar();
        searchPage.performSearch("İstanbul");
        searchPage.clickArticleByIndex(articleIndex);

        String hrefValue = searchPage.getHrefValue();

        System.out.println("Navigated to: " + hrefValue);

        // URL'nin doğru olup olmadığını kontrol et
        assertEquals(hrefValue, driver.getCurrentUrl(), "Beklenen URL ile eşleşmiyor!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
