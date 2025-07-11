package manager;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseAPI;
import static utils.PropertiesReader.*;

import static io.restassured.RestAssured.given;

public class AuthenticationController implements BaseAPI {
    public Response requestRegLogin(User user, String url) {
        return given()
                .baseUri(getProperty("login.properties", "baseUri"))
                .contentType(ContentType.JSON) //header
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post(url)
                .thenReturn()
                ;
    }
}
