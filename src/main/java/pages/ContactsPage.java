package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ContactsPage extends BasePage{
    public ContactsPage (WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[@href='/contacts']")
    WebElement btnContactsHeader;

    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']")
    List<WebElement> contactsList;

    public boolean isContactsPresent(){
        return isElementPresent(btnContactsHeader);
    }

    public Integer getContactsListSize(){
        System.out.println("contacts list size is"+contactsList.size());
        return contactsList.size();
    }

    public boolean validateContactNamePhone(String name, String phone) {
for (WebElement element: contactsList){
    //System.out.println(element.getText());
    if(element.getText().contains(name) &&element.getText().contains(phone))
        return true;
    }
return false;
    }
}
