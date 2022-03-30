package com.framework.driver;

import com.framework.utils.SystemUtil;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends DriverManager {

    @Override
    protected void createWedDriver() {
        SystemUtil.OSType os = SystemUtil.getOperatingSystemType();
        switch (os) {
            case WINDOWS:
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_WIN);
                break;
            case MACOS:
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_MAC);
                break;
            case LINUX:
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LINUX);
                break;
            default:
                throw new RuntimeException("Unsupported OS");
        }
        driver.set(new ChromeDriver());
        driver.get().manage().window().maximize();
    }
}
