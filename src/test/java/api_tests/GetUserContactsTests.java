package api_tests;

import dto.ContactsDto;
import dto.ErrorMessageDto;
import dto.TokenDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUserContactsTests extends ContactController {
    SoftAssert softAssert = new SoftAssert();

    @Test(groups = "smoke")
    public void getAllUserContactsPositiveTest() {
        Response response = getAllUserContacts(tokenDto);
        System.out.println(response.getStatusLine());
        ContactsDto contactsDto = new ContactsDto();
        if (response.getStatusCode() == 200) ;
        {
            contactsDto = response.body().as(ContactsDto.class);
        }
        System.out.println(">>>>" + contactsDto.getContacts().length);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(groups = "smoke")
    public void getAllUserContactsNegativeTest_401_WrongToken() {
        Response response = getAllUserContacts(TokenDto.builder()
                .token("wrong")
                .build());
        System.out.println(response.getStatusLine());
        response
                .then()
                .log().all()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto.toString());
        softAssert.assertEquals(errorMessageDto.getStatus(), 401);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JWT strings must contain exactly 2 period characters"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertAll();
    }
}
