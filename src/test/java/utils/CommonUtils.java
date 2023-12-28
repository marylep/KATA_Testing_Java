package utils;

import static pageObjects.HomePageObjects.CONFIRMATION_MODAL;
import static utils.DriversUtils.getDriver;

import io.cucumber.datatable.DataTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class CommonUtils {

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,250)");
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", getDriver().findElement(by));
    }

    public static void centerElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center',inline:'center'});", getDriver().findElement(by));
    }

    public void input(By by, String value) {
        WebElement element = getDriver().findElement(by);
        if(value != null) {
            element.click();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
            element.sendKeys(value);
        }
    }

    public void getConfirmationPopUp(String checkInDate, String checkOutDate) {
        WebElement confirmationMessageElement = getDriver().findElement(CONFIRMATION_MODAL);

        String confirmationText = confirmationMessageElement.getText();
        String oneLineText = confirmationText.replaceAll("\\r\\n|\\r|\\n", " ").replace("Close", "").trim();

        String expectedMessage = "Booking Successful! Congratulations! Your booking has been confirmed for: " + checkInDate + " - " + checkOutDate;
        Assert.assertEquals(expectedMessage, oneLineText);
    }

    public static Map<String, List<String>> readDataTableWithHeaderAndMultipleValues(DataTable table) {
        if(table == null || table.cells().isEmpty()) {
            throw new IllegalArgumentException("DataTable cannot be null or empty");
        }

        List<List<String>> tableCells = table.cells();
        List<String> headers = new ArrayList<>(tableCells.get(0));
        Map<String, List<String>> dataMap = new HashMap<>();

        for(int columnIndex = 0; columnIndex < headers.size(); columnIndex++) {
            List<String> columnValues = new ArrayList<>();
            for(int rowIndex = 1; rowIndex < tableCells.size(); rowIndex++) {
                columnValues.add(tableCells.get(rowIndex).get(columnIndex));
            }
            dataMap.put(headers.get(columnIndex), columnValues);
        }

        return dataMap;
    }

}
