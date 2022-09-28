package com.automation.framework.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected abstract void createWedDriver();

    public synchronized WebDriver getWebDriver() {
        if (driver.get() == null) {
            createWedDriver();
        }
        return driver.get();
    }

    public synchronized void quitWebDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.set(null);
        }
    }

}
