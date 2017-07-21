package swtestacademy.Javascript;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ExecuteAsycSleepBrowserTest {
    static WebDriver driver;
    private static String url = "http://phppot.com/demo/jquery-dependent-dropdown-list-countries-and-states/";

    //Setup Driver
    @BeforeClass
    public static void setupTest() {
        driver = new FirefoxDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }

    @Test
    public void browserSleepExampleTest() {
        //Set ScriptTimeout
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        //Declare and set start time
        long startTime = System.currentTimeMillis();

        //Declare JavascriptExecutor
        JavascriptExecutor js =(JavascriptExecutor)driver;

        //Call executeAsyncScript() method
        js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 4000);");

        //Get the difference (currentTime - startTime) it should be greater than 1500
        System.out.println("Passed time: " + (System.currentTimeMillis() - startTime));

        //Assert that the time difference is greater than 4000
        assertTrue("Time difference must be greater than 4000 milliseconds",
                (System.currentTimeMillis() - startTime) > 4000);
    }

    //Close Driver
    @AfterClass
    public static void quitDriver() {
        driver.quit();
    }
}