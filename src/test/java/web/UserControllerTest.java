package web;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import config.Configuration;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.UserService;

import static com.jayway.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @BeforeClass
    public static void setUp() throws Exception {
        Configuration.setupDB();
        Configuration.startServer();
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;
    }

    @AfterClass
    public static void stopServer() throws Exception {
        Configuration.stopServer();
    }

    @Test
    public void getUsersShouldReturn200IfSuccessful() {
        given()
                .when().get("/user/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getAllUsersShouldReturn200IfSuccessful() {
        given()
                .when().get("/user")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void addUserShouldReturn200IfSuccessful() {
        JSONObject user = new JSONObject()
                .put("username", "Username")
                .put("firstName", "First name")
                .put("lastName", "Last name");

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void addUserShouldReturn400IfFieldsMissing() {
        JSONObject user = new JSONObject()
                .put("username", "Username")
                .put("firstName", "First name");

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/user")
                .then()
                .statusCode(400);
    }
}
