package com.automation.legoProject.pageObjects;

import com.automation.legoProject.base.BasePage;
import com.framework.utils.CoreSelenium;
import org.openqa.selenium.By;

public class ProductInfoPage extends BasePage {

    private final String PAGE_NAME = "Product info";
    private By WISHLISH_BUTTON = By.xpath("//button[@data-test='add-to-wishlist']");
    private By PRODUCT_NAME = By.xpath("//h1[@data-test='product-overview-name']");
    private By ADD_TO_BAG = By.xpath("//button[@data-test='add-to-bag']");
    private By VIEW_MY_BAG = By.xpath("//a[@data-test='view-my-bag']");


    public ProductInfoPage(CoreSelenium selenium) {
        super(selenium);
        if (!selenium.isElementFound(WISHLISH_BUTTON))
            throw new RuntimeException("Failed to load " + PAGE_NAME);
        else
            logger.info(PAGE_NAME + " was loaded successfully");
    }

    public void addItemToCart(boolean navigateToCart) {
        selenium.click(ADD_TO_BAG, "Adding product to cart");
        if (navigateToCart)
            selenium.click(VIEW_MY_BAG, "Moving to the cart");

    }

    public String getProductName() {
        return selenium.getText(PRODUCT_NAME);
    }
}