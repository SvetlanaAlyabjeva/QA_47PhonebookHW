package api_tests;

import dto.ContactsDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetUserContactsTests extends ContactController {

    @Test(groups = "smoke")
    public void getAllUserContactsPositiveTest(){
        Response response = getAllUserContacts();
        System.out.println(response.getStatusLine());
        ContactsDto contactsDto = new ContactsDto();
        if(response.getStatusCode() == 200);{
            contactsDto = response.body().as(ContactsDto.class);
        }
        System.out.println(">>>>" + contactsDto.getContacts().length);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
