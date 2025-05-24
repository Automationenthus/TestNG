package driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ConfigReader;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;



public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
 

    public static WebDriver initDriver(String browser) {
    	          

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver.set(new EdgeDriver());
        } else if (browser.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver.set(new SafariDriver());
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }

       
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); 
        }
    }
}



