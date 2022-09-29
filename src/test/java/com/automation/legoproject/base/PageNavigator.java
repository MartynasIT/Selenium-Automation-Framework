package com.automation.legoproject.base;

import com.automation.framework.loging.ConsoleLogger;
import com.automation.framework.utils.CoreSelenium;
import com.automation.legoproject.pagecomponents.HeaderMenu;
import com.automation.legoproject.pageobjects.CartPage;
import com.automation.legoproject.pageobjects.ProductSearchResultPage;

public class PageNavigator {
    HeaderMenu header;
    private CoreSelenium selenium;
    private ConsoleLogger logger;

    public PageNavigator(CoreSelenium selenium, ConsoleLogger logger) {
        this.selenium = selenium;
        this.logger = logger;
        header = new HeaderMenu(selenium);
    }

    public ProductSearchResultPage navigateToProductsKeychains() {
        logger.log("Navigating to keychains");
        header.clickShop();
        header.clickMerchandise();
        header.clickKeychains();
        return new ProductSearchResultPage(selenium);
    }

    public CartPage navigateToCart() {
        logger.log("Navigating to Cart");
        header.clickBag();
        return new CartPage(selenium);
    }
}

