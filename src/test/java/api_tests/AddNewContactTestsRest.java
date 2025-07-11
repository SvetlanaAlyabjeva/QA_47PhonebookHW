package api_tests;

import dto.Contact;
import dto.ErrorMessageDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class AddNewContactTestsRest extends ContactController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void addNewContactPositiveTest() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        {
            System.out.println(response.getStatusLine());
            ResponseMessageDto responseMessageDto = new ResponseMessageDto();
            if (response.getStatusCode() == 200) {
                responseMessageDto = response.body().as(ResponseMessageDto.class);
            }
            System.out.println(responseMessageDto.toString());
            Assert.assertTrue(responseMessageDto.getMessage().contains("Contact was added!"));
        }
    }
    @Test
    public void addNewContactNegativeTest_400wrongMail() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateString(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto.toString());
softAssert.assertEquals(errorMessageDto.getStatus(), 400);
softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("email=must be a well-formed email address"));
softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
softAssert.assertAll();
    }
    @Test
    public void addNewContactNegativeTest_400wrongPhone() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("1121")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto.toString());
softAssert.assertEquals(errorMessageDto.getStatus(), 400);
softAssert.assertTrue(errorMessageDto.getMessage().toString().contains
("phone=Phone number must contain only digits! And length min 10, max 15!"));
softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
softAssert.assertAll();
    }

    @Test
    public void addNewContactNegativeTest_wrongToken() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("11215255622")
                .email(generateEmail(10))
                .address("Haifa" + generateString(10))
                .description("desc" + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, TokenDto.builder()
                .token("wrong")
                .build());
                   System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto.toString());
softAssert.assertEquals(errorMessageDto.getStatus(), 401);
softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JWT strings must contain exactly 2 period characters"));
softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
softAssert.assertAll();
    }
}
