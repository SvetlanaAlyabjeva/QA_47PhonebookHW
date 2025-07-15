package api_tests;

import dto.ErrorMessageDto;
import dto.User;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.PropertiesReader.getProperty;

import manager.AuthenticationController;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.RandomUtils.*;

import org.testng.asserts.SoftAssert;
import utils.BaseAPI;

public class RegistrationTestsRest extends AuthenticationController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest_200() {
        User user = new User(generateEmail(10), "Password123@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void registrationPositiveTest_getBody() {
        User user = new User(generateEmail(10), "Password123@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.body().print());
    }

    @Test
    public void registrationNegativeTest_wrongEmail_400() {
        User user = new User(generateString(10), "Password123@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void registrationNegativeTest_DuplicateUser_409() {
        User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        // User user = new User("svetlana.alyabjeva@gmail.com", "Sveta08@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        response
                .then()
                .log().all()
                .statusCode(409)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto.toString());
        softAssert.assertEquals(errorMessageDto.getStatus(), 409);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("User already exists"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Conflict"));
        softAssert.assertAll();
    }
}

