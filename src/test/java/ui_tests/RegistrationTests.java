package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.RetryAnalyzer;
import utils.TestNGListener;

import static utils.RandomUtils.generateEmail;
@Listeners(TestNGListener.class)
public class RegistrationTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        this.loginPage = new LoginPage(getDriver());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void registrationPositiveTest() {
        User user = new User(generateEmail(10), "Password1213!");
        loginPage.typeRegistrationForm(user);
        if(loginPage.isNoContactsMessagePresent("Add new by clicking on Add in NavBar!")){
            loginPage.logOut();
            loginPage.typeRegistrationForm(user);
            Assert.assertTrue(loginPage.closeAlertReturnText().contains("User already exist"));
            Assert.assertTrue(true);
        }else {
            Assert.fail("wrong registration with user" + user.toString());
        }
    }

    @Test
    public void registrationNegativeTest_DuplicateUser() {
        User user = new User(generateEmail(10), "Password1213!");
        loginPage.typeRegistrationForm(user);
        //Assert.assertTrue(loginPage.isNoContactsMessagePresent("Add new by clicking on Add in NavBar!"));
    }

    @Test
    public void registrationNegativeTestWrongPassword() {
        User user = new User(generateEmail(10), "Password1213");
        loginPage.typeRegistrationForm(user);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Password must contain at least one uppercase letter!"));

    }

}
