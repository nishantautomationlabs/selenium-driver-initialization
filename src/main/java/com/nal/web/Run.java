package com.nal.web;

import org.openqa.selenium.WebDriver;
import java.io.IOException;

/**
 * Created by nishant on 7/2/18.
 */
public class Run {
    public static void main(String[] args) throws IOException {
        WebDriver driver = null;

        //Local run on Firefox browser
        System.out.println("Local run on Firefox browser");
        try {
            driver = DriverFactory. initializeLocalWebDriver(Enums.BrowserName.FIREFOX, "/opt/softwares/");
            driver.get("http://automationindepth.blogspot.in");
        }
        catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }
        DriverFactory.quit(driver);


        //Local run on Chrome browser
        System.out.println("Local run on Chrome browser");
        try {
            driver = DriverFactory.initializeLocalWebDriver(Enums.BrowserName.CHROME, "/opt/softwares/");
            driver.get("http://automationindepth.blogspot.in");
        }
        catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }
        DriverFactory.quit(driver);


        //Grid run on Firefox browser
        System.out.println("Grid run on Firefox browser");
        try {
            driver = DriverFactory.initializeRemoteWebDriver(Enums.BrowserName.FIREFOX, "127.0.0.1", 4444);
            driver.get("http://automationindepth.blogspot.in");
        }
        catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }
        DriverFactory.quit(driver);


        //Grid run on Chrome browser
        System.out.println("Grid run on Chrome browser");
        try {
            driver = DriverFactory.initializeRemoteWebDriver(Enums.BrowserName.CHROME, "127.0.0.1", 4444);
            driver.get("http://automationindepth.blogspot.in");
        }
        catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }
        DriverFactory.quit(driver);
    }
}
