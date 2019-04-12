package web;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.jayway.restassured.RestAssured.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Before
    public void setUp() {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;
    }

    @Test
    public void getAccountsForUserShouldReturn200IfSuccessful() {
        given()
                .when().get("/account/user/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getAccountShouldReturn200IfSuccessful() {
        given()
                .when().get("/account/get/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void addAccountShouldReturn200IfSuccessful() {
        JSONObject user = new JSONObject()
                .put("ownerId", 1L)
                .put("currency", "GBP");

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/account/add")
                .then()
                .statusCode(200);
    }

    @Test
    public void addAccountShouldReturn403IfUserDoesNotExist() {
        JSONObject user = new JSONObject()
                .put("ownerId", 0L)
                .put("currency", "GBP");

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/account/add")
                .then()
                .statusCode(403);
    }

    @Test
    public void addAccountShouldReturn403IfFieldsAreMissing() {
        JSONObject user = new JSONObject()
                .put("ownerId", 0L);

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/account/add")
                .then()
                .statusCode(403);
    }
}