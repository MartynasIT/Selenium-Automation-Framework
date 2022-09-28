package com.automation.framework.driver;

import com.automation.framework.utils.SystemUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager extends DriverManager {

    @Override
    protected void createWedDriver() {
        SystemUtil.OSType os = SystemUtil.getOperatingSystemType();
        switch (os) {
            case WINDOWS:
                WebDriverManager.edgedriver().operatingSystem(OperatingSystem.WIN).setup();
                break;
            case MACOS:
                WebDriverManager.edgedriver().operatingSystem(OperatingSystem.MAC).setup();
                break;
            case LINUX:
                WebDriverManager.edgedriver().operatingSystem(OperatingSystem.LINUX).setup();
                break;
            default:
                throw new RuntimeException("Unsupported OS");
        }
        this.driver.set(new EdgeDriver());
        this.driver.get().manage().window().maximize();

    }
}
