package com.automation.legoproject.pagecomponents;

import com.automation.framework.utils.CoreSelenium;
import com.automation.legoproject.base.BasePage;
import org.openqa.selenium.By;

public class SpamPopUp extends BasePage {
    private final By COOKIE_POP_UP_ACCEPT = By.xpath("//button[@data-test='cookie-accept-all']");
    private final By POP_UP_SUBMIT = By.xpath("//div[@class='AgeGatestyles__Wrapper-xudtvj-0 bblFHz']//button[@type='submit']");
    private final By CONTINUE = By.xpath("//button[contains(text(),'Continue')]");

    public SpamPopUp(CoreSelenium coreSelenium) {
        super(coreSelenium);
    }

    public void bypassPopUps() {
        if (selenium.isElementFound(CONTINUE, 4, 1))
            selenium.click(CONTINUE, "Clicking continue on pop up");

        if (selenium.isElementFound(POP_UP_SUBMIT, 4, 1))
            selenium.click(POP_UP_SUBMIT, "Clicking continue on pop up");

        if (selenium.isElementFound(COOKIE_POP_UP_ACCEPT, 4, 1))
            selenium.click(COOKIE_POP_UP_ACCEPT, "Clicking accept cookies");
    }
}
