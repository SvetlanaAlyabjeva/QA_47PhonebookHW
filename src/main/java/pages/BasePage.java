package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HeaderMenuItem;

import java.time.Duration;

public abstract class BasePage {
    static WebDriver driver;

    Logger logger = LoggerFactory.getLogger(BasePage.class);

    public static void setDriver(WebDriver wd) {
        driver = wd;
    }

    public static void pause(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends BasePage> T clickButtonHeader(HeaderMenuItem headerMenuItem) {
        //pause(3);
        //WebElement element = driver.findElement(By.xpath(headerMenuItem.getLocator()));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(headerMenuItem.getLocator()))).click();
        //element.click();
        switch (headerMenuItem) {
            case LOGIN -> {
                return (T) new LoginPage(driver);
            }
            case ADD -> {
                return (T) new AddPage(driver);
            }
            case CONTACTS -> {
                return (T) new ContactsPage(driver);
            }
            case HOME -> {
                return (T) new HomePage(driver);
            }
            case ABOUT -> {
                return (T) new AboutPage(driver);
            }
            case SIGN_OUT -> {
                return (T) new LoginPage(driver);
            }
            default -> throw new IllegalArgumentException("Invalid parameter headerMenuItem");
        }
    }

    public boolean isElementPresent(WebElement element) {
        return element.isDisplayed();
    }

    public boolean isTextInElementPresent(WebElement element, String text) {
        return element.getText().contains(text);
    }
public boolean validateURL(String str){
       return new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains(str));

}public boolean isURLNotContains(String str){
        pause(5);
       return new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.not(ExpectedConditions.urlContains(str)));
}

}
