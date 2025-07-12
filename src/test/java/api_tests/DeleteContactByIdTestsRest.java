package api_tests;

import dto.Contact;
import dto.ResponseMessageDto;
import dto.TokenDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DeleteContactByIdTestsRest extends ContactController {
    Contact contact;

    @BeforeClass
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
            contact.setId(responseMessageDto.getMessage().split("ID: ")[1]);}
    }
@Test
    public void deleteContactByIdPositiveTest(){
Response response = deleteContactById(contact, tokenDto);
response
        .then()
        .log().all()
        .statusCode(200)
    .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"));
}

@Test
    public void deleteContactByIdNegativeTest_401_invalidToken(){
    TokenDto tokenDto1 = TokenDto.builder().token("invalid").build();
Response response = deleteContactById(contact, tokenDto1);
response
        .then()
        .log().all()
        .statusCode(401)
    .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
}
}
