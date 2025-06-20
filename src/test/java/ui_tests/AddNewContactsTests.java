package ui_tests;

import dto.Contact;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
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
   AddPage addPage;
   int sizeBeforeAdd;

    @BeforeMethod
    public void login(){
homePage = new HomePage(getDriver());
loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
loginPage.typeLoginForm("svetlana.alyabjeva@gmail.com", "Sveta08@");
contactsPage = new ContactsPage(getDriver());
sizeBeforeAdd = contactsPage.getContactsListSize();
addPage = clickButtonHeader((HeaderMenuItem.ADD));
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
addPage.typeAddNewContactForm(contact);
int sizeAfterAdd = contactsPage.getContactsListSize();
        System.out.println(sizeBeforeAdd+"X"+sizeAfterAdd);
        Assert.assertEquals(sizeBeforeAdd+1, sizeAfterAdd);
    }

    @Test
    public void addNewContactPositiveTest_validateContactNamePhone(){
Contact contact = Contact.builder()
        .name(generateString(8))
        .lastName(generateString(10))
        .phone(generatePhone(10))
        .email(generateEmail(10))
        .address("Haifa" +generateString(10))
        .description("desc"+ generateString(15))
        .build();
addPage.typeAddNewContactForm(contact);
Assert.assertTrue(contactsPage.validateContactNamePhone(contact.getName(), contact.getPhone()));

    }
}
