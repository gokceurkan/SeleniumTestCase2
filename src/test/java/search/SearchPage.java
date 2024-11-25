package search;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By searchButtonLocator = By.xpath("//*[@id=\"cmsmasters-scroll-top\"]/div/div/div/section/div/div[3]/div/div[2]/div/div/div[2]/div");
    private final By searchBarLocator = By.xpath("//*[@id=\"cmsmasters-scroll-top\"]/div/div/div/section/div/div[3]/div/div[2]/div/div/div[1]/div/form/div/input");
    private final By resultsContainerLocator = By.xpath("//*[@id=\"cmsmasters_body\"]/div[2]/div/div/section[2]/div/div[1]/div/div/div/div/div[2]/div/div");
    private final By articleTagLocator = By.tagName("article");
    private final By articleLinkLocator = By.tagName("a");

    private String hrefValue;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openSearchBar() {
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
        searchButton.click();
    }

    public void performSearch(String keyword) {
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBarLocator));
        searchBar.sendKeys(keyword);
        searchBar.submit();
    }

    public List<WebElement> getSearchResults() {
        WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(resultsContainerLocator));
        return resultsContainer.findElements(articleTagLocator);
    }

    public void clickArticleByIndex(int index) {
        List<WebElement> articles = getSearchResults();
        WebElement selectedArticle = articles.get(index - 1); // 1-based index
        WebElement articleLink = selectedArticle.findElement(articleLinkLocator);

        scrollToElement(articleLink);
        wait.until(ExpectedConditions.elementToBeClickable(articleLink));

        this.hrefValue = articleLink.getAttribute("href");
        clickToElement(articleLink);

        wait.until(ExpectedConditions.urlToBe(this.hrefValue));
    }

    public String getHrefValue() {
        return hrefValue; // Getter for hrefValue
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void clickToElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}
