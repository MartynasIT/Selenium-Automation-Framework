package com.automation.legoproject.base;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

public class TestContext {
    @Setter @Getter
    private ITestContext context;

    public TestContext(ITestContext context) {
        setContext(context);
    }

    /**
     * To set specific test data
     *
     * @param driver       - WebDriver
     * @param methodName   - Method name for faster debuging
     * @param jsonTestName - Full test name from json
     * @param browser      - Browser
     */
    public synchronized void setDefaultTestContextDetails(WebDriver driver, String methodName, String jsonTestName, String browser) {
        getContext().setAttribute("WebDriver", driver);
        getContext().setAttribute("JsonTestName", jsonTestName);
        getContext().setAttribute("MethodName", methodName);
        getContext().setAttribute("Browser", browser);
    }

    /**
     * To set specific test data
     *
     * @param attribute -  String attribute value
     * @param value     - String value
     */
    public synchronized void setCustomTestContextDetail(String attribute, String value) {
        getContext().setAttribute(attribute, value);
    }

    /**
     * To get specific detail from context
     *
     * @param attribute -  String attribute value
     */
    public synchronized Object getDetail(String attribute) {
        return getContext().getAttribute(attribute);
    }
}
