package com.automation.framework.driver;

import com.automation.framework.utils.SystemUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends DriverManager {

    @Override
    protected void createWedDriver() {
        SystemUtil.OSType os = SystemUtil.getOperatingSystemType();
        switch (os) {
            case WINDOWS:
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.WIN).setup();
                break;
            case MACOS:
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.MAC).setup();
                break;
            case LINUX:
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.LINUX).setup();
                break;
            default:
                throw new RuntimeException("Unsupported OS");
        }
        driver.set(new ChromeDriver());
        driver.get().manage().window().maximize();
    }
}
