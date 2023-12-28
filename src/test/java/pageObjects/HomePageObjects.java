package pageObjects;

import org.openqa.selenium.By;
import pages.HomePage;

public class HomePageObjects extends HomePage {
    public static final By BOOK_NOW_BUTTON=By.cssSelector("button[class*='openBooking']");
    public static final By FIRST_NAME_INPUT_FIELD=By.cssSelector("input[class*='room-firstname']");
    public static final By LAST_NAME_INPUT_FIELD=By.cssSelector("input[class*='room-lastname']");
    public static final By EMAIL_INPUT_FIELD=By.cssSelector("input[class*='room-email']");
    public static final By PHONE_INPUT_FIELD=By.cssSelector("input[class*='room-phone']");
    public static final By BOOK_CANCEL_BUTTON=By.cssSelector("button[class*='book-room']");
    public static final By VALIDATION_ERRORS=By.cssSelector("div[class*='alert-danger']");
    public static final By CALENDAR_MONTH_YEAR=By.cssSelector("span[class=rbc-toolbar-label]");
    public static final By NAVIGATE_CALENDAR_BUTTONS=By.cssSelector("span[class=rbc-btn-group]");
    public static final By CONFIRMATION_MODAL=By.cssSelector(".confirmation-modal");
}
