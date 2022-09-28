package com.automation.framework.driver;

public class DriverFactory {

    public enum DriverType {
        CHROME,
        EDGE
    }

    public static DriverManager getDriverManager() {
        DriverManager driverManager;
        String browser = System.getProperty("browser");
        if (browser == null)
            browser = "Edge";

        DriverType driver = DriverType.valueOf(browser.toUpperCase());
        switch (driver) {
            case EDGE:
                driverManager = new EdgeDriverManager();
                break;
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + driver);
        }
        return driverManager;

    }

}

