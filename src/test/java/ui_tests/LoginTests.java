package ui_tests;

import dto.User;
import dto.UserLombok;
import manager.ApplicationManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.RandomUtils.*;

import java.lang.reflect.Method;

import static utils.RandomUtils.generateEmail;

public class LoginTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;

    @BeforeMethod
    public void goToLoginPage() {
        homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        loginPage = new LoginPage(getDriver());
        contactsPage = new ContactsPage(getDriver());
    }

    @Test

    public void loginPositiveTests(Method method) {
        logger.info("start method  " + method.getName());
        //User user = new User("svetlana.alyabjeva@gmail.com", "Sveta08@");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm("svetlana.alyabjeva@gmail.com", "Sveta08@");
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isContactsPresent());
    }

    @Test
    public void loginPositiveTestLombok() {
        UserLombok user = UserLombok.builder()
                .username("svetlana.alyabjeva@gmail.com")
                .password("Sveta08@")
                .build();
        loginPage.typeLoginForm(user.getUsername(), user.getPassword());
        Assert.assertTrue(contactsPage.isContactsPresent());
    }

    @Test

    public void loginNegativeTest_WrongPassword() {
        // User user = new User("svetlana.alyabjeva@gmail.com", "Sveta08@111");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm("svetlana.alyabjeva@gmail.com", "Sveta08@111");
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTestLombok_WrongPassword() {
        UserLombok user = UserLombok.builder()
                .username("svetlana.alyabjeva@gmail.com")
                .password("Sveta08@111")
                .build();
        loginPage.typeLoginForm(user.getUsername(), user.getPassword());
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTestLombok_WrongEmail() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(10))
                .password("Sveta08@")
                .build();
        loginPage.typeLoginForm(user.getUsername(), user.getPassword());
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTest_WrongEmail() {
        //User user = new User("svetlan.alyabjeva@gmail.com", "Sveta08@111");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm("sveana.alyabjeva@gmail.com", "Sveta08@");
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

}
