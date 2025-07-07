package manager;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseAPI;

import static io.restassured.RestAssured.given;

public class AuthenticationController implements BaseAPI {
    public Response requestRegLogin(User user, String url) {
        return given()
                .contentType(ContentType.JSON) //header
                .body(user)
                .when()
                .post(BASE_URL + url)
                .thenReturn()
                ;
    }
}
