package com.automation.framework.listeners;

import com.automation.framework.loging.TestLogManager;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    public synchronized RemoteWebDriver getDriver() {
        return this.driver.get();
    }

    public synchronized void setDriver(RemoteWebDriver driver) {
        this.driver.set(driver);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        ITestContext context = result.getTestContext();
        setDriver((RemoteWebDriver) context.getAttribute("WebDriver"));
        new TestLogManager(result, getDriver()).log(true);
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        new TestLogManager(result, getDriver()).log(false);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
    }


}
