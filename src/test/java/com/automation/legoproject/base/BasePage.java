package com.automation.legoproject.base;

import com.automation.framework.utils.CoreSelenium;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {
    protected Logger logger = LogManager.getLogger(BasePage.class);
    protected CoreSelenium selenium;

    public BasePage(CoreSelenium coreSelenium) {
        this.selenium = coreSelenium;
    }
}