package com.automation.framework.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected static final String CHROME_DRIVER_WIN = "src/main/resources/chromedriver.exe";
    protected static final String CHROME_DRIVER_MAC = "src/main/resources/chromedriverMac";
    protected static final String CHROME_DRIVER_LINUX = "src/main/resources/chromedriverLinux";
    protected static final String EDGE_DRIVER_WIN = "src/main/resources/msedgedriver.exe";
    protected static final String EDGE_DRIVER_LINUX = "src/main/resources/msedgedriverLinux";
    protected static final String EDGE_DRIVER_MAC = "src/main/resources/msedgedriverMac";
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected abstract void createWedDriver();

    public synchronized WebDriver getWebDriver() {
        if (driver.get() == null) {
            createWedDriver();
        }
        return driver.get();
    }

}
