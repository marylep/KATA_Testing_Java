package features.step_definitions;

import static utils.DriversUtils.getDriver;
import static utils.DriversUtils.tearDown;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.concurrent.TimeUnit;
import pages.BasePage;
import pages.HomePage;

public class BookingSteps extends BasePage {

    HomePage homePage = new HomePage();

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage.navigateToHomePage();
    }

    @When("I browse through the page")
    public void i_browse_through_the_page() {
        homePage.goToRoomsCategory();
    }

    @Then("I have the option to book a room")
    public void i_have_the_option_to_book_a_room() {
        homePage.assertBookButtonDisplayed();
    }

    @And("I click the button to book a room")
    public void i_click_the_button_to_book_a_Room() {
        homePage.clickBookNowButton();
    }

    @When("i provide the following data for the book:")
    public void i_provide_the_following_data_for_the_book(DataTable data) {
        homePage.provideCustomerDataForBooking(data);
    }

    @And("i click the {string} button")
    public void i_click_the_button(String scenario) {
        homePage.clickBookOrCancelButton(scenario);
    }

    @Then("i get the confirmation message for the successful booking for days {string} to {string}")
    public void i_get_the_confirmation_message_for_the_successful_booking_for_days_to(String checkInDate, String checkOutDate) {
        homePage.getConfirmationPopUp(checkInDate, checkOutDate);
    }

    @Then("i get the correct validation messages for scenario {string}")
    public void i_get_the_correct_validation_messages_for_scenario(String scenario) {
        homePage.getValidationErrors(scenario);
    }

    @After(value = "@book")
    public void closeBrowser() {
        tearDown();
    }
}
