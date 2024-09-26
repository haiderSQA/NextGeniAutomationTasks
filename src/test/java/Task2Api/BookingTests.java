package Task2Api;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookingTests {

    private static String baseUrl = "https://restful-booker.herokuapp.com";
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private ExtentTest test;

    @BeforeClass
    public void setUp() {
        // Initialize Extent Reports
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        test = extent.createTest("BookingTest");
        RestAssured.baseURI = baseUrl;

        // Logging the setup step
        test.info("Setting up the test environment.");
    }

    @AfterClass
    public static void tearDown() {
        // Logging the teardown step
        extent.flush();
    }

    @Test
    public void addBooking() {
        test = extent.createTest("Add Booking Test");



        // Logging the request details
        test.info("Sending POST request to create a new booking.");
        test.info("Request Body: " + Payload.addItem());

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Payload.addItem())
                .when()
                .post("/booking")
                .then()
                .log().all()  // Log all response details
                .statusCode(200)
                .extract().response();

        // Logging the response details
        test.info("Received response from POST /booking.");
        test.info("Response: " + response.asString());
        System.out.println("Reponse of an Post Api"  +response.asString());

        int bookingId = response.jsonPath().getInt("bookingid");
        test.info("Created booking ID: " + bookingId);

        try {
            validateBooking(bookingId);   //This is GET Api method which get booking detail after passing booking id
            test.pass("Booking validated successfully.");
        } catch (AssertionError e) {
            test.fail("Booking validation failed: " + e.getMessage());
            throw e;
        }

        //to validate the api without somefields
        addBookingWithoutLastname();
    }

    public void validateBooking(int bookingId) {
        test.info("Validating the booking details for booking ID: " + bookingId);

        Response response = RestAssured.given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .log().all()  // Log all response details
                .statusCode(200)
                .extract().response();
        test.info("Received response from GET /booking/" + bookingId);
        test.info("Response: " + response.asString());

        System.out.println("Reponse of an get Api"  +response.asString());

        String firstname = response.jsonPath().getString("firstname");
        String lastname = response.jsonPath().getString("lastname");
        float totalprice = response.jsonPath().getFloat("totalprice");
        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        String checkin = response.jsonPath().getString("bookingdates.checkin");
        String checkout = response.jsonPath().getString("bookingdates.checkout");
        String additionalneeds = response.jsonPath().getString("additionalneeds");

        test.info("Validating the 'firstname' field.");
        Assert.assertEquals("Haider", firstname);
        test.pass("Validation passed for 'firstname'.");

        test.info("Validating the 'lastname' field.");
        Assert.assertEquals("Hasnain", lastname);
        test.pass("Validation passed for 'lastname'.");

        test.info("Validating the 'totalprice' field.");
        Assert.assertEquals(35000, totalprice);
        test.pass("Validation passed for 'totalprice'.");

        test.info("Validating the 'depositpaid' field.");
        Assert.assertTrue(depositpaid);
        test.pass("Validation passed for 'depositpaid'.");

        test.info("Validating the 'checkin' field.");
        Assert.assertEquals("2022-01-01", checkin);
        test.pass("Validation passed for 'checkin'.");

        test.info("Validating the 'checkout' field.");
        Assert.assertEquals("2024-01-01", checkout);
        test.pass("Validation passed for 'checkout'.");

        test.info("Validating the 'additionalneeds' field.");
        Assert.assertEquals("testAdd", additionalneeds);
        test.pass("Validation passed for 'additionalneeds'.");
    }

    public void addBookingWithoutLastname() {
        test = extent.createTest("Add Booking with out last name");


        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Payload.addBookingwithemptydata())
                .log().all()
                .when()
                .post("/booking")
                .then()
                .log().all()
                .statusCode(500)
                .extract().response();

        test.pass("Handled invalid data correctly.");
    }
}

