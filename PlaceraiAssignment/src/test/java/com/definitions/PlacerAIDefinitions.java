package com.definitions;

import com.API_Calls;
import com.DB_Queries;
import com.PropertiesReader;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import org.testng.Assert;
import pom.BrowserPage;

import static com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter.getCurrentStep;

public class PlacerAIDefinitions extends TestBase {

    private BrowserPage browserPage;
    private static String baseURL;

    @Before
    public static void setup() {
        baseURL = PropertiesReader.getInstance().getProperty("base.url");
        RestAssured.baseURI = baseURL;
    }

    @Given("I open browser")
    public void i_open_browser() {
        browserPage = new BrowserPage(chromeDriver);
        getCurrentStep().log(Status.INFO, "Opening Chrome browser...");
        getCurrentStep().log(Status.INFO, "Chrome is open!");
    }

    @Given("I navigate to {string} url using chrome browser")
    public void i_navigate_to_url_using_chrome_browser(String website) {
        browserPage.openPage(baseURL.concat(website));
    }

    @Given("matomo added name {string} to matomo_log_action table")
    public void matomo_added_name_to_matomo_log_action_table(String url) {
        String expectedURL = DB_Queries.executeQuery("SELECT name FROM matomo.matomo_log_action WHERE name = '".concat(url).concat("'"));
        Assert.assertEquals(url, expectedURL);
    }

    @Given("matomo recent {string} of {string} equals {string} of {string} action id")
    public void matomo_recent_of_equals_of_action_id(String idaction, String matomo_log_action, String idaction_name, String matomo_log_link_visit_action) {
        String logActionID = DB_Queries.executeQuery("SELECT".concat(idaction).concat("FROM matomo.").concat(matomo_log_action));
        String logLinkVisitActionID = DB_Queries.executeQuery("SELECT".concat(idaction_name).concat("FROM matomo.").concat(matomo_log_link_visit_action));
        Assert.assertEquals(logActionID, logLinkVisitActionID);
    }

    @Given("matomo recent {string} of {string} equals {string} of {string}")
    public void matomo_recent_of_equals_of(String idvisit1, String matomo_log_link_visit_action, String idvisit2, String matomo_log_visit) {
        String logLinkVisitID = DB_Queries.executeQuery("SELECT".concat(idvisit1).concat("FROM matomo.").concat(matomo_log_link_visit_action));
        String logVisitID = DB_Queries.executeQuery("SELECT".concat(idvisit2).concat("FROM matomo.").concat(matomo_log_visit));
        Assert.assertEquals(logLinkVisitID, logVisitID);
    }

    @Then("I close the browser")
    public void i_close_the_browser() {
        getCurrentStep().log(Status.INFO, "Closing chrome driver...");
        closeDriver();
        getCurrentStep().log(Status.INFO, "Chrome driver is closed!");
    }

    @When("I send requests to {string} url using API call")
    public void i_send_requests_to_url_using_api_call(String website) {
        API_Calls.extractGETResponse(baseURL.concat(website));
    }

    @Then("first visit is from {string} and the second is from {string}")
    public void first_visit_is_from_and_the_second_is_from(String browserName1, String browserName2) {
        String firstVisit = DB_Queries.executeQuery("SELECT config_browser_name FROM matomo.matomo_log_visit WHERE idvisit =(SELECT MAX(idvisit)-1 FROM matomo.matomo_log_visit)");
        String secondVisit = DB_Queries.executeQuery("SELECT config_browser_name FROM matomo.matomo_log_visit WHERE idvisit =(SELECT MAX(idvisit) FROM matomo.matomo_log_visit)");
        Assert.assertEquals(browserName1, firstVisit);
        Assert.assertEquals(browserName2, secondVisit);
    }

    @After
    public void afterAll() {
        DB_Queries.closeConnection();
    }
}
