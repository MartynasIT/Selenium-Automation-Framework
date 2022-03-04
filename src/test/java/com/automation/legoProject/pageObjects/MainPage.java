package com.automation.legoProject.pageObjects;

import com.automation.legoProject.base.BasePage;
import com.framework.utils.CoreSelenium;
import org.openqa.selenium.By;

public class MainPage extends BasePage {

    private String PAGE_NAME = "Main Page";
    private final By COOKIE_POP_UP_ACCEPT = By.xpath("//button[@data-test='cookie-accept-all']");
    private final By POP_UP_SUBMIT = By.xpath("//div[@class='AgeGatestyles__Wrapper-xudtvj-0 bblFHz']//button[@type='submit']");


    public MainPage(CoreSelenium selenium, boolean firstTimeLoading) {
        super(selenium);
        if (firstTimeLoading)
            bypassPopUps();
        if (!selenium.isElementFound(By.xpath("//ul[@data-test='quicklinks']"), 5, 1))
            throw new RuntimeException("Failed to load " + PAGE_NAME);
        else
            logger.info(PAGE_NAME + " was loaded successfully");
    }

    public void bypassPopUps() {
        if (selenium.isElementFound(POP_UP_SUBMIT, 2, 1))
            selenium.click(POP_UP_SUBMIT, "Clicking continue on pop up");

        if (selenium.isElementFound(COOKIE_POP_UP_ACCEPT, 4, 1))
            selenium.click(COOKIE_POP_UP_ACCEPT, "Clicking accept cookies");
    }
}
