package test.dialogue;

import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SMSTest {

    @BeforeTest
    public void environmentSetup() {
        RestAssured.baseURI ="http://localhost:8888/dialogue/rest/v2";
    }

    @Test
    public void smsSuccessfulAdvance() throws IOException {

        File requestBody = new File(Constants.REQUESTS_DIRECTORY + "/sms_advance_request_successful.json");

        given().
                header("Content-Type", "application/json").
                body(FileUtils.readFileToString(requestBody, StandardCharsets.UTF_8)).
        when().
                post("/SMS_DIALOGUE").
        then().
                statusCode(200).
                body("outcome", equalTo("SUCCESS")).
                body("terminateSession", equalTo("true")).
                body("callback.action", equalTo("advance")).
                body("callback.params", notNullValue()).
                body("actualResponse", equalTo(""));
    }

    @Test
    public void smsFailedAdvance() throws IOException {

        File requestBody = new File(Constants.REQUESTS_DIRECTORY + "/sms_advance_request_failed.json");

        given().
                header("Content-Type", "application/json").
                body(FileUtils.readFileToString(requestBody, StandardCharsets.UTF_8)).
        when().
                post("/SMS_DIALOGUE").
        then().
                statusCode(200).
                body("outcome", equalTo("SUCCESS")).
                body("terminateSession", equalTo("true")).
                body("callback", nullValue()).
                body("actualResponse", equalTo("Dear customer, please recharge to settle your outstanding " +
                        "Airtime Advance of M6449 and be able to enjoy the service again.")); // the value is project specific
    }
}
