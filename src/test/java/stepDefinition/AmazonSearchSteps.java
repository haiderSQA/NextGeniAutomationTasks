package stepDefinition;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.testng.Assert.assertEquals;

public class AmazonSearchSteps {

    WebDriver driver;
    String firstToyPrice;
    String firstToyPricedetailpage;
    String secondToyPrice;
    String secondToyPricedetailpage;

    @Given("I open the Chrome browser with the specified profile")
    public void i_open_the_chrome_browser_with_the_specified_profile() {
        WebDriverManager.chromedriver().setup();
        String userProfile = "C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1";
        String profileDir = "Profile 1";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=" + userProfile);
        options.addArguments("profile-directory=" + profileDir);

        driver = new ChromeDriver(options);
    }

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    @When("I search for {string}")
    public void i_search_for(String searchText) {
        WebElement searchPlaceHolder = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
        searchPlaceHolder.sendKeys(searchText);

        WebElement clickOnSearchButton = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));
        clickOnSearchButton.click();
    }

    @When("I add the first toy to the cart and store its price")
    public void i_add_the_first_toy_to_the_cart_and_store_its_price() throws InterruptedException {
        Thread.sleep(5000);
        WebElement clickonFirstToy = driver.findElement(By.xpath("//div[@class='s-widget-container s-spacing-small s-widget-container-height-small celwidget slot=MAIN template=SEARCH_RESULTS widgetId=search-results_1']//span[@class='a-price']"));
        String priceOffirsttoy = clickonFirstToy.getText();
        firstToyPrice = formatPrice(priceOffirsttoy);
        clickonFirstToy.click();

        WebElement productdetail1sttoy = driver.findElement(By.xpath("//div[@class='a-section a-spacing-micro']"));
        String priceOndetailPage = productdetail1sttoy.getText();
        firstToyPricedetailpage = formatPrice(priceOndetailPage);

        driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
        driver.navigate().back();
        driver.navigate().back();
    }

    @When("I add the second toy to the cart and store its price")
    public void i_add_the_second_toy_to_the_cart_and_store_its_price() {
        WebElement clickonsecondToy = driver.findElement(By.xpath("//div[@class='s-widget-container s-spacing-small s-widget-container-height-small celwidget slot=MAIN template=SEARCH_RESULTS widgetId=search-results_2']//span[@class='a-price']"));
        String priceOfsecondftoy = clickonsecondToy.getText();
        secondToyPrice = formatPrice(priceOfsecondftoy);
        clickonsecondToy.click();

        WebElement productdetail2ndtoy = driver.findElement(By.xpath("//div[@class='a-section a-spacing-micro']"));
        String priceOndetailPage2 = productdetail2ndtoy.getText();
        secondToyPricedetailpage = formatPrice(priceOndetailPage2);

        driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
    }

    @Then("I validate the prices of the toys in the cart")
    public void i_validate_the_prices_of_the_toys_in_the_cart() {
        WebElement cartview = driver.findElement(By.xpath("//a[@href='/cart?ref_=sw_gtc']"));
        cartview.click();

        String validatingT2priceincart = driver.findElement(By.xpath("//span[normalize-space()='" + secondToyPrice + "']")).getText();


        String validatingT1priceincart = driver.findElement(By.xpath("//span[normalize-space()='" + firstToyPrice + "']")).getText();


        System.out.println("First toy price on search: " + firstToyPrice);
        System.out.println("First toy price on product detail page: " + firstToyPricedetailpage);
        System.out.println("First toy price in cart: " + validatingT1priceincart);

       System.out.println("Second toy price on search: " + secondToyPrice);
     System.out.println("Second toy price on product detail page: " + secondToyPricedetailpage);
       System.out.println("Second toy price in cart: " + validatingT2priceincart);

        try {
            assertEquals(firstToyPrice, firstToyPricedetailpage, "First toy price on search page does not match detail page");
            assertEquals(firstToyPrice, validatingT1priceincart, "First toy price in cart does not match");
            assertEquals(secondToyPrice, secondToyPricedetailpage, "Second toy price on search page does not match detail page");
            assertEquals(secondToyPrice, validatingT2priceincart, "Second toy price in cart does not match");
            System.out.println("All price validations passed successfully.");
        } catch (AssertionError e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public static String formatPrice(String priceString) {
        String[] lines = priceString.split("\n");

        // Check if we have two lines
        if (lines.length < 2) {
            return "Price format is incorrect.";
        }

        // Extract the price part from the first line
        String line1 = lines[0];
        int dollarIndex = line1.indexOf('$');
        if (dollarIndex == -1) {
            return "Price format is incorrect.";
        }

        String pricePart = line1.substring(dollarIndex); // Gets "$14"
        String centsPart = lines[1].trim(); // Gets "99"

        // Combine and format the price
        return pricePart + "." + centsPart;
    }
}
