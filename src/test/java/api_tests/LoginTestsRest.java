package api_tests;

import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.PropertiesReader.*;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class LoginTestsRest extends AuthenticationController {


    @Test
    public void loginPositiveTest_200() {
        User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void loginNegativeTest_401_WrongPassword() {
        User user = new User(getProperty("login.properties", "email"), "Password123#");
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 401);
    }

    @Test
    public void loginNegativeTest_401_WrongEmail() {
        User user = new User("sveta@mail.com", getProperty("login.properties", "password"));
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 401);
    }
}