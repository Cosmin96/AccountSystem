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
import org.mockito.junit.MockitoJUnitRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

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
    public void getTransactionsShouldReturn200IfSuccessful() {
        given()
                .when().get("/transaction/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getAllTransactionsShouldReturn200IfSuccessful() {
        given()
                .when().get("/transaction")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void withdrawMoneyShouldReturn200IfSuccessful() {
        JSONObject withdrawal = new JSONObject()
                .put("fromAccount", 1L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Withdrawal");

        given()
                .contentType(ContentType.JSON)
                .body(withdrawal.toString())
                .when().post("/transaction/withdraw")
                .then()
                .statusCode(200);
    }

    @Test
    public void withdrawMoneyShouldReturn400IfAmountIsNegative() {
        JSONObject withdrawal = new JSONObject()
                .put("fromAccount", 1L)
                .put("amount", -10)
                .put("currency", "GBP")
                .put("type", "Withdrawal");

        given()
                .contentType(ContentType.JSON)
                .body(withdrawal.toString())
                .when().post("/transaction/withdraw")
                .then()
                .statusCode(400);
    }

    @Test
    public void withdrawMoneyShouldReturn400IfTypeIsWrong() {
        JSONObject withdrawal = new JSONObject()
                .put("fromAccount", 1L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Deposit");

        given()
                .contentType(ContentType.JSON)
                .body(withdrawal.toString())
                .when().post("/transaction/withdraw")
                .then()
                .statusCode(400);
    }

    @Test
    public void withdrawMoneyShouldReturn400IfInsufficientFunds() {
        JSONObject withdrawal = new JSONObject()
                .put("fromAccount", 1L)
                .put("amount", 1000000000)
                .put("currency", "GBP")
                .put("type", "Withdrawal");

        given()
                .contentType(ContentType.JSON)
                .body(withdrawal.toString())
                .when().post("/transaction/withdraw")
                .then()
                .statusCode(400);
    }

    @Test
    public void withdrawMoneyShouldReturn400IfCurrencyMismatch() {
        JSONObject withdrawal = new JSONObject()
                .put("fromAccount", 1L)
                .put("amount", 10)
                .put("currency", "EUR")
                .put("type", "Withdrawal");

        given()
                .contentType(ContentType.JSON)
                .body(withdrawal.toString())
                .when().post("/transaction/withdraw")
                .then()
                .statusCode(400);
    }

    @Test
    public void withdrawMoneyShouldReturn400IfTooManyDecimals() {
        JSONObject withdrawal = new JSONObject()
                .put("fromAccount", 1L)
                .put("amount", 10.0043)
                .put("currency", "GBP")
                .put("type", "Withdrawal");

        given()
                .contentType(ContentType.JSON)
                .body(withdrawal.toString())
                .when().post("/transaction/withdraw")
                .then()
                .statusCode(400);
    }

    @Test
    public void depositMoneyShouldReturn200IfSuccessful() {
        JSONObject deposit = new JSONObject()
                .put("toAccount", 1L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Deposit");

        given()
                .contentType(ContentType.JSON)
                .body(deposit.toString())
                .when().post("/transaction/deposit")
                .then()
                .statusCode(200);
    }

    @Test
    public void depositMoneyShouldReturn400IfAmountIsNegative() {
        JSONObject deposit = new JSONObject()
                .put("toAccount", 1L)
                .put("amount", -10)
                .put("currency", "GBP")
                .put("type", "Deposit");

        given()
                .contentType(ContentType.JSON)
                .body(deposit.toString())
                .when().post("/transaction/deposit")
                .then()
                .statusCode(400);
    }

    @Test
    public void depositMoneyShouldReturn400IfTypeWrong() {
        JSONObject deposit = new JSONObject()
                .put("toAccount", 1L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Withdrawal");

        given()
                .contentType(ContentType.JSON)
                .body(deposit.toString())
                .when().post("/transaction/deposit")
                .then()
                .statusCode(400);
    }

    @Test
    public void depositMoneyShouldReturn400IfTooManyDecimals() {
        JSONObject deposit = new JSONObject()
                .put("toAccount", 1L)
                .put("amount", 10.003)
                .put("currency", "GBP")
                .put("type", "Deposit");

        given()
                .contentType(ContentType.JSON)
                .body(deposit.toString())
                .when().post("/transaction/deposit")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn200IfSuccessful() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 3L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(200);
    }

    @Test
    public void transferMoneyShouldReturn400IfAccountToItself() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 1L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn400IfNegativeAmount() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 3L)
                .put("amount", -10)
                .put("currency", "GBP")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn400IfTypeWrong() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 3L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Deposit");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn400IfInsufficientFunds() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 3L)
                .put("amount", 10000000)
                .put("currency", "GBP")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn400IfTooManyDecimals() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 3L)
                .put("amount", 10.003)
                .put("currency", "GBP")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn400IfExchangeOfCurrencies() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 2L)
                .put("amount", 10)
                .put("currency", "GBP")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }

    @Test
    public void transferMoneyShouldReturn400IfCurrencyMismatch() {
        JSONObject transfer = new JSONObject()
                .put("fromAccount", 1L)
                .put("toAccount", 3L)
                .put("amount", 10)
                .put("currency", "EUR")
                .put("type", "Transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transfer.toString())
                .when().post("/transaction/transfer")
                .then()
                .statusCode(400);
    }
}