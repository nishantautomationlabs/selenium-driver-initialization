package com.nal.web;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.nal.web.Enums.*;

/**
 * Created by nishant on 6/2/18.
 */
public class DriverFactory {

    public static WebDriver initializeLocalWebDriver(BrowserName browserName, String driverPath)
    {
        WebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (browserName) {

            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.link.open_newwindow.restriction", 0); //To open new tab instead of new window on clicking on any link.
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                driver = new FirefoxDriver(capabilities);
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
                capabilities = DesiredCapabilities.chrome();
                final ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("enable-automation");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--start-maximized");
                Map prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_setting_values.geolocation", 2); // 1:allow 2:block To Block Geo Location
                chromeOptions.setExperimentalOption("prefs", prefs);
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                driver = new ChromeDriver(capabilities);
                break;
            default:
                System.out.println("Initialize Local Web Driver not implemented for " + browserName + " browser");
                System.exit(1);
        }
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static WebDriver initializeRemoteWebDriver(BrowserName browserName, String seleniumServerIP, int seleniumServerPort) {
        RemoteWebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browserName.toString().toLowerCase());
        capabilities.setCapability("platform", "ANY");
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        switch (browserName) {

            case FIREFOX:
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.link.open_newwindow.restriction", 0); //To open new tab instead of new window on clicking on any link.
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                break;
            case CHROME:
                final ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("enable-automation");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--start-maximized");
                Map prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_setting_values.geolocation", 2); // 1:allow 2:block
                chromeOptions.setExperimentalOption("prefs", prefs);
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                break;
            default:
                System.out.println("Initialize Remote Web Driver not implemented for " + browserName + " browser");
                System.exit(1);
        }
        try {
            driver = new RemoteWebDriver(new URL("http://" + seleniumServerIP + ":" + seleniumServerPort + "/wd/hub"), capabilities);
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }
        return driver;
    }

    public static void quit(WebDriver driver)
    {
        if(driver != null)
            driver.quit();;
    }
}
