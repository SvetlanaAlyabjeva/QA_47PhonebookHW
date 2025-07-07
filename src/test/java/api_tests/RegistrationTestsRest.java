package api_tests;

import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.RandomUtils.*;
import utils.BaseAPI;

public class RegistrationTestsRest extends AuthenticationController {

    @Test
    public void registrationPositiveTest_200(){
        User user = new User(generateEmail(10), "Password123@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 200);
    } @Test
    public void registrationPositiveTest_getBody(){
        User user = new User(generateEmail(10), "Password123@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.body().print());
    }
    @Test
    public void registrationNegativeTest_wrongEmail_400(){
        User user = new User(generateString(10), "Password123@");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
