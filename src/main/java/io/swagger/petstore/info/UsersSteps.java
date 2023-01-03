package io.swagger.petstore.info;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.constants.EndPoints;
import io.swagger.petstore.model.UserPojo;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UsersSteps {

    @Step("Create user with userName : {1}, firstName:{2}, lastName: {3},email:{4}")
    public ValidatableResponse createNewUser(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        UserPojo userPojo = new UserPojo();
        userPojo.setId(id);
        userPojo.setUsername(username);
        userPojo.setFirstName(firstName);
        userPojo.setLastName(lastName);
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        userPojo.setPhone(phone);
        userPojo.setUserStatus(userStatus);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post(EndPoints.CREATE_USER)
                .then();
    }

    @Step("Getting the user information with firstName: {0}")
    public HashMap<String, Object> getUserByUserName(String username) {

        return SerenityRest.given().log().all()
                .pathParam("username", username)
                .when()
                .get(EndPoints.GET_USER_BY_USERNAME)
                .then()
                .statusCode(200)
                .extract().path("");
    }

    @Step("Updating User information with userName: {0}, firstName: {1}, lastName: {2}, email: {3}, programme: {4} and courses: {5}")

    public ValidatableResponse updateUser(String id, String userName, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        UserPojo usersPojo = new UserPojo();
        usersPojo.setId(id);
        usersPojo.setUsername(userName);
        usersPojo.setFirstName(firstName);
        usersPojo.setLastName(lastName);
        usersPojo.setEmail(email);
        usersPojo.setPassword(password);
        usersPojo.setPhone(phone);
        usersPojo.setUserStatus(userStatus);

        return  SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("username", userName)
                .when()
                .body(usersPojo)
                .put(EndPoints.UPDATE_USER_BY_USERNAME)
                .then();

    }
    @Step("Delete user")
    public ValidatableResponse deleteUser(String username) {

        return SerenityRest.given().log().all()
                .pathParam("username", username)
                .when()
                .delete(EndPoints.DELETE_USER_BY_USERNAME)
                .then();
    }
}
