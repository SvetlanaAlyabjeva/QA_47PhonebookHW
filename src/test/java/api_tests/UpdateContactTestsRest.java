package api_tests;

import dto.Contact;
import dto.ErrorMessageDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UpdateContactTestsRest extends ContactController {
    SoftAssert softAssert = new SoftAssert();
    Contact contact;

    @BeforeClass(alwaysRun = true)
    public void CreateContact() {
        contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        ResponseMessageDto responseMessageDto;

        if (response.getStatusCode() != 200)
            System.out.println("Contact is not created");
        else {
            responseMessageDto = response.body().as(ResponseMessageDto.class);

            contact.setId(responseMessageDto.getMessage().split("ID: ")[1]);
        }
    }

    @Test(groups = {"smoke", "contact"})
    public void updateContactPositiveTest() {
        System.out.println(contact.toString());
        contact.setName("New name");
        Response response = updateContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        response
                .then()
                .log().all()//ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"));
        ResponseMessageDto responseMessageDto = response.body().as(ResponseMessageDto.class);
        Assert.assertTrue(responseMessageDto.getMessage().contains("Contact was updated"));
    }

    @Test(groups = "smoke")
    public void updateContactNegativeTest_401_WrongToken() {
        System.out.println(contact.toString());
        contact.setName("New name");
        Response response = updateContactRequest(contact, TokenDto.builder()
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

    @Test(groups = "smoke")
    public void updateContactNegativeTest_400_WrongFormat() {
        System.out.println(contact.toString());
        contact.setEmail("newMail.com");
        Response response = updateContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto.toString());
        softAssert.assertEquals(errorMessageDto.getStatus(), 400);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("email=must be a well-formed email address"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
        softAssert.assertAll();
    }
}