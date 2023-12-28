package pages;

import static pageObjects.HomePageObjects.CALENDAR_MONTH_YEAR;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CalendarPage extends HomePage {
    private WebDriver driver;

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
    }

    public static class DateComponents {
        public final String year;

        public final String month;

        public final String day;

        public DateComponents(String year, String month, String day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    public void selectDateRange(String startDate, String endDate) {
        DateComponents checkInDate = dateSplitter(startDate);
        DateComponents checkOutDate = dateSplitter(endDate);
        // Find the desired month and year combination
        YearMonth desiredMonthYear = YearMonth.of(Integer.parseInt(checkInDate.year), Integer.parseInt(checkInDate.month));
        // Check if month and year is the same as the one visible. If not click the next/back button until we navigate to the required one
        YearMonth currentMonthYear = getCurrentMonthYear();
        while(!currentMonthYear.equals(desiredMonthYear)) {
            if(currentMonthYear.isBefore(desiredMonthYear)) {
                clickNavigationCalendarButtons("Next");
            } else if(currentMonthYear.isAfter(desiredMonthYear)) {
                clickNavigationCalendarButtons("Back");
            }
            currentMonthYear = getCurrentMonthYear();
        }

        // Find the start date element
        WebElement startElement = driver.findElement(By.xpath("//button[text()='" + checkInDate.day + "']"));
        // Find the end date element
        WebElement endElement = driver.findElement(By.xpath("//button[text()='" + checkOutDate.day + "']"));

        // Perform click and drag action
        Actions actions = new Actions(driver);
        actions.clickAndHold(endElement).dragAndDrop(startElement, endElement)
               .build()
               .perform();
    }

    private YearMonth getCurrentMonthYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        YearMonth yearMonth = YearMonth.parse(driver.findElement(CALENDAR_MONTH_YEAR).getText(), formatter);
        return yearMonth;
    }

    public static DateComponents dateSplitter(String dateString) {
        // Use a DateTimeFormatter with the same pattern as the date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Extract the year, month, and day as strings
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonthValue()); // Ensures two digits for month
        String day = String.format("%02d", date.getDayOfMonth());   // Ensures two digits for day

        return new DateComponents(year, month, day);
    }
}

