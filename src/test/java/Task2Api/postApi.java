package Task2Api;





import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class postApi {

    private Response response;
    private ExtentReports extent;
    private ExtentTest test;
    private String payload;

    @BeforeClass
    public void setUp() {
        // Initialize Extent Reports
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        test = extent.createTest("API Test for Create Object");
        RestAssured.baseURI = "https://api.restful-api.dev/objects";

    }

    @Test
    public void testCreateObject() {
        // Send a POST request with the payload
        response = given().log().all()
                .contentType(ContentType.JSON)
                .body(Task2Api.payload.addDevicePayload())
                .when()
                .post()
                .then()
                .extract()
                .response();

        // Log response details
        test.info("Response Status Code: " + response.getStatusCode());
        test.info("Response Body: " + response.asString());
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());


        // Validate the status code
        assertEquals(200, response.getStatusCode());
        test.pass("Status code validation passed");

        // Validate the response body
        JsonPath js = response.jsonPath();
        String actualName = js.getString("name");
        int actualYear = js.getInt("data.year");
        double actualPrice = js.getDouble("data.price");

        // Expected values
        String expectedName = "Apple Max Pro 2TB";
        int expectedYear = 2023;
        double expectedPrice = 7999.99;

        // Validate the extracted values
        assertEquals(expectedName, actualName);
        assertEquals(expectedYear, actualYear);
        assertEquals(expectedPrice, actualPrice, 0.01); // Use delta for floating-point comparison

        // Log validation results
        test.pass("Validation of response body completed successfully");
    }

    @AfterClass
    public void tearDown() {
        // Flush the report
        extent.flush();
    }
}
