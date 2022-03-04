package com.framework.driver;

import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager extends DriverManager {

    @Override
    protected void createWedDriver() {
        System.setProperty("webdriver.edge.driver", EDGE_DRIVER);
        this.driver.set(new EdgeDriver());
        this.driver.get().manage().window().maximize();

    }
}
