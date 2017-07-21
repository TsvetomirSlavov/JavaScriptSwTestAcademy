package swtestacademy.Javascript;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by ONUR BASKIRT on 25.01.2016.
*/
public class ExecuteAsyncXMLHttpRequestTest {
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
    public void sendXMLHTTPRequestTest() {
        Object response = null;

        //Set ScriptTimeout
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        //Declare JavascriptExecutor
        JavascriptExecutor js =(JavascriptExecutor)driver;

        //Injecting a XMLHttpRequest and waiting for the result
        //Ref1: https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/JavascriptExecutor.html
        //Ref2: http://www.openjs.com/articles/ajax_xmlhttp_using_post.php
        try {
            response = js.executeAsyncScript(
                    //Declare callback first!
                    "var callback = arguments[arguments.length - 1];" +

                    //Declare url, parameters and method for POST
                    //Send country_id=5 (USA)
                    "var http = new XMLHttpRequest();" +
                    "var url = 'get_state.php';" +  //url
                    "var params = 'country_id=5';" +  //parameters
                    "http.open('POST', url, true);" +

                    //Send the proper header information along with the request
                    "http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');" +
                    "http.setRequestHeader('Content-length', params.length);" +
                    "http.setRequestHeader('Connection', 'close');" +

                    //Call a function when the state changes.
                    "http.onreadystatechange = function() {" +
                    "    if(http.readyState == 4) {" +
                    "        callback(http.responseText);" +
                    "    };" +
                    "};" +
                    "http.send(params);");
        } catch (UnhandledAlertException e) {
            System.err.println("Error Occured!");
        }

        //Assert that returned cities are related with USA
        System.out.println((String)response);
        assertThat((String) response, containsString("<option value=\"4\">New York</option>"));
    }

    //Close Driver
    @AfterClass
    public static void quitDriver() {
        driver.quit();
    }
}