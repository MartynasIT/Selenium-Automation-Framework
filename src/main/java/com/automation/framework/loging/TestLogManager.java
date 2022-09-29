package com.automation.framework.loging;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TestLogManager {
    private final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    private ITestResult result;
    private ITestContext context;
    private String testName;
    private String methodName;
    private String browser;
    private static final String NO_ERROR = "No Errors!";
    FileLogger JsonLogger;

    public TestLogManager(ITestResult result, RemoteWebDriver driver) {
        this.result = result;
        context = result.getTestContext();
        setDriver(driver);
        JsonLogger = new JsonLogger();
    }

    public synchronized void setDriver(RemoteWebDriver driver) {
        this.driver.set(driver);
    }

    public synchronized void log(boolean passed) {
        testName = context.getAttribute("JsonTestName").toString();
        methodName = context.getAttribute("MethodName").toString();
        browser = context.getAttribute("Browser").toString();
        if (passed)
            JsonLogger.log(methodName, true, NO_ERROR, browser, testName);
        else
            JsonLogger.log(methodName, false, result.getThrowable().getMessage(), browser, testName);
    }
}
