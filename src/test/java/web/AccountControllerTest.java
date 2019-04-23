package web;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import config.Configuration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import sun.security.krb5.Config;

import static com.jayway.restassured.RestAssured.given;

public class AccountControllerTest {


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
                .when().get("/account/1")
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
                .when().post("/account")
                .then()
                .statusCode(200);
    }

    @Test
    public void addAccountShouldReturn404IfUserDoesNotExist() {
        JSONObject user = new JSONObject()
                .put("ownerId", 0L)
                .put("currency", "GBP");

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/account")
                .then()
                .statusCode(404);
    }

    @Test
    public void addAccountShouldReturn400IfFieldsAreMissing() {
        JSONObject user = new JSONObject()
                .put("currency", "GBP");

        given()
                .contentType(ContentType.JSON)
                .body(user.toString())
                .when().post("/account")
                .then()
                .statusCode(400);
    }
}