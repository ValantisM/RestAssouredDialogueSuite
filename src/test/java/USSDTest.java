import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class USSDTest {

    @BeforeTest
    public void environmentSetup() {
        RestAssured.baseURI ="http://localhost:8888/dialogue/rest/v2";
    }

    @Test
    public void ussdInit() throws IOException {

        File requestBody = new File(Constants.REQUESTS_DIRECTORY + "/ussd_init.json");

        given().
                header("Content-Type", "application/json").
                body(FileUtils.readFileToString(requestBody, StandardCharsets.UTF_8)).
                when().
                post("/USSD_DIALOGUE").
                then().
                statusCode(200).
                body("outcome", equalTo("SUCCESS")).
                body("terminateSession", equalTo("false")).
                body("actualResponse", startsWith("Airtime Advance. Select amount up to"));
    }
}
