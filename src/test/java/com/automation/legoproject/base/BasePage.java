package com.automation.legoproject.base;

import com.automation.framework.utils.CoreSelenium;

public class BasePage {
    protected CoreSelenium selenium;

    public BasePage(CoreSelenium coreSelenium) {
        this.selenium = coreSelenium;
    }
}
