package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;

@Listeners(TestNGListener.class)
public class HomeTests extends ApplicationManager {

    @Test
    public void firstTest(){
        HomePage homePage = new HomePage(getDriver());

    }
}
