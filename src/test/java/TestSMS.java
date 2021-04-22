import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestSMS {

    @BeforeTest
    public void environmentSetup() {
        RestAssured.baseURI ="http://localhost:8888/dialogue/rest/v2";
    }

    @Test
    public void successfulAdvance() throws IOException {

        File requestBody = new File(Constants.REQUESTS_DIRECTORY + "/sms_advance_response_successful.json");

        given().
                header("Content-Type", "application/json").
                body(FileUtils.readFileToString(requestBody, StandardCharsets.UTF_8)).
        when().
                post("/SMS_ADVANCE").
        then().
                statusCode(200).
                body("outcome", equalTo("SUCCESS")).
                body("terminateSession", equalTo("true")).
                body("actualResponse", equalTo("")); // value is project specific, not always an empty string
    }

    @Test
    public void failedAdvance() throws IOException {

        File requestBody = new File(Constants.REQUESTS_DIRECTORY + "/sms_advance_response_failed.json");

        given().
                header("Content-Type", "application/json").
                body(FileUtils.readFileToString(requestBody, StandardCharsets.UTF_8)).
                when().
                post("/SMS_ADVANCE").
                then().
                statusCode(200).
                body("outcome", equalTo("SUCCESS")).
                body("terminateSession", equalTo("true")).
                body("actualResponse", equalTo("Dear customer, please recharge to settle your outstanding " +
                        "Airtime Advance and be able to enjoy the service again.")); // value is project specific
    }
}
