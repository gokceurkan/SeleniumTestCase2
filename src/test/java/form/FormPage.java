package form;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    // Locators
    private By formLocator = By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/form");
    private By nameLocator = By.id("name");
    private By birthLocator = By.id("birth");
    private By idLocator = By.id("tcKimlik");
    private By phoneLocator = By.id("phone");
    private By emailLocator = By.id("email");
    private By cvLocator = By.id("cv_field");
    private By undergraduateButtonLocator = By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/form/div[5]/div/button[2]");
    private By nextButtonLocator = By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/form/div[7]/button");
    private By testEngineerButtonLocator = By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div[3]/div[1]/div/div[2]");
    private By submitButtonLocator = By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div[3]/div[2]/div[2]");

    public FormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void fillForm(String name, LocalDate birthDate, String id, String phone, String email, String filePath) {
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(formLocator));

        form.findElement(nameLocator).sendKeys(name);

        String formattedBirthDate = birthDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        form.findElement(birthLocator).sendKeys(formattedBirthDate);

        form.findElement(idLocator).sendKeys(id);
        form.findElement(phoneLocator).sendKeys(phone);
        form.findElement(emailLocator).sendKeys(email);
        form.findElement(cvLocator).sendKeys(filePath);
    }

    public void clickUndergraduate() {
        clickButton(undergraduateButtonLocator);
    }

    public void clickNext() {
        clickButton(nextButtonLocator);
    }

    public void selectTestEngineer() {
        clickButton(testEngineerButtonLocator);
    }

    public void submitForm() {
        clickButton(submitButtonLocator);
    }

    private void clickButton(By locator) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }
}
