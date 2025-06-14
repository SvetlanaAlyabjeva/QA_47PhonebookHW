package ui_tests;

import dto.Contact;
import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import static pages.BasePage.*;
import static utils.RandomUtils.*;

public class AddNewContactsTests extends ApplicationManager {
   HomePage homePage;
   LoginPage loginPage;
   ContactsPage contactsPage;

    @BeforeMethod
    public void login(){
homePage = new HomePage(getDriver());
loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
loginPage.typeLoginForm("svetlana.alyabjeva@gmail.com", "Sveta08@");
contactsPage = clickButtonHeader((HeaderMenuItem.ADD));
    }

    @Test
    public void addNewContactPositiveTest(){
Contact contact = Contact.builder()
        .name(generateString(5))
        .lastName(generateString(10))
        .phone("11215255622")
        .email(generateEmail(10))
        .address("Haifa" +generateString(10))
        .description("desc"+ generateString(15))
        .build();
    }
}
