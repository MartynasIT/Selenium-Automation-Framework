package com.automation.legoproject.pageobjects;

import com.automation.framework.loging.Log4jLogger;
import com.automation.legoproject.base.BasePage;
import com.automation.framework.utils.CoreSelenium;
import com.automation.legoproject.pagecomponents.SpamPopUp;
import org.openqa.selenium.By;

public class MainPage extends BasePage {
    private static String PAGE_NAME = "Main Page";

    public MainPage(CoreSelenium selenium, boolean firstTimeLoading) {
        super(selenium);
        if (firstTimeLoading)
            new SpamPopUp(selenium).bypassPopUps();
        if (!selenium.isElementFound(By.xpath("//ul[@data-test='quicklinks']"), 5, 1))
            throw new RuntimeException("Failed to load " + PAGE_NAME);
        else
            Log4jLogger.log(PAGE_NAME + " was loaded successfully");
    }
}
