package com.automation.framework.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected static final String CHROME_DRIVER = "src/main/resources/chromedriver.exe";
    protected static final String EDGE_DRIVER = "src/main/resources/msedgedriver.exe";
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected abstract void createWedDriver();

    public synchronized WebDriver getWebDriver() {
        if (driver.get() == null) {
            createWedDriver();
        }
        return driver.get();
    }

}
