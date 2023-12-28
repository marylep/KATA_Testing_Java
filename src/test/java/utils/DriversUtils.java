package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class DriversUtils {
    static WebDriver driver;

    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {

        if (driver == null ) {
            initDriver();
        }
        return driver;
    }

    public static void tearDown() {
        driver.quit();
        driver = null;
    }
}

