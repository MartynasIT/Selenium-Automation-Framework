package com.framework.driver;

import com.framework.utils.SystemUtil;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager extends DriverManager {

    @Override
    protected void createWedDriver() {
        SystemUtil.OSType os = SystemUtil.getOperatingSystemType();
        switch (os) {
            case WINDOWS:
                System.setProperty("webdriver.edge.driver", EDGE_DRIVER_WIN);
                break;
            case MACOS:
                System.setProperty("webdriver.edge.driver", EDGE_DRIVER_MAC);
                break;
            case LINUX:
                System.setProperty("webdriver.edge.driver", EDGE_DRIVER_LINUX);
                break;
            default:
                throw new RuntimeException("Unsupported OS");
        }
        this.driver.set(new EdgeDriver());
        this.driver.get().manage().window().maximize();

    }
}
