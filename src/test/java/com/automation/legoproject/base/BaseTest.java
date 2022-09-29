package com.automation.legoproject.base;

import com.automation.framework.driver.DriverFactory;
import com.automation.framework.driver.DriverManager;
import com.automation.framework.loging.JsonLogger;
import com.automation.framework.loging.Log4jLogger;
import com.automation.framework.utils.CoreSelenium;
import com.automation.framework.utils.JsonReader;
import lombok.Getter;
import lombok.Setter;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.io.File;


public class BaseTest {
    protected CoreSelenium selenium;
    @Setter @Getter
    protected DriverManager driverManager;
    @Setter @Getter
    private JsonReader jsonReader;
    private static final String ENVIRONMENTS = "src/test/resources/env.json";
    @Setter @Getter
    private TestContext testContext;
    private static boolean suiteRan;

    @BeforeTest
    @Parameters("testDataPath")
    public void setup(@Optional String testDataPath) {
        setTestContext(new TestContext(Reporter.getCurrentTestResult().getTestContext()));
        String browser = ((System.getProperty("browser") == null) ? "Edge" : System.getProperty("browser"));
        if (testDataPath != null)
            setJsonReader(new JsonReader(testDataPath));
        setDriverManager(DriverFactory.getDriverManager());
        selenium = new CoreSelenium(getDriverManager().getWebDriver());
        getTestContext().setDefaultTestContextDetails(
                getDriverManager().getWebDriver(),
                Reporter.getCurrentTestResult().getTestContext().getAllTestMethods()[0].getMethodName(),
                getJsonReader().getValue("Test_Name") + " : " + browser,
                browser);
    }

    @BeforeMethod
    public void startDriver() {
        Log4jLogger.log("Test Started: " + getTestContext().getDetail("JsonTestName"));
        selenium.get(new JsonReader(ENVIRONMENTS).getValue("Lego.URL"), "Launching website");
    }

    @AfterTest
    public void quitDriver() {
        getDriverManager().quitWebDriver();
    }

    @BeforeSuite
    public void deleteReport() {
        if(!suiteRan) {
            suiteRan = true;
            File logFile = new File(JsonLogger.JSON_PATH);
            if(logFile.exists())
                logFile.delete();
        }
    }
}
