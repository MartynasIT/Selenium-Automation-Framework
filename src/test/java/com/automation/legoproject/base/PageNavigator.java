package com.automation.legoproject.base;

import com.automation.framework.utils.CoreSelenium;
import com.automation.legoproject.pagecomponents.HeaderMenu;
import com.automation.legoproject.pageobjects.CartPage;
import com.automation.legoproject.pageobjects.ProductSearchResultPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageNavigator {
    HeaderMenu header;
    protected Logger logger = LogManager.getLogger(PageNavigator.class);

    private final CoreSelenium selenium;

    public PageNavigator(CoreSelenium selenium) {
        this.selenium = selenium;
        header = new HeaderMenu(selenium);
    }

    public ProductSearchResultPage navigateToProductsKeychains() {
        logger.info("Navigating to keychains");
        header.clickShop();
        header.clickMerchandise();
        header.clickKeychains();
        return new ProductSearchResultPage(selenium);
    }

    public CartPage navigateToCart() {
        logger.info("Navigating to Cart");
        header.clickBag();
        return new CartPage(selenium);
    }
}

