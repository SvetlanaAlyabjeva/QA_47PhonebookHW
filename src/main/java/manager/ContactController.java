package manager;

import dto.Contact;
import dto.TokenDto;
import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import utils.BaseAPI;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static utils.PropertiesReader.getProperty;

public class ContactController implements BaseAPI {

    protected TokenDto tokenDto;

@BeforeSuite(alwaysRun = true)
    public void login(){
    User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
    Response response = new AuthenticationController().requestRegLogin(user, LOGIN_URL);
   // System.out.println(response.getStatusLine());
    if (response.getStatusCode() == 200) {
tokenDto = response.body().as(TokenDto.class);
    } else {
        System.out.println("smth sent wrong>>>" + response.getStatusCode());
    }
}
protected Response addNewContactRequest(Contact contact, TokenDto tokenDto) {
    return given()
            .body(contact)
            .baseUri(getProperty("login.properties", "baseUri"))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .header("Authorization", tokenDto.getToken())
            .post(ADD_NEW_CONTACT_URL)
            .thenReturn()
            ;
}
    protected Response updateContactRequest(Contact contact, TokenDto tokenDto){
    return  given()
            .log().all() //body, headers, cookies, status
            .body(contact)
            .baseUri(getProperty("login.properties", "baseUri"))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .header("Authorization", tokenDto.getToken())
            .put(ADD_NEW_CONTACT_URL)
            .thenReturn()
            ;
}
public Response getAllUserContacts(){
    return given()
            .baseUri(getProperty("login.properties", "baseUri"))
            .accept(ContentType.JSON)
            .header("Authorization", tokenDto.getToken())
            .get(ADD_NEW_CONTACT_URL)
            .thenReturn()
            ;
}
protected Response deleteContactById(Contact contact, TokenDto tokenDto){
    return given()
            .log().all()
            .baseUri(getProperty("login.properties", "baseUri"))
            .accept(ContentType.JSON)
            .header("Authorization", tokenDto.getToken())
            .delete(ADD_NEW_CONTACT_URL+"/"+contact.getId())
            .thenReturn()
            ;
}


}
