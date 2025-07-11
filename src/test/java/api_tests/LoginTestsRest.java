package api_tests;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static utils.PropertiesReader.*;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class LoginTestsRest extends AuthenticationController {
    SoftAssert softAssert = new SoftAssert();

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

    @Test
    public void loginPositiveTest() {
        User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        softAssert.assertEquals(response.getStatusCode(), 200);
        TokenDto tokenDto = response.body().as(TokenDto.class);
        System.out.println(tokenDto);
        softAssert.assertTrue(tokenDto.toString().contains("token"));
        softAssert.assertAll();
    }
    @Test
    public void loginNegativeTest_wrongPassword() {
        User user = new User("svetlana.alyabjeva@gmail.com", "Sveta08@11");
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        softAssert.assertEquals(response.getStatusCode(), 401);
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        softAssert.assertTrue(errorMessageDto.getMessage().equals("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getPath().contains("login/usernamepassword"));
        System.out.println(LocalDate.now().toString().equals(errorMessageDto.getTimestamp().substring(0,10)));
        softAssert.assertEquals(LocalDate.now().toString(), errorMessageDto.getTimestamp().substring(0,10));
        softAssert.assertAll();
    }
    @Test
    public void loginNegativeTest_wrongEmailFormat() {
        User user = new User("svetlevagmail.com", "Sveta08@11");
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        softAssert.assertEquals(response.getStatusCode(), 401);
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        softAssert.assertTrue(errorMessageDto.getMessage().equals("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getPath().contains("login/usernamepassword"));
        System.out.println(LocalDate.now().toString().equals(errorMessageDto.getTimestamp().substring(0,10)));
        softAssert.assertEquals(LocalDate.now().toString(), errorMessageDto.getTimestamp().substring(0,10));
        softAssert.assertAll();
    }

}
