package ui_tests;

import data_provider.ContactDP;
import dto.Contact;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.*;
import static utils.RandomUtils.*;
import static utils.PropertiesReader.*;

@Listeners(TestNGListener.class)
public class AddNewContactsTests extends ApplicationManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    int sizeBeforeAdd;
    String existPhone;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        contactsPage = new ContactsPage(getDriver());
        sizeBeforeAdd = contactsPage.getContactsListSize();
        existPhone = contactsPage.getPhoneFromList();
        addPage = clickButtonHeader((HeaderMenuItem.ADD));
    }

    @Test(groups = "smoke")
    public void addNewContactPositiveTest() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        int sizeAfterAdd = contactsPage.getContactsListSize();
        System.out.println(sizeBeforeAdd + "X" + sizeAfterAdd);
        Assert.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
    }

    @Test
    public void addNewContactPositiveTest_validateContactNamePhone() {
        Contact contact = Contact.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        Assert.assertTrue(contactsPage.validateContactNamePhone(contact.getName(), contact.getPhone()));

    }

    @Test
    public void addNewContactNegativeTest_NoName() {
        Contact contact = Contact.builder()
                .name("")
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        int sizeAfterAdd = contactsPage.getContactsListSize();
        System.out.println(sizeBeforeAdd + "X" + sizeAfterAdd);
        Assert.assertNotEquals(sizeBeforeAdd + 1, sizeAfterAdd);
    }

    @Test
    public void addNewContactNegativeTest_emptyName() {
        Contact contact = Contact.builder()
                .name("")
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        Assert.assertTrue(addPage.validateURL("add"));
    }

    @Test
    public void addNewContactNegativeTest_emptyLastName() {
        Contact contact = Contact.builder()
                .name(generateString(7))
                .lastName("")
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        Assert.assertTrue(addPage.isURLNotContains("contacts"));
    }

    @Test
    public void addNewContactNegativeTest_PhoneNotValid() {
        Contact contact = Contact.builder()
                .name(generateString(10))
                .lastName(generateString(10))
                .phone(generateString(12))
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
    }

    @Test(invocationCount = 1)
    public void addNewContactNegativeTest_MailNotValid() {
        Contact contact = Contact.builder()
                .name(generateString(10))
                .lastName(generateString(10))
                .phone(generatePhone(12))
                .email(generateString(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains(
                "Email not valid: must be a well-formed email address"));
    }

    @Test(invocationCount = 1)
    public void addNewContactNegativeTest_ExistPhone() {
        Contact contact = Contact.builder()
                .name(generateString(10))
                .lastName(generateString(10))
                .phone(existPhone)
                .email(generateEmail(8))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        //Assert.assertTrue(addPage.isURLNotContains("contacts"));
    }

    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = ContactDP.class)
    public void addNewContact_PositiveDP(Contact contact) {
        addPage.typeAddNewContactForm(contact);
        Assert.assertTrue(contactsPage.validateContactNamePhone(contact.getName(), contact.getPhone()));
    }

}
