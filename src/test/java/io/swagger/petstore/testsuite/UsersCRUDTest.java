package io.swagger.petstore.testsuite;

import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.info.UsersSteps;
import io.swagger.petstore.testbase.TestBase;
import io.swagger.petstore.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UsersCRUDTest extends TestBase {

    static String id = "5";
    static String username = "test123" + TestUtils.getRandomValue();
    static String firstName = "Anu";
    static String lastName = "Sharma";
    static String email = "anusharma@gmail.com";
    static String password = "pass123";
    static String phone = "78291030122";
    static String userStatus = "1";

    static int userID;

    @Steps
    UsersSteps usersSteps;

    @Title("This will create new user")
    @Test
    public void test001() {
        ValidatableResponse response = usersSteps.createNewUser(id, username, firstName, lastName, email, password, phone, userStatus);
        response.log().all().statusCode(200);
    }

    @Title("Verify user was added")
    @Test
    public void test002() {
        HashMap<String, Object> userMap = usersSteps.getUserByUserName(username);
        Assert.assertThat(userMap, hasValue(username));
    }

    @Title("Verify user was Updated")
    @Test
    public void test003() {
        username = username + "_updated";
        usersSteps.updateUser(id,username,firstName,lastName,email,password,phone,userStatus);
        HashMap<String, Object> userMap = usersSteps.getUserByUserName(username);
        Assert.assertThat(userMap, anything(firstName));
    }

    @Title("Verify user was deleted")
    @Test
    public void test004() {
        usersSteps.deleteUser(username).statusCode(200).log().all();

    }
}
