package com.framework.driver;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends DriverManager {

    @Override
    protected void createWedDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        driver.set(new ChromeDriver());
        driver.get().manage().window().maximize();
    }
}
