package pages;

import static pageObjects.HomePageObjects.*;
import static utils.DriversUtils.getDriver;

import io.cucumber.datatable.DataTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonUtils;

public class HomePage extends CommonUtils {

    @FindBy(tagName = "h2")
    private WebElement roomCategoryIdentifier;

    @FindBy(xpath = "//button[contains(@class,'openBooking')]")
    private WebElement bookButton;

    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void goToRoomsCategory() {
        try {
            scrollToElement(roomCategoryIdentifier);
        } catch(RuntimeException e) {
            e.printStackTrace();
            System.out.println("Error in the rooms category method");
        }
    }

    public void assertBookButtonDisplayed() {
        Assert.assertEquals(true, bookButton.isDisplayed());
    }

    public void navigateToHomePage() {
        getDriver().get("https://automationintesting.online/#/");
    }

    public void clickBookNowButton() {
        scrollToElement(BOOK_NOW_BUTTON);
        Assert.assertEquals(true, bookButton.isDisplayed());
        getDriver().findElement(BOOK_NOW_BUTTON).click();
    }

    public void clickBookOrCancelButton(String scenario) {
        List<WebElement> buttons = getDriver().findElements(BOOK_CANCEL_BUTTON);
        for(WebElement element : buttons) {
            if(element.getText().equals(scenario)) {
                centerElement(BOOK_CANCEL_BUTTON);
                element.click();
            }
        }
    }

    public void clickNavigationCalendarButtons(String scenario) {
        List<WebElement> buttons = getDriver().findElement(NAVIGATE_CALENDAR_BUTTONS).findElements((By.tagName("button")));
        for(WebElement element : buttons) {
            if(element.getText().equals(scenario)) {
                centerElement(NAVIGATE_CALENDAR_BUTTONS);
                element.click();
            }
        }
    }

    public void provideCustomerDataForBooking(DataTable table) {
        Map<String, List<String>> data = readDataTableWithHeaderAndMultipleValues(table);
        List<String> firstName = data.get("FirstName");
        List<String> lastName = data.get("LastName");
        List<String> email = data.get("Email");
        List<String> phone = data.get("Phone");
        List<String> checkInDate = data.get("CheckInDate");
        List<String> checkOutDate = data.get("CheckOutDate");

        if(firstName != null & !firstName.get(0).equals("null")) {
            centerElement(FIRST_NAME_INPUT_FIELD);
            input(FIRST_NAME_INPUT_FIELD, firstName.get(0));
        }

        if(lastName != null & !lastName.get(0).equals("null")) {
            centerElement(LAST_NAME_INPUT_FIELD);
            input(LAST_NAME_INPUT_FIELD, lastName.get(0));
        }
        if(email != null & !email.get(0).equals("null")) {
            centerElement(EMAIL_INPUT_FIELD);
            input(EMAIL_INPUT_FIELD, email.get(0));
        }
        if(phone != null & !phone.get(0).equals("null")) {
            centerElement(PHONE_INPUT_FIELD);
            input(PHONE_INPUT_FIELD, phone.get(0));
        }

        if(checkInDate != null & !checkInDate.get(0).equals("null") && checkOutDate != null & !checkOutDate.get(0).equals("null")) {
            CalendarPage calendarPage = new CalendarPage(getDriver());
            calendarPage.selectDateRange(checkInDate.get(0), checkOutDate.get(0));
        }
    }

    public void getValidationErrors(String scenario) {
        centerElement(VALIDATION_ERRORS);
        WebElement validationMessages = getDriver().findElement(VALIDATION_ERRORS);
        List<WebElement> errors = validationMessages.findElements(By.tagName("p"));
        List<String> errorMessages = new ArrayList<>();
        for(WebElement error : errors) {
            String errorText = error.getText().trim();
            errorMessages.add(errorText);
        }
        switch(scenario) {
            case "phone number missing":
                Assert.assertEquals(2, errorMessages.size());
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("must not be empty")));
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("size must be between 11 and 21")));
                break;
            case "email missing":
                Assert.assertEquals(1, errorMessages.size());
                Assert.assertTrue(errorMessages.stream().allMatch(error->error.equals("must not be empty")));
                break;
            case "last name missing":
                Assert.assertEquals(2, errorMessages.size());
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("size must be between 3 and 30")));
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("Lastname should not be blank")));
                break;
            case "first name missing":
                Assert.assertEquals(2, errorMessages.size());
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("size must be between 3 and 18")));
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("Firstname should not be blank")));
                break;
            case "no dates selected":
                Assert.assertEquals(2, errorMessages.size());
                Assert.assertTrue(errorMessages.stream().allMatch(error->error.equals("must not be null")));
                break;
            case "invalid email":
                Assert.assertEquals(1, errorMessages.size());
                Assert.assertTrue(errorMessages.stream().anyMatch(error->error.equals("must be a well-formed email address")));
                break;
        }
    }
}

