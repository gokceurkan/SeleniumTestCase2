package Navbar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class NavbarMenu {
    private WebDriver driver;
    private WebDriverWait wait;
    private By navbarLocator = By.xpath("//*[@id=\"cmsmasters-scroll-top\"]/div/div/div/section/div/div[2]/div/div/div/nav");
    private By linkLocator = By.tagName("a");

    public NavbarMenu(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    public List<String> getLinkHrefs() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(navbarLocator));
        List<WebElement> linkElements = container.findElements(linkLocator);
        return linkElements.stream()
                .map(link -> link.getAttribute("href"))
                .filter(href -> href != null && !href.isEmpty())
                .collect(Collectors.toList());
    }

    public WebElement getLinkElement(String hrefValue) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"cmsmasters-scroll-top\"]/div/div/div/section/div/div[2]/div/div/div/nav//a[@href='" + hrefValue + "']")));
    }
}
